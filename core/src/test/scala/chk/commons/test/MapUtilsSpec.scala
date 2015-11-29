package chk.commons.test

import chk.commons.util.MapUtils


class MapUtilsSpec extends MapUtils with UnitSpec {

  val map = Map[String, String](
    "key1" -> "1",
    "key2" -> "2",
    "key3" -> "1"
  )

  behavior of "Map Utils"

  it should "find key by value" in {
    map.findKey("1") should be (Some("key1"))
    map.findKey("5") should be (None)
  }

  it should "find keys by value" in {
    map.findKeys("1") should contain theSameElementsAs List("key1", "key3")
    map.findKeys("5") should be ('empty)
  }

  it should "return map string with format key = value AND ..." in {
    val paramMap = Map[String, Seq[String]](
      "key1" -> Seq("1", "2"),
      "key2" -> Seq("2"),
      "key3" -> Seq("1")
    )
    paramMap.makeString("=", " AND ").trim should be ("key1=1 AND key1=2 AND key2=2 AND key3=1")
  }

}
