package chk.commons.util

import scala.concurrent.Future
import scala.language.implicitConversions
import scala.reflect.ClassTag


trait AnyUtils {

  implicit class AnyRefOps[T <: AnyRef](underlying: T) {

    /**
     * Check if the instance is null
     *
     * @return true if null, false otherwise.
     */
    def isNull: Boolean = underlying eq null

  }

  implicit class AnyOps[T <: Any](underlying: T)(implicit tag: ClassTag[T]) {

    /**
     * Wrap to Option
     * @return
     */
    def option: Option[T] = Option(underlying)

    /**
     * Wrap to Future
     * @return
     */
    def future: Future[T] = Future.successful(underlying)

    /**
     * Helper for chaining function call. Just link Unix tee command. It always returns the object it’s called on.
     *
     * @param f block to execute the object
     * @return the object it's called on
     */
    def tap(f: T => Unit): T = {
      f(underlying)
      underlying
    }

    /** alias of tap */
    def >|(f: T => Unit): T = tap(f)

    /** check null before calling tap */
    def tapNotNull(f: T => Unit): T = {
      if(underlying != null) tap(f)
      else underlying
    }

    /** alias of tapNotNull */
    def ?>|(f: T => Unit): T = tapNotNull(f)

    /**
     * Helper for chaining function call. Just link Unix pipeline.
     *
     * @param f block to execute the object
     * @return the object after the block execute
     */
    def pipe[That](f: T => That): That = f(underlying)

    /** alias of pipe */
    def |[That](f: T => That): That = pipe(f)

  }

  implicit def anyToOption[T <: Any](underlying: T): Option[T] = Option(underlying)
  implicit def anyToFuture[T <: Any](underlying: T): Future[T] = Future.successful(underlying)
  implicit def anyToString[T <: Any](underlying: T): String = underlying.toString
}
