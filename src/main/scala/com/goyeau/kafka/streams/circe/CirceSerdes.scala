package com.goyeau.kafka.streams.circe

import java.util

import io.circe.{Decoder, Encoder}
import io.circe.parser._
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.{Deserializer, Serde, Serdes, Serializer}

object CirceSerdes {

  implicit def serializer[CC >: Null: Encoder]: Serializer[CC] =
      new Serializer[CC] {
        override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = ()
        override def serialize(topic: String, caseClass: CC): Array[Byte] =
          implicitly[Encoder[CC]].apply(caseClass).noSpaces.getBytes
        override def close(): Unit = ()
      }

  implicit def deserializer[CC >: Null: Decoder]: Deserializer[CC] =
    new Deserializer[CC] {
      override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = ()
      override def deserialize(topic: String, data: Array[Byte]): CC =
        decode[CC](new String(data)).fold(error => throw new SerializationException(error), identity)
      override def close(): Unit = ()
    }

  implicit def serde[CC >: Null: Encoder: Decoder]: Serde[CC] =
    Serdes.serdeFrom(serializer, deserializer)
}
