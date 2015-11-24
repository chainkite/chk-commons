package chk.commons.util

import scala.util._


trait TryUtils {

  implicit class TryOps[T](underlying: Try[T]) {

    /**
     * Converts to Either
     *
     * @return `Either`
     */
    def toEither: Either[Throwable, T] = underlying match {
      case Success(s) => Right(s)
      case Failure(e) => Left(e)
    }

    /**
     * Get underlying result or throw exception if failed
     * @return
     */
    def getOrThrow: T = underlying match {
      case Success(s) => s
      case Failure(e) => throw e
    }

  }

}
