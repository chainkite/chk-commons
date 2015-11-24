package chk.commons

import scala.collection.mutable
import scala.reflect._
import scala.util.Try

class ChkException(val code: String, val message: String, val details: Option[String] = None, val cause: Option[Throwable] = None, _params: Map[String, Any] = Map.empty[String, Any])
  extends RuntimeException(s"$message ${details.fold("")(s => s"($s)")}", cause.orNull) {

  private val _properties: mutable.HashMap[String, Any] = mutable.HashMap(_params.toSeq:_*)

  def params = _properties.toMap

  def +=(key: String, value: Any) = set(key, value)

  def set(key: String, value: Any): ChkException = {
    _properties += (key -> value)
    this
  }

  def ++=(map: Map[String, Any]) = set(map)

  def set(map: Map[String, Any]): ChkException = {
    _properties ++= map
    this
  }

  def get[T: ClassTag](key: String): Option[T] = {
    _properties.get(key).flatMap { v =>
      if(classTag[T].runtimeClass.isInstance(v)) Some(v.asInstanceOf[T])
      else None
    }
  }
}

object ChkException {

  def apply(e: ChkError) = new ChkException(e.code, e.error)
  def apply(e: ChkError, details: String) = new ChkException(e.code, e.error, Option(details))
  def apply(e: ChkError, cause: Throwable) = new ChkException(e.code, e.error, cause = Option(cause))
  def apply(e: ChkError, cause: Option[Throwable]) = new ChkException(e.code, e.error, cause = cause)
  def apply(e: ChkError, details: String, cause: Throwable) = new ChkException(e.code, e.error, Option(details), Option(cause))

  def wrap(throwable: Throwable, e: ChkError): ChkException = {
    throwable match {
      case e: ChkException => e
      case _ => this (e, throwable)
    }
  }

  @throws[ChkException]
  def chkEx[T](f: => T): T = chkEx(SystemError.INTERNAL_ERROR)(f)

  /**
    * Try and catch all exception occur in the function block, all exception would wrap into ChkException
    * @param ec [[ChkError]]
    * @param f Function Block
    * @tparam T
    * @throws [[ChkException]]
    * @return
    */
  @throws[ChkException]
  def chkEx[T](ec: ChkError)(f: => T): T = {
    Try(f).recoverWith {
      case e: Throwable => throw ChkException.wrap(e, ec)
    }.get
  }
}


case class ChkExceptions (exceptions: List[Throwable]) extends RuntimeException {
  def ::(throwable: Throwable) = this.copy(exceptions.::(throwable))
  def :+(throwable: Throwable) = this.copy(exceptions :+ throwable)
  def +:(throwable: Throwable) = this.copy(exceptions.+:(throwable))
  def ++(throwables: List[Throwable]) = this.copy(exceptions ++ throwables)
}
