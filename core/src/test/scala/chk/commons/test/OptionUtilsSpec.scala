package chk.commons.test

import chk.commons.util.OptionUtils


class OptionUtilsSpec extends OptionUtils with UnitSpec {

  behavior of "Option Utils"

  it should "throw exception if None" in {
    val o = Some("test")
    o.getOrThrow(new IllegalArgumentException()) should equal ("test")
    val n = None
    an [IllegalArgumentException] should be thrownBy { n.getOrThrow(new IllegalArgumentException()) }
  }

  it should "tapSome functions if Option = Some" in {
    var run = false
    val o = Some("test")
    val tapResult = o tapSome { _ => run = true }
    run should be (true)
    tapResult should equal (o)
  }

  it should "tapSome functions if Option = None" in {
    var run = false
    val none: Option[String] = None
    val tapResult = none tapSome { _ => run = true }
    run should be (false)
    tapResult should equal (none)
  }

  it should "tapNone functions if Option = Some" in {
    var run = false
    val o = Some("test")
    val tapResult = o tapNone { run = true }
    run should be (false)
    tapResult should equal (o)
  }

  it should "tapNone functions if Option = None" in {
    var run = false
    val none: Option[String] = None
    val tapResult = none tapNone { run = true }
    run should be (true)
    tapResult should equal (none)
  }

  it should "return Some[T] if tryCatchOpt block runs successfully" in {
    val opt = tryCatchOpt("Some String")
    opt should be (Some("Some String"))
  }

  it should "return None if tryCatchOpt block throws Exception" in {
    val opt = tryCatchOpt {
      val str = "Some String"
      throw new RuntimeException("test")
    }
    opt should be (None)
  }
}
