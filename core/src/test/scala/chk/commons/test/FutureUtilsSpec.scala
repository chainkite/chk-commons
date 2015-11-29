package chk.commons.test

import chk.commons.{ChkException, SystemError}
import chk.commons.util.FutureUtils

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/*
 * Created by wxk on 11/29/15 at 5:06 PM
 */
class FutureUtilsSpec extends FutureUtils with UnitSpec {

  def success = Future(1)
  def failure = Future(throw ChkException(SystemError.INTERNAL_ERROR))

  behavior of "Future Utils"

  it should "recover by" in {
    Await.result(failure.recoverBy(2), 10 seconds) should be (2)
  }

  it should "flatten future" in {
    Await.result(Future(success).flatten, 10 seconds) should be (1)
  }

}
