package my.chk.util


trait BooleanUtils {

  implicit class BooleanOps(underlying: Boolean) {

    /**
     * Convert Boolean to Option
     *
     * @param f block to execute if underlying is true
     * @tparam T
     * @return Option value if true, None if false
     */
    def toOption[T](f: => T): Option[T] = {
      if(underlying) Option(f) else None
    }

  }

}
