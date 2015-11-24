package chk.commons.test.util

import java.net.{URI, URLDecoder}

import chk.commons.test.util.test.UnitSpec
import chk.commons.util.{MapUtils, URIUtils}


class URIUtilsSpec extends URIUtils with MapUtils with UnitSpec {
  behavior of "URI Utils"
  val uri = new URI("http://example.com/query?q=random%20word%24&r=12%233&s=wern%3D+*&%22t%22=&u")

  it should "test newEncodedURI" in {
    val uriStr = "example.com/query?q=random word$"
    val encodedUriStr = "http://example.com/query?q=random%20word%24"
    val uri = new URI(encodedUriStr)
    val newUri = newEncodedURI(uriStr)
    uri.getRawQuery should be ("q=random%20word%24")
    uri.getQuery should be ("q=random word$")
    newUri.getRawQuery should be (uri.getRawQuery)
    newUri.getQuery should be (uri.getQuery)
  }

  it should "return 4" in {
    val uri = newEncodedURI("http://example.com/query?q=&r=&s=&t=&u")
    val pairs = uri.toNameValuePairs
    pairs.size should be (4)
  }

  it should "return random word#" in {
    val uri = new URI("http://example.com/query?q=random%20word%23")
    val pairs = uri.toNameValuePairs
    val q = pairs.map(_.toKeyValue).toMap[String, String].get("q")
    val right = URLDecoder.decode("random%20word%23", "UTF-8")
    q should be (Some(right))
  }

  it should "return Some(12#3)" in {
    uri.paramMap.get("r").map(_(0)) should be (Some("12#3"))
  }

  it should "return q=random%20word%24&r=12%233&s=wern%3D%20*&%22t%22=" in {
    val uri = new URI("http://example.com/query?q=random%20word%24&r=12%233&s=wern%3D+*&%22t%22=&u")
    val pairs = uri.toNameValuePairs
    pairs.map(_.toQuery).mkString("&") should be ("q=random%20word%24&r=12%233&s=wern%3D%20*&%22t%22=")
  }

  it should "return pairs' size" in {
    val uri = newEncodedURI("http://example.com/query?q=&r=&s=&t=&t===&u=&u=&u=")
    val pairs = uri.toNameValuePairs
    val paramMap = uri.paramMap
    paramMap.foldLeft(0)((a, p) => a + p._2.size) should be (pairs.size)
  }

  it should "return 0" in {
    val uri = new URI("http://example.com/query?q=random%20word%24&r=12%233&s=wern%3D+*&%22t%22=&u")
    val pairs = uri.toNameValuePairs
    val frmPairs = pairs.map(_.toQuery)
    val frmMap = uri.paramMap.map {
      case (k, vs) => (encode(k), vs.map(encode(_)))
    }.makeString(innerSep = "=", outerSep = "&").split("&")
    (frmPairs diff frmMap).size should be (0)
  }

  it should "return empty list or map" in {
    val uri = new URI("http://example.com/query")
    val pairs = uri.toNameValuePairs
    val paramMap = uri.paramMap
    pairs.size should be (0)
    paramMap.size should be (0)
  }

}
