package com.mate1.avro.repo.client

trait ShortId {
  def idToString(id: Short): String = id.toString
  def stringToId(id: String): Short = id.toShort
}

trait IntegerId {
  def idToString(id: Int): String = id.toString
  def stringToId(id: String): Int = id.toInt
}

trait LongId {
  def idToString(id: Long): String = id.toString
  def stringToId(id: String): Long = id.toLong
}

trait StringId {
  def idToString(id: String): String = id
  def stringToId(id: String): String = id
}