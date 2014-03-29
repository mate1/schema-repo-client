package com.mate1.avro.repo.client

class TestSchemaRepoForStringIdAndString
  extends GenericTestSchemaRepo[String, String]
  with StringId
  with StringSchema