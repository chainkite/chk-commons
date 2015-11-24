package chk.commons.util

import java.net.{URLEncoder, URLDecoder, URI}

import chk.commons.model.NameValuePair


trait URIUtils {

  implicit class URIOps(uri: URI) {
    /**
     * convert URI parameters to NameValuePair sequence (decoded)
     * @return NameValuePair sequence (can be empty)
     */
    def toNameValuePairs: Seq[NameValuePair] = _queryStringToNameValuePairs(uri.getRawQuery)((s: String) => decode(s))

    /**
     * convert URI parameters to Map, one key can has multiple values
     * @return Map[ String, Seq[String] ] (can be empty)
     */
    def paramMap: Map[String, Seq[String]] = toNameValuePairs.map(_.toKeyValue).groupBy(_._1).map(m => (m._1, m._2.map(_._2)))
  }

  /**
   * new URI with encoded uri query string
   * @param unencodedStr uri string not encoded
   * @return
   */
  def newEncodedURI(unencodedStr: String): URI = {
    unencodedStr.indexOf("?") match {
      case -1 => new URI(unencodedStr) // no query params, cannot be encoded
      case i =>
        val path = unencodedStr.substring(0, i)
        val queryString = unencodedStr.substring(i + 1)
        val pairs = _queryStringToNameValuePairs(queryString)((s: String) => s)
        val encodedQueryString = pairs.map(_.toQuery).mkString("&")
        new URI(s"$path?$encodedQueryString")
    }
  }

  /**
   * URLEncoder encode, then replace "+" with "%20"
   * @return NameValuePair sequence (can be empty)
   */
  def encode(str: String, charset: String = "UTF-8"): String = URLEncoder.encode(str, charset).replaceAll("\\+", "%20")

  /**
   * replace "+" with "%20", then URLDecoder decode
   * @return Decoded String
   */
  def decode(str: String, charset: String = "UTF-8"): String = URLDecoder.decode(str.replaceAll("\\+", "%20"), charset)

  /**
   * convert URI query string to NameValuePair sequence
   * @param str
   * @param convertFunc convert func for name / value, like encode or decode, default not convert
   * @return NameValuePair sequence (can be empty)
   */
  private def _queryStringToNameValuePairs(str: String)(convertFunc: String => String = (s: String) => s): Seq[NameValuePair] = {
    if (str == null || str.isEmpty) Seq.empty[NameValuePair]
    else str.split("&").map(_.trim.split("=", 2)).filter(_.size >= 2).map { pairArr =>
      NameValuePair(convertFunc(pairArr(0)), convertFunc(pairArr(1)))
    }
  }

  implicit class StringURIOps(underlying: String) {
    def urlEncode(charset: String = "UTF-8") = encode(underlying, charset)
    def urlDecode(charset: String = "UTF-8") = decode(underlying, charset)
  }

}