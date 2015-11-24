package chk.commons.util

import scala.concurrent.{Promise, ExecutionContext, Future}
import scala.util.Try


trait FutureUtils {

  def serializeFutures[A, B](l: Iterable[A])(fn: A => Future[B])(implicit ec: ExecutionContext): Future[List[B]] = {
    l.foldLeft(Future(List.empty[B])) { (prevFuture, next) =>
      for {
        prevResult <- prevFuture
        next <- fn(next)
      } yield prevResult :+ next
    }
  }

  implicit class FutureOps[T](f: Future[T]) {

    /**
      * Catch all exception with provided recovered object
      * @param obj recovered object
      * @param ec Execution Context
      * @return Future with recovered object if exception occurs
      */
    def recoverBy(obj: T)(implicit ec: ExecutionContext): Future[T] = {
      f.recoverWith {
        case e: Throwable => obj
      }
    }

    /**
      * Catch all exceptions with provided recovered function
      * @param func recovered function
      * @param ec Execution Context
      * @return Future with recovered object if exception occurs
      */
    def recoverBy(func: Throwable => T)(implicit ec: ExecutionContext): Future[T] = {
      f.recoverWith {
        case e: Throwable => func(e)
      }
    }

    /** Perform same as the Future.onComplete, but return with Future[U] instead of Unit
      * @param func
      * @param ec
      * @tparam U
      * @return Future with object result in partial function
      */
    def mapAll[U](func: PartialFunction[Try[T], U])(implicit ec: ExecutionContext): Future[U] = {
      val p = Promise[U]()
      f.onComplete(r => p.complete(Try(func(r))))
      p.future
    }

    /** Perform same as the Future.onComplete, but return with Future[U] instead of Unit
      * @param func
      * @param ec
      * @tparam U
      * @return Future with object result in partial function
      */
    def flatMapAll[U](func: PartialFunction[Try[T], Future[U]])(implicit ec: ExecutionContext): Future[U] = {
      val p = Promise[U]()
      f.onComplete { r =>
        func(r).map { fr => p.complete(Try(fr)) }
      }
      p.future
    }

  }

  implicit class FuturesOps[T](f: Future[Future[T]]) {

    /**
      * Flatten Future in Future
      * @return Future[T]
      */
    def flatten(implicit ec: ExecutionContext) = f.flatMap(identity)

  }

}