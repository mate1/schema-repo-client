package com.mate1.avro.repo.client

import org.apache.avro.Schema

class TestSchemaRepoForShortIdAndAvro
  extends GenericTestSchemaRepo[Short, Schema]
  with ShortId
  with AvroSchema