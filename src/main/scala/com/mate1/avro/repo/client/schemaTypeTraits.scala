package com.mate1.avro.repo.client

import org.apache.avro.Schema


trait AvroSchema {
  val parser = new Schema.Parser()

  def schemaToString(schema: Schema): String = schema.toString(true)
  def stringToSchema(schema: String): Schema = parser.parse(schema)
}

trait StringSchema {
  def schemaToString(schema: String): String = schema
  def stringToSchema(schema: String): String = schema
}