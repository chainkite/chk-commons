package chk.commons.test

import chk.commons.util.StringUtils


class StringUtilsSpec extends StringUtils with UnitSpec {

  behavior of "String Utils"

  it should "return None if empty" in {
    val str = ""
    str.emptyOpt should be (None)
  }

  it should "return Some(string) if not empty" in {
    val str = "str"
    str.emptyOpt should equal (Some(str))
  }

  it should "return None if blank" in {
    val str = "  "
    str.blankOpt should be (None)
  }

  it should "return Some(string) if not blank" in {
    val str = " some "
    str.blankOpt should equal (Some(str))
  }

  it should "camelize a string" in {
    val str = "test string"
    str.camelize(removeDelimiter = true) should equal ("TestString")
    str.camelize(removeDelimiter = false) should equal ("Test String")
  }

  it should "camelize a string with custom delimiter" in {
    val str = "test-string"
    str.camelize(delimiter = "-", removeDelimiter = true) should equal ("TestString")
    str.camelize(delimiter = "-", removeDelimiter = false) should equal ("Test-String")
  }
}