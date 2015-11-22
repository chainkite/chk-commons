import sbt._
import Keys._

object BuildSettings extends ShellPromptPlugin {

  lazy val ver = "0.1.0-SHAPSHOT"

  lazy val jvmSource = "1.8"
  lazy val jvmTarget = "1.8"

  lazy val buildSettings = Seq(
    organization := "com.chn",
    version := ver,
    scalaVersion := "2.11.8",
    shellPrompt := buildShellPrompt(version.value),
    unmanagedJars in Compile <<= baseDirectory.map(base => (base / "lib" ** "*.jar").classpath),
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:higherKinds", "-language:postfixOps",
      "-language:implicitConversions", "-language:reflectiveCalls", "-language:existentials", s"-target:jvm-$jvmTarget",
      "-encoding", "utf8"),
    javacOptions in Compile ++= Seq("-target", jvmTarget, "-source", jvmSource, "-Xlint:deprecation")
  )

}