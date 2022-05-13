import mill._
import mill.scalalib._

object Dependencies {
  lazy val circe = {
    val version = "0.13.0"
    Agg(
      ivy"io.circe::circe-core:$version",
      ivy"io.circe::circe-generic:$version",
      ivy"io.circe::circe-parser:$version"
    )
  }

  lazy val kafkaStreams = Agg(ivy"org.apache.kafka::kafka-streams-scala:3.2.0")
}
