package chk.commons.config

import java.io._
import com.typesafe.config._
import chk.commons.{SystemError, ChkException}
import chk.commons.log.Slf4jLazyLogging

object Configuration extends Slf4jLazyLogging {

  def setConfigFile(path: String) { System.setProperty("config.file", path) }
  def setConfigResource(resource: String) { System.setProperty("config.resource", resource) }

  /**
   * Loads a new `Configuration` either from the classpath or from `application.conf`
   *
   * @return a `Configuration` instance
   */
  def load(): Config = {
    try {
      val fileConfig = Option(System.getProperty("config.file")).map(f => ConfigFactory.parseFileAnySyntax(new File(f)))
      Option(System.getProperty("config.resource")).map { r =>
        logger.info(s"Loading Resource Config [$r]...")
        ConfigFactory.parseResourcesAnySyntax(r)
      }.orElse {
        fileConfig.foreach { f =>
          logger.info(s"Loading File Config [${f.origin().url()}]...")
        }
        fileConfig
      }.getOrElse {
        logger.info("Loading Default Config...")
        ConfigFactory.load()
      }
    } catch {
      case e: ConfigException => throw ChkException(SystemError.CONFIG_ERROR, e).set("origin", e.origin)
      case e: Throwable => throw e
    }
  }

  def load(path: File): Config = {
    try {
      setConfigFile(path.getAbsolutePath)
      logger.info(s"Loading Config [${path.getAbsolutePath}]...")
      ConfigFactory.parseFileAnySyntax(path)
    } catch {
      case e: ConfigException => throw ChkException(SystemError.CONFIG_ERROR, e).set("origin", e.origin)
      case e: Throwable => throw e
    }
  }

  /**
   * Returns an empty Configuration object.
   */
  def empty = ConfigFactory.empty()

}

