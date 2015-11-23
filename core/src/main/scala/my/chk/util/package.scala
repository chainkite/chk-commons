package my.chk.util

package object util extends CommonUtils

/**
  * Includes All Implicits Trait
  */

trait CommonUtils
  extends AnyUtils
  with BooleanUtils
  with ClassUtils
  with MapUtils
  with OptionUtils
  with StringUtils
  with TryUtils
  with TraversableUtils
  with FutureUtils
  with SecurityUtils
  with URIUtils
  with ThrowableUtils