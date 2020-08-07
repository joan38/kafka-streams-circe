import $ivy.`com.goyeau::mill-git:0.1.1`
import $ivy.`com.goyeau::mill-scalafix:0.1.3`
import $ivy.`com.lihaoyi::mill-contrib-bsp:$MILL_VERSION`
import $ivy.`io.github.davidgregory084::mill-tpolecat:0.1.3`
import $file.project.Dependencies, Dependencies.Dependencies._
import com.goyeau.mill.git.GitVersionedPublishModule
import com.goyeau.mill.scalafix.StyleModule
import io.github.davidgregory084.TpolecatModule
import mill._
import mill.scalalib._
import mill.scalalib.publish.{Developer, License, PomSettings, VersionControl}

object `kafka-streams-circe` extends Cross[KubernetesClientModule]("2.13.2", "2.12.11")
class KubernetesClientModule(val crossScalaVersion: String)
    extends CrossScalaModule
    with TpolecatModule
    with StyleModule
    with GitVersionedPublishModule {
  override def ivyDeps = super.ivyDeps() ++ circe ++ kafkaStreams

  object test extends Tests {
    def testFrameworks    = Seq("org.scalatest.tools.Framework")
    override def forkArgs = super.forkArgs() :+ "-Djdk.tls.client.protocols=TLSv1.2"
    override def ivyDeps  = super.ivyDeps() ++ Agg(ivy"org.scalatest::scalatest:3.2.1")
  }

  override def artifactName = "kafka-streams-circe"
  def pomSettings =
    PomSettings(
      description = "Generic Serdes with Circe for Kafka Streams",
      organization = "com.goyeau",
      url = "https://github.com/joan38/kafka-streams-circe",
      licenses = Seq(License.`Apache-2.0`),
      versionControl = VersionControl.github("joan38", "kafka-streams-circe"),
      developers = Seq(Developer("joan38", "Joan Goyeau", "https://github.com/joan38"))
    )
}
