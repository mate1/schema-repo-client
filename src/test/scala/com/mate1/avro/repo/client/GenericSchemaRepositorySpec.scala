package com.mate1.avro.repo.client

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import scala.{Some, None}
import scala.Some

abstract class GenericSchemaRepositorySpec[ID, SCHEMA] extends FlatSpec with ShouldMatchers {

  val repo: GenericTestSchemaRepo[ID, SCHEMA] // = new GenericTestSchemaRepo[ID, SCHEMA] with IdTrait[ID] with SchemaTrait[SCHEMA]

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

  s"A schema repo with $idTypeToString IDs and $schemaTypeToString schemas" should
    "be able to register schemas and give them back" in {
      val idForSchema1: ID = repo.registerSchema(topicA, schema1)
      val idForSchema2: ID = repo.registerSchema(topicA, schema2)

      val retrievedIdForSchema1: Option[ID] = repo.getSchemaId(topicA, schema1)
      val retrievedIdForSchema2: Option[ID] = repo.getSchemaId(topicA, schema2)
      val retrievedIdForSchema3: Option[ID] = repo.getSchemaId(topicA, schema3)

      val retrievedSchemaForId1: Option[SCHEMA] = repo.getSchema(topicA, idForSchema1)
      val retrievedSchemaForId2: Option[SCHEMA] = repo.getSchema(topicA, idForSchema2)
      val retrievedSchemaForId3: Option[SCHEMA] = repo.getSchema(topicA, id3)

      Some(idForSchema1) should be === retrievedIdForSchema1
      Some(idForSchema2) should be === retrievedIdForSchema2
      Some(idForSchema1) should not equal retrievedIdForSchema2

      Some(schema1) should be === retrievedSchemaForId1
      Some(schema2) should be === retrievedSchemaForId2
      Some(schema1) should not equal retrievedSchemaForId2

      retrievedIdForSchema3 should be (None)
      retrievedSchemaForId3 should be (None)

      // TODO: Add more tests..
    }
}