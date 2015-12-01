package chk.commons.test.util


trait RandomGenerator[+T] {
  self =>

  def random: T

  def foreach[U](f: T => U) {
    f(random)
  }

  def map[S](f: T => S): RandomGenerator[S] = new RandomGenerator[S] {
    def random = f(self.random)
  }

  def flatMap[S](f: T => RandomGenerator[S]): RandomGenerator[S] = new RandomGenerator[S] {
    def random = f(self.random).random
  }
}

object RandomGenerators {

  def choose(from: Int, to: Int) = new RandomGenerator[Int] {
    def random = if (from == to) from else scala.util.Random.nextInt(to - from) + from
  }

  def single[T](x: T) = new RandomGenerator[T] {
    def random = x
  }

  def pairs[T, U](t: RandomGenerator[T], u: RandomGenerator[U]): RandomGenerator[(T, U)] = for {
    x <- t
    y <- u
  } yield (x, y)

  def strings(maxLength: Int) = choose(0, maxLength).map(int => scala.util.Random.nextString(int))

  val integers = new RandomGenerator[Int] {
    def random = scala.util.Random.nextInt()
  }
  val positiveIntegers = integers.map(_ & Int.MaxValue)
  val negativeIntegers = integers.map(_ | Int.MinValue)

  val longs = new RandomGenerator[Long] {
    def random = scala.util.Random.nextLong()
  }
  val positiveLongs = longs.map(_ & Long.MaxValue)
  val negativeLongs = longs.map(_ | Int.MinValue)

  val doubles = new RandomGenerator[Double] {
    def random = scala.util.Random.nextDouble()
  }

  val booleans = integers.map(_ >= 0)

  val shortStrings = strings(20)

  def emptyLists = single(Nil)

  def nonEmptyLists[T](generator: RandomGenerator[T]) = for {
    head <- generator
    tail <- lists(generator)
  } yield head :: tail

  def lists[T](generator: RandomGenerator[T]): RandomGenerator[List[T]] = for {
    cutoff <- booleans
    list <- if (cutoff) emptyLists else nonEmptyLists[T](generator)
  } yield list

}