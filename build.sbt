import Dependencies._
import ShellPromptPlugin._


lazy val VERSION = "0.1.0"

lazy val jvmSource = "1.8"
lazy val jvmTarget = "1.8"


lazy val commonDeps = Seq(
  slf4j,
  scalaLogging,
  typesafeConfig,
  scalatest
)

lazy val depOverrides = Set(
  slf4j,
  typesafeConfig,
  scalatest
)

lazy val buildSettings = Seq(
  organization := "chainkite",
  version in ThisBuild := VERSION,
  scalaVersion := "2.11.7",
  shellPrompt := buildShellPrompt(version.value),
  unmanagedJars in Compile <<= baseDirectory.map(base => (base / "lib" ** "*.jar").classpath),
  scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:higherKinds", "-language:postfixOps",
    "-language:implicitConversions", "-language:reflectiveCalls", "-language:existentials", s"-target:jvm-$jvmTarget",
    "-encoding", "utf8"),
  javacOptions in Compile ++= Seq("-target", jvmTarget, "-source", jvmSource, "-Xlint:deprecation"),
  resolvers += "Chainkite Repository" at "http://dl.bintray.com/chainkite/maven",
  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
  bintrayReleaseOnPublish in ThisBuild := false
)

lazy val `chk-commons` = (project in file(".")).
  settings(buildSettings: _*).
  settings(publishArtifact := false).
  aggregate(`chk-commons-core`, `chk-commons-test`)

lazy val `chk-commons-core` = (project in file("core")).
  settings(buildSettings: _*).
  settings(
    libraryDependencies ++= commonDeps ++ Seq(
      commonsCodec, scaldiCore % Provided, mavenArtifact % Provided.intransitive
    ),
    dependencyOverrides ++= depOverrides
  ).dependsOn(`chk-commons-test`)

lazy val `chk-commons-test` = (project in file("test")).
  settings(buildSettings: _*).
  settings(
    libraryDependencies ++= commonDeps,
    dependencyOverrides ++= depOverrides
  )