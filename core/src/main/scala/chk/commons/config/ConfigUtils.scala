package chk.commons.config

import java.util.Properties
import java.util.concurrent.TimeUnit
import chk.commons.{SystemError, ChkException}
import chk.commons.di.Injection

import collection.JavaConverters._
import scala.language.existentials
import com.typesafe.config._
import scala.util.control.Exception._
import scaldi._

trait ConfigPrefixInjector extends ConfigUtils with Injection {
  def configPrefix: String
  lazy val config = inject[Config] (identified by configPrefix is by default Configuration.empty)
}

class ConfigModule(config: Config) extends Module {
  bind [Config] to config
}

trait ConfigUtils {
  
  implicit class ConfigOps(underlying: Config) {
    private def option[T](body: => T) = handling[Option[T]](classOf[ConfigException.Missing]) by (_ => None) apply Option(body)

    def getStringOpt(path: String) = option(underlying.getString(path))
    def getStringListOpt(path: String) = option(underlying.getStringList(path))

    def getIntOpt(path: String) = option(underlying.getInt(path))
    def getIntListOpt(path: String) = option(underlying.getIntList(path))

    def getLongOpt(path: String) = option(underlying.getLong(path))
    def getLongListOpt(path: String) = option(underlying.getLongList(path))

    def getDoubleOpt(path: String) = option(underlying.getDouble(path))
    def getDoubleListOpt(path: String) = option(underlying.getDoubleList(path))

    def getNumberOpt(path: String) = option(underlying.getNumber(path))
    def getNumberListOpt(path: String) = option(underlying.getNumberList(path))

    def getDurationOpt(path: String, unit: TimeUnit) = option(underlying.getDuration(path, unit))
    def getDurationListOpt(path: String, unit: TimeUnit) = option(underlying.getDurationList(path, unit))

    def getBooleanOpt(path: String) = option(underlying.getBoolean(path))
    def getBooleanListOpt(path: String) = option(underlying.getBooleanList(path))

    def getConfigOpt(path: String) = option(underlying.getConfig(path))
    def getConfigListOpt(path: String) = option(underlying.getConfigList(path))

    def getValueOpt(path: String) = option(underlying.getValue(path))

    def keys: Set[String] = underlying.entrySet().asScala.map(_.getKey).toSet
    def subKeys: Set[String] = underlying.root().keySet().asScala.toSet

    def toProperties: Properties = {
      val prop = new Properties()
      underlying.subKeys.foreach { key =>
        val value = underlying.getString(key)
        prop.setProperty(key, value)
      }
      prop
    }

    private[commons] def getOriginDescription(origin: ConfigOrigin) = {
      val line = origin.lineNumber()
      val input = Option(origin.url).map(_.toURI.getPath).orNull
      val source = Option(origin.filename).orNull
      s"(line = $line, input = $input, source = $source)"
    }

    def reportError(path: String, message: String, cause: Option[Throwable] = None): ChkException = {
      val origin = underlying.getValueOpt(path).map(_.origin()).getOrElse(underlying.root().origin())
      ChkException(SystemError.CONFIG_ERROR, cause).set("origin", origin)
    }

    def globalError(message: String, cause: Option[Throwable] = None): ChkException = {
      ChkException(SystemError.CONFIG_ERROR, cause).set("origin", underlying.root().origin())
    }
  }
}
