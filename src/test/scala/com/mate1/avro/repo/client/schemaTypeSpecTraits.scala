package com.mate1.avro.repo.client

import org.apache.avro.Schema
import com.mate1.avro.example.records._

trait SchemaSpec {
  type schemaTrait
}

trait AvroSchemaSpec extends SchemaSpec {
  type schemaTrait = AvroSchema
  def schemaTypeToString: String = "Avro"
  def schema1: Schema = DummyLog.SCHEMA$
  def schema2: Schema = DummyLog2.SCHEMA$
  def schema3: Schema = DummyLog3.SCHEMA$
}

trait StringSchemaSpec extends SchemaSpec {
  type schemaTrait = StringSchema
  def schemaTypeToString: String = "String"
  def schema1: String = "blah blah schema1"
  def schema2: String = "blah blah schema2"
  def schema3: String = "blah blah schema3"
}