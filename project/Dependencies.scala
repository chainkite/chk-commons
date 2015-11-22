import sbt._
import Keys._

object Dependencies {
  // val slf4j = "org.slf4j" % "slf4j-api" % "1.7.10"
  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"
  val slf4j = "org.slf4j" % "slf4j-api" % "1.7.12"
  val typesafeConfig = "com.typesafe" % "config" % "1.2.1"
  
  val scalatest = "org.scalatest" %% "scalatest" % "1.4.1" % "test"
}