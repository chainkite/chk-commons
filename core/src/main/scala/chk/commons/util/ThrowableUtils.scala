package chk.commons.util

import chk.commons.{ChkException, ChkError}


trait ThrowableUtils {

  implicit class ThrowableOps(e: Throwable) {
    def toChkEx(ec: ChkError) = ChkException.wrap(e, ec)
  }

}

