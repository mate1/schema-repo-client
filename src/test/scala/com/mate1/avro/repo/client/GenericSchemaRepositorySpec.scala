package com.mate1.avro.repo.client

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import scala.{Some, None}
import scala.Some

abstract class GenericSchemaRepositorySpec[ID, SCHEMA] extends FlatSpec with ShouldMatchers {

  def generateRepo: GenericTestSchemaRepo[ID, SCHEMA]

  def idTypeToString: String
  def schemaTypeToString: String

  def id1: ID
  def id2: ID
  def id3: ID

  def schema1: SCHEMA
  def schema2: SCHEMA
  def schema3: SCHEMA

  val topicA = "TOPIC_A"
  val topicB = "TOPIC_B"

  s"A schema repo with $idTypeToString IDs and $schemaTypeToString Schemas" should "be able to register schemas and get them back by ID" in {
    val repo = generateRepo

    val idForSchema1: ID = repo.registerSchema(topicA, schema1)
    val idForSchema2: ID = repo.registerSchema(topicA, schema2)

    val retrievedIdForSchema1: Option[ID] = repo.getSchemaId(topicA, schema1)
    Some(idForSchema1) should be === retrievedIdForSchema1

    val retrievedIdForSchema2: Option[ID] = repo.getSchemaId(topicA, schema2)
    Some(idForSchema2) should be === retrievedIdForSchema2
    Some(idForSchema1) should not equal retrievedIdForSchema2
  }

  it should "be able to register schemas and get them back by schema (with consistent IDs)" in {
    val repo = generateRepo

    val idForSchema1: ID = repo.registerSchema(topicA, schema1)
    val idForSchema2: ID = repo.registerSchema(topicA, schema2)

    val retrievedSchemaForId1: Option[SCHEMA] = repo.getSchema(topicA, idForSchema1)
    Some(schema1) should be === retrievedSchemaForId1

    val retrievedSchemaForId2: Option[SCHEMA] = repo.getSchema(topicA, idForSchema2)
    Some(schema2) should be === retrievedSchemaForId2
    Some(schema1) should not equal retrievedSchemaForId2
  }

  it should "return None for schemas and IDs that don't exist" in {
    val repo = generateRepo

    val idForSchema1: ID = repo.registerSchema(topicA, schema1)
    val idForSchema2: ID = repo.registerSchema(topicA, schema2)

    val retrievedIdForSchema3: Option[ID] = repo.getSchemaId(topicA, schema3)
    retrievedIdForSchema3 should be (None)

    val retrievedSchemaForId3: Option[SCHEMA] = repo.getSchema(topicA, id3)
    retrievedSchemaForId3 should be (None)

    val retrievedIdForSchema1InTopicB: Option[ID] = repo.getSchemaId(topicB, schema3)
    retrievedIdForSchema1InTopicB should be (None)

    val retrievedIdForId1InTopicB: Option[SCHEMA] = repo.getSchema(topicB, idForSchema1)
    retrievedIdForId1InTopicB should be (None)
  }

  it should "return the same ID when registering duplicate schemas" in {
    val repo = generateRepo

    val idForSchema1: ID = repo.registerSchema(topicA, schema1)
    val idForSchema2: ID = repo.registerSchema(topicA, schema2)
    val otherIdForSchema1: ID = repo.registerSchema(topicA, schema1)
    val otherIdForSchema2: ID = repo.registerSchema(topicA, schema2)

    idForSchema1 should be === otherIdForSchema1
    idForSchema2 should be === otherIdForSchema2
  }
}