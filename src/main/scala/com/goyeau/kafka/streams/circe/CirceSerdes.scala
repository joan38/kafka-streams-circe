package com.goyeau.kafka.streams.circe

import java.util

import io.circe.{Decoder, Encoder}
import io.circe.parser._
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.{Deserializer, Serde, Serdes, Serializer}

object CirceSerdes {

  implicit def serializer[T: Encoder]: Serializer[T] =
      new Serializer[T] {
        override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = ()
        override def serialize(topic: String, caseClass: T): Array[Byte] =
          implicitly[Encoder[T]].apply(caseClass).noSpaces.getBytes
        override def close(): Unit = ()
      }

  implicit def deserializer[T: Decoder]: Deserializer[T] =
    new Deserializer[T] {
      override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = ()
      override def deserialize(topic: String, data: Array[Byte]): T =
        Option(data).fold(null.asInstanceOf[T]) { data =>
          decode[T](new String(data)).fold(error => throw new SerializationException(error), identity)
        }
      override def close(): Unit = ()
    }

  implicit def serde[CC: Encoder: Decoder]: Serde[CC] = Serdes.serdeFrom(serializer, deserializer)
}
