import scala.language.postfixOps
import sbt._
import Keys._

object ShellPromptPlugin {

  val devnull: ProcessLogger = new ProcessLogger {
    def info (s: => String) {}
    def error (s: => String) {}
    def buffer[T] (f: => T): T = f
  }

  def currBranch = ("git status -sb" lines_! devnull headOption).getOrElse("-").stripPrefix("## ").split("\\.\\.\\.")(0)

  def buildShellPrompt(version: String) = (state: State) => {
    import scala.Console.RESET
    val LYELLOW = "\033[1;33m"
    val LGREEN = "\033[1;32m"
    val LBLUE = "\033[01;34m"

    val currProject = Project.extract(state).currentProject.id

    "%s%s%s:%s%s%s:%s%s%s> ".format (
      LGREEN, currProject, RESET,
      LBLUE, version, RESET,
      LYELLOW, currBranch, RESET
    )
  }

}