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

  it should "retry" in {
    an [ChkException] should be thrownBy (Await.result(failure.retry(3)(failure), 10 seconds))
    Await.result(failure.map(_ => 0).retry(3)(success), 10 seconds) should be (1)
    Await.result(success.retry(3)(failure), 10 seconds) should be (1)

  }

}
