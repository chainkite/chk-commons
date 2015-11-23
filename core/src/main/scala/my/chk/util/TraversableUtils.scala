package my.chk.util

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


trait TraversableUtils {

  implicit class TraversableOps[T <: Traversable[_]](underlying: T) {

    /** Converts underlying traversable to option
     *  @return Some() if not empty, None if traversable is empty
     */
    def emptyOpt: Option[T] = {
      if(underlying.isEmpty) None
      else Some(underlying)
    }

  }

  implicit class FutureTraversableOps[T](underlying: Traversable[Future[T]]) {

    /** Same as `Future.sequence`, transform `Traversable[Future[T]]` into `Future[Traversable[T]]`
     *  @return
     */
    def flattenFuture: Future[Traversable[T]] = Future.sequence(underlying)
  }
}
