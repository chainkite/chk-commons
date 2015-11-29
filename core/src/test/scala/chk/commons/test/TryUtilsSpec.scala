package chk.commons.test

import chk.commons.util.TryUtils

import scala.language.postfixOps
import scala.util._


class TryUtilsSpec extends TryUtils with UnitSpec {

  def successMethod = "ok"
  def failureMethod = throw new IllegalArgumentException("Test failure method")

  behavior of "Try Utils"

  it should "transform to either right if success" in {
    val t = (Try { successMethod } toEither)
    t should be('right)
    t should be(Right("ok"))
  }

  it should "transform to either left if failure" in {
    val t = (Try { failureMethod } toEither)
    t should be('left)
    t.left.get.isInstanceOf[IllegalArgumentException] should be(true)
  }

  it should "get underlying object if success" in {
    val t = Try { successMethod } getOrThrow;
    t should equal(successMethod)
  }

  it should "throw exception if failure" in {
    an [IllegalArgumentException] should be thrownBy {
      Try { failureMethod } getOrThrow
    }

  }

}
