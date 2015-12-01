package chk.commons.test.test

import chk.commons.test.UnitSpec
import chk.commons.test.util.RandomGenerators._

class GeneratorSpec extends UnitSpec {

  behavior of "RandomGenerators"

  it should "generate random values" in {
    forAll(1 until (positiveIntegers.random % 100)) { n =>
      val i = integers.random
      i should be >= scala.Int.MinValue
      i should be <= scala.Int.MaxValue

      val l = longs.random
      l should be >= scala.Long.MinValue
      l should be <= scala.Long.MaxValue

      val d = doubles.random
      d should be >= scala.Double.MinValue
      d should be <= scala.Double.MaxValue

      val s = strings(n).random
      s.length should be <= n

      val ss = shortStrings.random
      ss.length should be >= 0

      val list = lists[Int](integers).random
      forAll(list)(_ should be <= scala.Int.MaxValue)
    }
  }

}
