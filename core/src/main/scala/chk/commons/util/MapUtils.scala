package chk.commons.util

import collection.immutable.Map


trait MapUtils {

  implicit class MapOps[K,V](map: Map[K,V]) {
    /**
     * find key by given value, if multiple keys contains same value, it return the first only
     * @param value
     * @return Key
     */
    def findKey(value: V): Option[K] = map find { case(k, v) => v == value } map { _._1 }

    /**
     * find keys by given value
     * @param value
     * @return Iterable Key
     */
    def findKeys(value: V): Iterable[K] = map filter { case(k, v) => v == value } keys

  }

  /**
   * special case of MapOps[K, V]: Map[ String, Seq[String] ]
   * @param map
   */
  implicit class ParamMapOps(map: Map[String,Seq[String]]) extends MapOps[String,Seq[String]](map) {

    /**
     * make string of the map
     * @param innerSep separator between key and value
     * @param outerSep separator between key-value pairs
     * @param start extra start of the whole string
     * @param end extra end of the whole string
     * @return String
     */
    def makeString(innerSep: String, outerSep: String, start: String, end: String): String = map.flatMap {
      case (k, vs) => vs map (v => s"$k$innerSep$v")
    }.mkString(start, outerSep, end)

    /**
     * make string of the map, no extra start and end string
     * @param innerSep separator between key and value
     * @param outerSep separator between key-value pairs
     * @return String
     */
    def makeString(innerSep: String, outerSep: String): String = makeString(innerSep, outerSep, "", "")

  }
}
