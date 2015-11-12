import sbt._
import Keys._


object BuildSettings {
  val kitchenVersion = "0.0.1-SNATSHOT"
  val buildSettings = Defaults.defaultSettings ++ Seq(
    version := kitchenVersion,
    scalaVersion := "2.11.7",
    crossScalaVersions := Seq("2.11.5", "2.11.6", "2.11.7"),
    resolvers += Resolver.sonatypeRepo("snapshots"),
    resolvers += Resolver.sonatypeRepo("releases"),
    shellPrompt  := buildShellPrompt,
    scalacOptions ++= Seq()
  )

  object ShellPrompt {
    object devnull extends ProcessLogger {
      def info (s: => String) {}
      def error (s: => String) { }
      def buffer[T] (f: => T): T = f
    }
    def currBranch = {
      ("git status -sb" lines_! devnull headOption)
        getOrElse "-" stripPrefix "## "
    }
    val buildShellPrompt = (state: State) => {
      val currProject = Project.extract (state).currentProject.id
      "%s:%s:%s> ".format (
        currProject, currBranch, BuildSettings.buildVersion
      )
    }
  }
}


object CommonsBuild extends Build {



}
