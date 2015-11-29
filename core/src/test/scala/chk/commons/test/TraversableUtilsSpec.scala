package chk.commons.test

import chk.commons.util.TraversableUtils


class TraversableUtilsSpec extends TraversableUtils with UnitSpec {

  behavior of "Traversable Utils"

  it should "convert list to option" in {
    val list = List("1", "2")
    list.emptyOpt should be ('defined)
    list.emptyOpt.value should contain theSameElementsAs list

    val emptyList = List.empty[String]
    emptyList.emptyOpt should be (None)
  }
}
