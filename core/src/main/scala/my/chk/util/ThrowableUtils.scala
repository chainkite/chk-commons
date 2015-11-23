package my.chk.util

import
import my.chk.{ChkException, ChkError}


trait ThrowableUtils {

  implicit class ThrowableOps(e: Throwable) {
    def toMunerisEx(ec: ChkError) = ChkException.wrap(e, ec)
  }

}

