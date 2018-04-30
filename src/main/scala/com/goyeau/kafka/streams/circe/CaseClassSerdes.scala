package com.goyeau.kafka.streams.circe

import com.lightbend.kafka.scala.streams.StatelessScalaSerde
import io.circe.{Decoder, Encoder}
import io.circe.parser._
import org.apache.kafka.common.serialization.Serde

object CaseClassSerdes {

  implicit def caseClassSerde[CC >: Null: Encoder: Decoder]: Serde[CC] =
    new StatelessScalaSerde[CC] {
      override def serialize(caseClass: CC): Array[Byte] =
        implicitly[Encoder[CC]].apply(caseClass).noSpaces.getBytes

      override def deserialize(caseClass: Array[Byte]): Option[CC] =
        parse(new String(caseClass))
          .flatMap(implicitly[Decoder[CC]].decodeJson(_))
          .toOption
    }
}
