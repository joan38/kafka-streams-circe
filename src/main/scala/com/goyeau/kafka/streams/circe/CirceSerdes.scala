package com.goyeau.kafka.streams.circe

import java.nio.charset.StandardCharsets
import java.util

import io.circe.parser._
import io.circe.{Decoder, Encoder}
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.{Deserializer, Serde, Serdes, Serializer}

/**
 * By default json is serialized using Printer.noSpaces.
 *
 * If you want to custom serialization, instead of import CirceSerdes you could write something like:
 *
 * <pre>
 * val serdes = CirceSerdes(Printer(
 *  dropNullValues = true,
 *  indent = ""
 * ))
 * import serdes._
 * </pre>
 *
 * @param printer
 */
class CirceSerdes(printer: Printer) {

  implicit def serializer[T: Encoder]: Serializer[T] =
    new Serializer[T] {
      override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = ()
      override def serialize(topic: String, caseClass: T): Array[Byte] =
        printer.print(Encoder[T].apply(caseClass)).getBytes(StandardCharsets.UTF_8)
      override def close(): Unit = ()
    }

  implicit def deserializer[T: Decoder]: Deserializer[T] =
    new Deserializer[T] {
      override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = ()
      override def deserialize(topic: String, data: Array[Byte]): T =
        Option(data).fold(null.asInstanceOf[T]) { data =>
          decode[T](new String(data, StandardCharsets.UTF_8))
            .fold(error => throw new SerializationException(error), identity)
        }
      override def close(): Unit = ()
    }

  implicit def serde[CC: Encoder: Decoder]: Serde[CC] = Serdes.serdeFrom(serializer, deserializer)
}

object CirceSerdes extends CirceSerdes(Printer.noSpaces) {
  def apply(printer: Printer) = new CirceSerdes(printer)
}
