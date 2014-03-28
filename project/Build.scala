import sbt._
import sbt.Keys._

object ApplicationBuild extends Build {

  val projectName = "schema-repo-client"
  val scalaVersionValue = "2.10.2"

  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization        := "Mate1",
    version             := "0.1-SNAPSHOT",
    scalaVersion        := scalaVersionValue,
    libraryDependencies := Seq(
      "org.apache.avro" % "avro" % "1.7.5",
      "org.scala-lang" % "scala-library" % scalaVersionValue,
      "com.google.guava" % "guava" % "14.0.1",
      "javax.inject" % "javax.inject" % "1",
      "com.google.code.findbugs" % "jsr305" % "1.3.+"
    )
  )

  val project = Project(
    id = projectName,
    base = file("."),
    settings = buildSettings
  )
}