import sbt._
import Keys._

object Dependencies {

  object V {
    final lazy val scalaLogging = "3.1.0"
    final lazy val slf4j = "1.7.12"
    final lazy val typesafeConfig = "1.2.1"
    final lazy val scalatest = "2.2.4"
    final lazy val scaldi = "0.5.6"
    final lazy val mavenArtifact = "3.3.3"
  }

  lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % V.scalaLogging
  lazy val slf4j = "org.slf4j" % "slf4j-api" % V.slf4j
  lazy val typesafeConfig = "com.typesafe" % "config" % V.typesafeConfig

  lazy val scalatest = "org.scalatest" %% "scalatest" % V.scalatest

  lazy val scaldiCore = "org.scaldi" %% "scaldi" % V.scaldi

  lazy val mavenArtifact = "org.apache.maven" % "maven-artifact" % V.mavenArtifact

}