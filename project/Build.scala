import sbt._
import sbt.Keys._

object ApplicationBuild extends Build {

  val projectName = "schema-repo-client"
  val scalaVersionValue = "2.10.2"

  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization        := "com.mate1.avro",
    version             := "0.1-SNAPSHOT",
    scalaVersion        := scalaVersionValue,
    libraryDependencies := Seq(
      "org.apache.avro" % "avro" % "1.7.5",
      "org.apache.avro.repo" % "avro-repo-client" % "1.7.5-1124-SNAPSHOT",
      "org.scala-lang" % "scala-library" % scalaVersionValue,
      "com.google.code.findbugs" % "jsr305" % "1.3.9",
      "com.google.guava" % "guava" % "14.0.1",
      "javax.inject" % "javax.inject" % "1"
    ),
    resolvers ++= Seq(
      "Mate1 Public Maven Repo" at "https://raw.github.com/mate1/maven/master/public/"
    )
  )

  val project = Project(
    id = projectName,
    base = file("."),
    settings = buildSettings
  )
}