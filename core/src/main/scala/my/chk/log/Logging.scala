package my.chk.log

import com.typesafe.scalalogging._

trait Logging[T] {
  protected def logger: T
}

trait Slf4jLazyLogging extends LazyLogging with Logging[Logger]
trait Slf4jStrictLogging extends StrictLogging with Logging[Logger]