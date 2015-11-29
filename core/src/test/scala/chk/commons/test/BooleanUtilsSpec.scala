package chk.commons.test

import chk.commons.util.BooleanUtils


class BooleanUtilsSpec extends BooleanUtils with UnitSpec {

  behavior of "Boolean Utils"

  it should "transform to option Some if true" in {
    val opt = (1 == 1).toOption("true")
    opt should be (Some("true"))
  }

  it should "transform to option None if false" in {
    val opt = (1 == 2).toOption("true")
    opt should be (None)
  }

}