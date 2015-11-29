package chk.commons.test.util

import java.nio.file.{Paths, Files}

import scala.util.Random


object StringGenerator {

  private val words = Files.lines(Paths.get(System.getProperty("user.dir"), "test", "src", "main", "resources", "dictionary.txt")).toArray

  private val random = new Random()

  def getRandomString(maxLength: Int) = random.nextString(maxLength)

  def getRandomWord = words(random.nextInt(words.length))

  def getRandomText(maxLength: Int): String = (1 to (random.nextInt(maxLength) + 2)).map(i => getRandomWord).mkString(" ")
}
