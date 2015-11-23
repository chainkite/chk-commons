package my.chk.util

import scala.util.control.Exception._


trait OptionUtils {

  /**
   * Catch any exception occurred in the function block as Option
   *
   * @param func
   * @tparam T
   * @return Some[T] if function run successfully, None if throws Exception
   */
  protected def tryCatchOpt[T](func: => T): Option[T] = handling[Option[T]](classOf[Throwable]) by (_ => None) apply Option(func)
//  protected def tryCatchOpt[T, E](func: => T): Option[T] = handling[Option[T]](classOf[E]) by (_ => None) apply Option(func)

  implicit class OptionOps[T](underlying: Option[T]) {

    /**
     * Returns the option's value if the option is nonempty, otherwise
     * throws the result of evaluating `throwable`.
     *
     * @param throwable Exception expression
     * @return Option's value or throws Exception
     */
    @throws[Throwable]
    def getOrThrow(throwable: => Throwable): T = underlying.getOrElse(throw throwable)

    /**
     * Helper for chaining function call. Just link Unix tee command. It runs the block only if Option is Some.
     *
     * @param f block to execute the object
     * @return the object it's called on
     */
    def tapSome(f: T => Unit): Option[T] = {
      if(underlying.isDefined) f(underlying.get)
      underlying
    }

    /**
     * Helper for chaining function call. Just link Unix tee command. It runs the block only if Option is None.
     *
     * @param f block to execute the object
     * @return the object it's called on
     */
    def tapNone(f: => Unit): Option[T] = {
      if(underlying.isEmpty) f
      underlying
    }

  }

}
