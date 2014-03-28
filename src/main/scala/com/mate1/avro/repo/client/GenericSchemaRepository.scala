package com.mate1.avro.repo.client

import org.apache.avro.repo.client.RESTRepositoryClient
import scala.collection.mutable
import java.util.logging.Logger
import org.apache.avro.repo.{SchemaEntry, Subject}
import com.google.common.collect.{HashBiMap, BiMap}

/**
 * Generic implementation of a caching client for an AVRO-1124-style repo which provides strongly-typed APIs.
 *
 * @tparam ID
 * @tparam SCHEMA
 */
abstract class GenericSchemaRepository[ID, SCHEMA] {

  // Abstract functions which need to be overridden using traits or custom implementations.

  // Functions used to convert back and forth with the AVRO-1124 Schema Repo, which uses Strings for its IDs and Schemas.
  protected def idToString(id: ID): String
  protected def stringToId(id: String): ID
  protected def schemaToString(schema: SCHEMA): String
  protected def stringToSchema(schema: String): SCHEMA

  // Configuration
  protected def getRepositoryURL: String

  // Concrete implementation !

  // Utilities
  private lazy val client = new RESTRepositoryClient(getRepositoryURL)
  private val logger = Logger.getLogger(classOf[GenericSchemaRepository[ID, SCHEMA]].getName)

  // Internal state
  private val idToSchemaCache = mutable.Map[String, BiMap[ID, SCHEMA]]()
  private val schemaToIdCache = mutable.Map[String, BiMap[SCHEMA, ID]]()

  // Private functions to DRY up the code

  /**
   * Retrieves an entity (which is currently not found in our internal cache) from the remote repository,
   * and persists the retrieved entity in the cacheMap parameter that's passed in
   *
   * @param topic to look into to get the repository's corresponding [[org.apache.avro.repo.Subject]]
   * @param cacheKey to store in the cache, if we are able to retrieve an entity
   * @param cacheMap to store the key and entity into, if we are able to retrieve the entity
   *            N.B.: using a java.util.Map for compatibility with Guava's [[com.google.common.collect.BiMap]]
   * @param entityRetrievalFunction to use on the [[org.apache.avro.repo.Subject]] to get a [[org.apache.avro.repo.SchemaEntry]]
   * @param schemaEntryToStringFunction to use on the [[org.apache.avro.repo.SchemaEntry]] in order to get our (Stringly-typed) entity
   * @param stringToValueFunction to convert the (Stringly-typed) entity into the proper type (VALUE).
   * @param createMissingSubject to tell the function whether to create the topic/Subject in the remote repository, if it doesn't already exist (default = false).
   * @param throwException to tell the function whether to throw an exception instead of returning None if there's any problem (default = false).
   * @tparam KEY the type of the key in the cacheMap we want to update
   * @tparam VALUE the type of the value in the cacheMap we want to update
   * @return Some(schema) if the topic and key are valid, None otherwise
   */
  private def retrieveUnknownEntity[KEY, VALUE](topic: String,
                                                cacheKey: KEY,
                                                cacheMap: java.util.Map[KEY, VALUE],
                                                entityRetrievalFunction: Subject => SchemaEntry,
                                                schemaEntryToStringFunction: SchemaEntry => String,
                                                stringToValueFunction: String => VALUE,
                                                createMissingSubject: Boolean = false,
                                                throwException: Boolean = false): Option[VALUE] = {
    val subjectOption: Option[Subject] = client.lookup(topic) match {
      case null => {
        if (createMissingSubject) {
          Some(client.register(topic, null))
        } else {
          None
        }
      }
      case subject => Some(subject)
    }

    subjectOption match {
      case Some(subject) => {
        try {
          entityRetrievalFunction(subject) match {
            case null => {
              if (throwException) {
                throw new RuntimeException("An unknown problem occurred... the RESTRepositoryClient returned null.")
              } else {
                None
              }
            }
            case schemaEntry => {
              val entity: VALUE = stringToValueFunction(
                schemaEntryToStringFunction(
                  schemaEntry
                )
              )
              cacheMap.put(cacheKey, entity)
              Some(entity)
            }
          }
        } catch {
          case e: Exception => {
            logger.warning("Got an exception while trying to retrieve an entity from the RESTRepositoryClient!\n" +
              s"${e.getMessage} : ${e.getStackTraceString}")

            if (throwException) {
              throw e
            } else {
              None
            }
          }
        }
      }
      case None => if (throwException) {
        throw new RuntimeException("The requested topic does not exist in the remote Schema Repository.")
      } else {
        None
      }
    }
  }

  /**
   *
   * @param topic
   * @param cacheKey
   * @param mainCache
   * @param inverseCache
   * @param entityRetrievalFunction
   * @param schemaEntryToStringFunction
   * @param stringToValueFunction
   * @param createMissingSubject
   * @param throwException
   * @tparam KEY
   * @tparam VALUE
   * @return
   */
  private def retrieveEntity[KEY, VALUE](topic: String,
                                         cacheKey: KEY,
                                         mainCache: mutable.Map[String, BiMap[KEY, VALUE]],
                                         inverseCache: mutable.Map[String, BiMap[VALUE, KEY]],
                                         entityRetrievalFunction: Subject => SchemaEntry,
                                         schemaEntryToStringFunction: SchemaEntry => String,
                                         stringToValueFunction: String => VALUE,
                                         createMissingSubject: Boolean = false,
                                         throwException: Boolean = false): Option[VALUE] = {
    def specificRetrieveFunction(cacheMap: java.util.Map[KEY, VALUE]): Option[VALUE] = {
      retrieveUnknownEntity[KEY, VALUE](
        topic,
        cacheKey,
        cacheMap,
        entityRetrievalFunction,
        schemaEntryToStringFunction,
        stringToValueFunction,
        createMissingSubject,
        throwException
      )
    }

    mainCache.get(topic) match {
      case Some(existingCachedMap) => Option(existingCachedMap.get(cacheKey)) match {
        case None => specificRetrieveFunction(existingCachedMap)
        case someSchema => someSchema
      }
      case None => {
        val newMapToCache = HashBiMap.create[KEY, VALUE]()
        mainCache.put(topic, newMapToCache)
        inverseCache.put(topic, newMapToCache.inverse())
        specificRetrieveFunction(newMapToCache)
      }
    }
  }

  // Public APIs below

  /**
   *
   * @param topic
   * @param schemaId
   * @return Some(schema) if the topic and schemaId are valid, None otherwise
   */
  def getSchema(topic: String, schemaId: ID): Option[SCHEMA] = {
    retrieveEntity[ID, SCHEMA](
      topic,
      cacheKey = schemaId,
      mainCache = idToSchemaCache,
      inverseCache = schemaToIdCache,
      entityRetrievalFunction = _.lookupById(idToString(schemaId)),
      schemaEntryToStringFunction = _.getSchema,
      stringToValueFunction = stringToSchema
    )
  }

  /**
   *
   * @param topic
   * @param schema
   * @return Some(schemaId) if the topic and schema are valid, None otherwise
   */
  def getSchemaId(topic: String, schema: SCHEMA): Option[ID] = {
    retrieveEntity[SCHEMA, ID](
      topic,
      cacheKey = schema,
      mainCache = schemaToIdCache,
      inverseCache = idToSchemaCache,
      entityRetrievalFunction = _.lookupBySchema(schemaToString(schema)),
      schemaEntryToStringFunction = _.getId,
      stringToValueFunction = stringToId
    )
  }

  /**
   *
   * @param topic
   * @param schema
   * @return schemaId, potentially an already existing one, if the schema isn't new.
   * @throws Exception if registration is unsuccessful
   */
  def registerSchema(topic: String, schema: SCHEMA): ID = {
    retrieveEntity[SCHEMA, ID](
      topic,
      cacheKey = schema,
      mainCache = schemaToIdCache,
      inverseCache = idToSchemaCache,
      entityRetrievalFunction = _.register(schemaToString(schema)),
      schemaEntryToStringFunction = _.getId,
      stringToValueFunction = stringToId,
      createMissingSubject = true,
      throwException = true
    ).get
  }
}