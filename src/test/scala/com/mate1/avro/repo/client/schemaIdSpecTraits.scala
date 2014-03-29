package com.mate1.avro.repo.client

trait IdSpec {
  type idTrait
}

trait ShortSchemaIdSpec extends IdSpec {
  type idTrait = ShortId
  def idTypeToString: String = "Short"
  def id1: Short = 1
  def id2: Short = 2
  def id3: Short = 3
}

trait IntegerSchemaIdSpec extends IdSpec {
  type idTrait = IntegerId
  def idTypeToString: String = "Integer"
  def id1: Int = 1
  def id2: Int = 2
  def id3: Int = 3
}

trait LongSchemaIdSpec extends IdSpec {
  type idTrait = LongId
  def idTypeToString: String = "Long"
  def id1: Long = 1
  def id2: Long = 2
  def id3: Long = 3
}

trait StringSchemaIdSpec extends IdSpec {
  type idTrait = StringId
  def idTypeToString: String = "String"
  def id1: String = "1"
  def id2: String = "2"
  def id3: String = "3"
}