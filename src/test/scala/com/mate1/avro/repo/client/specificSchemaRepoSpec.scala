package com.mate1.avro.repo.client

import org.apache.avro.Schema

// String Schema

class SchemaRepoWithStringIdAndStringSchemaSpec extends GenericSchemaRepositorySpec[String, String] with StringSchemaIdSpec with StringSchemaSpec {
  override val repo = new GenericTestSchemaRepo[String, String] with idTrait with schemaTrait
}

class SchemaRepoWithShortIdAndStringSchemaSpec extends GenericSchemaRepositorySpec[Short, String] with ShortSchemaIdSpec with StringSchemaSpec {
  override val repo = new GenericTestSchemaRepo[Short, String] with idTrait with schemaTrait
}

class SchemaRepoWithIntegerIdAndStringSchemaSpec extends GenericSchemaRepositorySpec[Int, String] with IntegerSchemaIdSpec with StringSchemaSpec {
  override val repo = new GenericTestSchemaRepo[Int, String] with idTrait with schemaTrait
}

class SchemaRepoWithLongIdAndStringSchemaSpec extends GenericSchemaRepositorySpec[Long, String] with LongSchemaIdSpec with StringSchemaSpec {
  override val repo = new GenericTestSchemaRepo[Long, String] with idTrait with schemaTrait
}

// Avro Schema

class SchemaRepoWithStringIdAndAvroSchemaSpec extends GenericSchemaRepositorySpec[String, Schema] with StringSchemaIdSpec with AvroSchemaSpec {
  override val repo = new GenericTestSchemaRepo[String, Schema] with idTrait with schemaTrait
}

class SchemaRepoWithShortIdAndAvroSchemaSpec extends GenericSchemaRepositorySpec[Short, Schema] with ShortSchemaIdSpec with AvroSchemaSpec {
  override val repo = new GenericTestSchemaRepo[Short, Schema] with idTrait with schemaTrait
}

class SchemaRepoWithIntegerIdAndAvroSchemaSpec extends GenericSchemaRepositorySpec[Int, Schema] with IntegerSchemaIdSpec with AvroSchemaSpec {
  override val repo = new GenericTestSchemaRepo[Int, Schema] with idTrait with schemaTrait
}

class SchemaRepoWithLongIdAndAvroSchemaSpec extends GenericSchemaRepositorySpec[Long, Schema] with LongSchemaIdSpec with AvroSchemaSpec {
  override val repo = new GenericTestSchemaRepo[Long, Schema] with idTrait with schemaTrait
}