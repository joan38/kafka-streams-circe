name := "Kafka Streams Circe"
organization := "com.goyeau"
scalaVersion := "2.13.1"
crossScalaVersions := Seq(scalaVersion.value, "2.12.10")
Global / onChangedBuildSource := ReloadOnSourceChanges
ThisBuild / turbo := true
ThisBuild / libraryDependencies += compilerPlugin(scalafixSemanticdb)
addCommandAlias("style", "compile:scalafix; test:scalafix; compile:scalafmt; test:scalafmt; scalafmtSbt")
addCommandAlias(
  "styleCheck",
  "compile:scalafix --check; test:scalafix --check; compile:scalafmtCheck; test:scalafmtCheck; scalafmtSbtCheck"
)

libraryDependencies ++= circe ++ kafkaStreams

ThisBuild / dynverSonatypeSnapshots := true
licenses += "APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0")
homepage := Option(url("https://github.com/joan38/kafka-streams-circe"))
scmInfo := Option(
  ScmInfo(
    url("https://github.com/joan38/kafka-streams-circe"),
    "https://github.com/joan38/kafka-streams-circe.git"
  )
)
developers += Developer(id = "joan38", name = "Joan Goyeau", email = "joan@goyeau.com", url = url("http://goyeau.com"))
publishTo := sonatypePublishToBundle.value

lazy val kafkaStreams = Seq("org.apache.kafka" %% "kafka-streams-scala" % "2.4.0")

lazy val circe = {
  val version = "0.13.0"
  Seq(
    "io.circe" %% "circe-core"    % version,
    "io.circe" %% "circe-parser"  % version,
    "io.circe" %% "circe-generic" % version % Test
  )
}
