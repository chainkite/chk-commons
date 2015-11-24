package chk.commons.util


trait ClassUtils extends OptionUtils {

  protected def getInstanceForName[T](name: String): Option[T] = {
    tryCatchOpt(Class.forName(name).newInstance().asInstanceOf[T])
  }

}