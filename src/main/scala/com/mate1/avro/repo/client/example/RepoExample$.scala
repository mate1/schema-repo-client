package com.mate1.avro.repo.client.example

import org.apache.avro.Schema
import com.mate1.avro.repo.client.{GenericSchemaRepository, ShortId, AvroSchema}

/**
 * Example implementation of the Schema repo client.
 */
object RepoExample$
  extends GenericSchemaRepository[Short, Schema]
  with ShortId
  with AvroSchema {

  def getRepositoryURL: String = "http://example.org"
}