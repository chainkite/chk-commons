package chk.commons.test

import org.scalatest._
import org.scalatest.concurrent.{Futures, AsyncAssertions, ScalaFutures}
import org.scalatest.time._

trait CommonSpec extends Matchers
  with OptionValues
  with Inside
  with Inspectors
  with ScalaFutures
  with Futures
  with AsyncAssertions { self: Suite =>

  implicit val defaultPatience = PatienceConfig(timeout = Span(1, Seconds), interval = Span(15, Millis))

}

trait UnitSpec extends CommonSpec with FlatSpecLike

trait FeatureSpec extends CommonSpec with FeatureSpecLike
