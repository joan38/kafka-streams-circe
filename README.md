# Kafka Streams Circe

[![Latest version](https://index.scala-lang.org/joan38/kafka-streams-circe/kafka-streams-circe/latest.svg)](https://index.scala-lang.org/joan38/kafka-streams-circe/kafka-streams-circe)

Generic Serdes with [Circe](https://github.com/circe/circe) for [Kafka Streams](https://github.com/apache/kafka)

## Installation

```scala
libraryDependencies += "com.goyeau" %% "kafka-streams-circe" % "<latest version>"
```


## Example

```scala
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.Serdes._
import com.goyeau.kafka.streams.circe.CirceSerdes._
import io.circe.generic.auto._

case class Person(firstname: String, lastname: String, age: Int)

object Streams extends App {
  println("Starting streams")

  val streamsBuilder = new StreamsBuilder
  val testStream = streamsBuilder.stream[String, Person]("some-topic")
}
```
