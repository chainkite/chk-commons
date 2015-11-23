package my.chk.util


trait StringUtils {

  implicit class StringOps(underlying: String) {

    def isBlank: Boolean = Option(underlying) map (_.trim.isEmpty) getOrElse (true)

    /**
     * Converts underlying string to option
     * @return Some(string) if not empty, None if string is null or empty
     */
    def blankOpt: Option[String] = emptyOpt
    def emptyOpt: Option[String] = Option(underlying) filterNot (_.trim.isEmpty)

    /**
      * Converts underlying string to camelized string
      * @return
      */
    def camelize(delimiter: String = " ", removeDelimiter: Boolean = true): String = {
      if (underlying.isBlank) underlying
      else {
        val capitalized = (underlying split delimiter) map (_.capitalize)
        if (removeDelimiter) capitalized.mkString
        else capitalized.mkString(delimiter)
      }
    }

  }

}
