name := "Kafka Streams Circe"
organization := "com.goyeau"
scalaVersion := "2.12.10"

ThisBuild / dynverSonatypeSnapshots := true

libraryDependencies ++= circe ++ kafkaStreams

licenses += "APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0")
homepage := Option(url("https://github.com/joan38/kafka-streams-circe"))
scmInfo := Option(
  ScmInfo(
    url("https://github.com/joan38/kafka-streams-circe"),
    "https://github.com/joan38/kafka-streams-circe.git"
  )
)
developers += Developer(id = "joan38", name = "Joan Goyeau", email = "joan@goyeau.com", url = url("http://goyeau.com"))
publishTo := Option(
  if (isSnapshot.value) Opts.resolver.sonatypeSnapshots
  else Opts.resolver.sonatypeStaging
)

lazy val kafkaStreams = Seq("org.apache.kafka" %% "kafka-streams-scala" % "2.3.1")

lazy val circe = {
  val version = "0.12.3"
  Seq(
    "io.circe" %% "circe-core" % version,
    "io.circe" %% "circe-parser" % version,
    "io.circe" %% "circe-generic" % version % Test
  )
}
