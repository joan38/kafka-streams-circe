# Kafka Streams Circe

Generic Serdes with [Circe](https://github.com/circe/circe) for [Kafka Streams](https://github.com/apache/kafka)

## Example

```scala
import com.lightbend.kafka.scala.streams.DefaultSerdes._
import com.lightbend.kafka.scala.streams.ImplicitConversions._
import com.lightbend.kafka.scala.streams.StreamsBuilderS
import com.goyeau.kafka.streams.circe.CaseClassSerdes._
import io.circe.generic.auto._

case class Person(firstname: String, lastname: String, age: Int)

object Streams extends App {
  println("Starting streams")

  val streamsBuilder = new StreamsBuilderS()
  val testStream = streamsBuilder.stream[String, Person]("some-topic")
}
```
