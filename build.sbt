import Dependencies._
import BuildSettings._

lazy val jvmSource = "1.8"
lazy val jvmTarget = "1.8"

lazy val commonDeps = Seq(
  slf4j,
  scalaLogging,
  typesafeConfig,
  scalatest
)

lazy val `chk-commons-core` = (project in file("core")).
  settings(buildSettings: _*).
  settings(
    libraryDependencies ++= commonDeps
  )
