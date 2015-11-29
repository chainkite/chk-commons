package chk.commons.test

import chk.commons.ChkException

class ChkExceptionSpec extends UnitSpec {

  behavior of "Chk Exception"

  it should "get by class tag" in {
    val ex = new ChkException("code", "msg", Some("details"), None)
    val obj = TestObject("test")
    ex += ("key", obj)
    ex.get[String]("key") should be (None)
    ex.get[TestObject]("obj") should be (None)
    ex.get[TestObject]("key") should be (Some(obj))
  }

}

case class TestObject(s: String)