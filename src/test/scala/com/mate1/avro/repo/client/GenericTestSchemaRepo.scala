package com.mate1.avro.repo.client

import org.apache.avro.repo.{ValidatorFactory, InMemoryRepository}

trait GenericTestSchemaRepo[ID, SCHEMA]
  extends GenericSchemaRepository[ID, SCHEMA] {

   def getRepositoryURL: String = "http://example.org"

   override lazy val client = new InMemoryRepository(new ValidatorFactory.Builder().build())
}