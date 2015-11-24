package chk.commons.model

import chk.commons.util._

/**
 * Name Value Pair
 * @param name Pair Key
 * @param value Pair Value
 */
case class NameValuePair(name: String, value: String) {
  /**
   * URLEncode value with "UTF-8"
   * @return URL encoded string value with "UTF-8"
   */
  def encodedValue = encode(value)

  /**
   * URLEncode name with "UTF-8"
   * @return URL encoded string name with "UTF-8"
   */
  def encodedName = encode(name)

  /**
   * convert to URL query string format
   * @return String with URL query string format
   */
  def toQuery = s"$encodedName=$encodedValue"

  /**
   * convert to Key -> Value Tuple
   * @return Tuple(String, String)
   */
  def toKeyValue = (name -> value)
}