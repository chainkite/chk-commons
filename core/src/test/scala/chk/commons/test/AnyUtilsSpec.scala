package chk.commons.test

import chk.commons.util.AnyUtils


class AnyUtilsSpec extends AnyUtils with UnitSpec {

  behavior of "Any Utils"

  it should "convert any to Option" in {
    val i = 10
    val o = i.option
    o should be (Some(i))

    val s: String = null
    val so = s.option
    so should be (None)
  }

  it should "check if null" in {
    val s: String = null
    s.isNull shouldBe true
    val n = "10"
    n.isNull shouldBe false
  }

  it should "tap some functions on AnyRef" in {
    var run = false
    val s: String = "string"
    val tapResult = s tap { str => run = true }
    run shouldBe true
    tapResult should equal (s)
  }

  it should "tap some functions on Any" in {
    var run = false
    val i: Int = 10
    val tapResult = i tap { in => run = true }
    run shouldBe true
    tapResult should equal (i)
  }

  it should "don't tap functions if null" in {
    var run = false
    val i: String = null
    i tapNotNull { in => run = true }
    run shouldBe false
  }

  it should "tap functions if not null" in {
    var run = false
    val i: String = "10"
    val tapResult = i tapNotNull { in => run = true }
    run shouldBe true
    tapResult should equal (i)
  }

  it should "pipeline on AnyRef" in {
    val s: String = "string"
    val pipeResult = s pipe { _.reverse } pipe { _.drop(1) }
    pipeResult should equal ("nirts")
  }

  it should "pipeline on Any" in {
    val i = 10
    val pipeResult = i pipe { _ + 1 } pipe { _ * 2 }
    pipeResult should equal (22)
  }

  it should "convert any to Option implicitly" in {
    val i = 10
    val o: Option[Int] = i
    o should be (Some(i))
  }

}
