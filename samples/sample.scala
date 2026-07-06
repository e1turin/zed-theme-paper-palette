// ====================================================================
// Scala Syntax Highlighting Sample — NOT compilable, covers many
// syntax constructs for theme testing purposes.
// ====================================================================

// === PACKAGE & IMPORTS ===

package io.github.e1turin.samples

import scala.collection.immutable.{ListMap, VectorMap}
import scala.math.{Pi => π, pow, sqrt}
import scala.util._
import scala.concurrent.{Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global

// === VAL / VAR / LAZY VAL / TYPE ===

val MaxSize: Int = 1000
val PiConst: Double = 3.14159
val Debug: Boolean = true
val DefaultName: String = "Unnamed"
private var counter: Int = 0
var mutableFlag: Boolean = false

lazy val expensiveResource: String = {
  println("initialising lazy val…")
  "loaded"
}

type ShapeFactory = (String, Double*) => Shape
type Points = Seq[(Double, Double)]

// === DEF — functions, default params, currying, by-name ===

def add(a: Int, b: Int): Int = a + b

def greet(name: String, greeting: String = "Hello"): String =
  s"$greeting, $name!"

def namedArgsExample(): Unit =
  println(greet(greeting = "Hi", name = "Bob"))

def curried(a: Int)(b: Int)(c: Int): Int = a + b + c

def byName(cond: Boolean)(block: => Unit): Unit =
  if (cond) block

def varargs(values: Int*): Int = values.sum

// === IMPLICIT ===

implicit val defaultGreeting: String = "Hello"

def greetImplicit(name: String)(implicit greeting: String): String =
  s"$greeting, $name!"

implicit class RichInt(val x: Int) extends AnyVal {
  def squared: Int = x * x
  def isEven: Boolean = x % 2 == 0
}

implicit def intToOption(x: Int): Option[Int] = Some(x)

// === TRAIT ===

trait Drawable {
  def draw(): String
  def description: String = "A drawable object"
}

trait Movable {
  def move(dx: Double, dy: Double): Unit
}

sealed trait Status
sealed abstract class Event

// === CLASS — constructor params, extends, with ===

class Circle(val radius: Double, val name: String = "Circle")
    extends Shape
    with Drawable {
  require(radius > 0, "Radius must be positive")

  override def area(): Double = π * radius * radius
  override def perimeter(): Double = 2 * π * radius
  override def draw(): String = s"Circle($radius)"
}

class Rectangle(
    val width: Double,
    val height: Double,
    private var _name: String = "Rectangle"
) extends Shape
    with Drawable {
  override def area(): Double = width * height
  override def perimeter(): Double = 2 * (width + height)
  override def draw(): String = s"Rectangle($width × $height)"
}

// === CASE CLASS / CASE OBJECT ===

case class Point(x: Double, y: Double) {
  def distanceTo(other: Point): Double =
    sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y))
}

case object Origin extends Point(0, 0)

case class Error(code: Int, message: String) extends Status

// === OBJECT / COMPANION OBJECT / APPLY / UNAPPLY ===

object ShapeRegistry {
  private val shapes = scala.collection.mutable.ListBuffer.empty[Shape]

  def register(shape: Shape): Unit = shapes += shape
  def all(): List[Shape] = shapes.toList
  def clear(): Unit = shapes.clear()
}

object User {
  private var nextId = 1L

  def apply(name: String, email: Option[String] = None): User = {
    val id = nextId; nextId += 1; new User(id, name, email)
  }

  def unapply(u: User): Option[(Long, String, Option[String])] =
    Some((u.id, u.name, u.email))
}

class User private (val id: Long, val name: String, val email: Option[String]) {
  override def toString: String = s"User(id=$id, name='$name')"
}

// === ENUM — Scala 2 Enumeration + Scala 3 enum ===

object Color extends Enumeration {
  type Color = Value
  val Red: Color   = Value("#FF0000")
  val Green: Color = Value("#00FF00")
  val Blue: Color  = Value("#0000FF")
}

// Scala 3 enum (commented for Scala 2 compat; syntax reference)
// enum TrafficLight:
//   case Red, Yellow, Green
//   def next: TrafficLight = this match
//     case Red    => Green
//     case Yellow => Red
//     case Green  => Yellow

// === PATTERN MATCHING ===

val describe: Status => String = {
  case Active           => "Active"
  case Inactive         => "Inactive"
  case Pending          => "Pending…"
  case Error(code, msg) => s"Error $code: $msg"
}

def matchDemo(s: Shape): String = s match {
  case c: Circle    => s"Circle(r=${c.radius})"
  case r: Rectangle => s"Rectangle(${r.width}×${r.height})"
  case _            => "Unknown shape"
}

def guardDemo(x: Int): String = x match {
  case n if n < 0  => "negative"
  case n if n == 0 => "zero"
  case n           => s"positive ($n)"
}

// === PARTIAL FUNCTION ===

val divide: PartialFunction[Int, Int] = {
  case d: Int if d != 0 => 42 / d
}

val safeSqrt: PartialFunction[Double, Double] = {
  case x if x >= 0 => sqrt(x)
}

// === FOR COMPREHENSION / FOR LOOP ===

val coordinates: List[(Int, Int)] = for {
  x <- List(1, 2, 3)
  y <- List(4, 5, 6)
  if x < y
} yield (x, y)

val optResult: Option[Int] = for {
  a <- Some(10)
  b <- Some(20)
} yield a + b

for {
  i <- 1 to 10
  if i % 2 == 0
  if i > 3
} println(s"Even > 3: $i")

// === IF / ELSE / WHILE / DO WHILE ===

def signum(x: Int): String =
  if (x > 0) "positive"
  else if (x < 0) "negative"
  else "zero"

var i = 0
while (i < 3) { println(i); i += 1 }

var j = 0
do { println(j); j += 1 } while (j < 3)

// === COLLECTIONS ===

val list: List[Int]          = List(1, 2, 3, 4, 5)
val vector: Vector[Int]      = Vector(1, 2, 3)
val set: Set[Int]           = Set(1, 2, 3, 1)
val map: Map[String, Int]   = Map("one" -> 1, "two" -> 2, "three" -> 3)
val seq: Seq[Int]           = Seq(1, 2, 3)
val indexed: IndexedSeq[Int] = IndexedSeq(1, 2, 3)
val range: Range             = 1 to 10 by 2

// === HIGHER-ORDER FUNCTIONS ===

val doubled: List[Int]        = list.map(_ * 2)
val evens: List[Int]          = list.filter(_ % 2 == 0)
val flatMapped: List[Int]    = list.flatMap(x => List(x, x * 10))
val sum: Int                  = list.reduce(_ + _)
val foldSum: Int             = list.foldLeft(0)(_ + _)
val product: Int             = list.foldRight(1)(_ * _)
val collected: List[Int]     = list.collect { case n if n > 2 => n * n }
val (big, small)             = list.partition(_ > 3)
val zipped: List[(Int, Int)] = list.zip(List(5, 4, 3, 2, 1))
val flattened: List[Int]     = List(List(1, 2), List(3, 4)).flatten
val grouped: Map[Boolean, List[Int]] = list.groupBy(_ % 2 == 0)

// === OPTION / EITHER / TRY ===

val someVal: Option[Int] = Some(42)
val noneVal: Option[Int] = None

val eitherRight: Either[String, Int] = Right(42)
val eitherLeft: Either[String, Int]  = Left("error")

val trySuccess: Try[Int] = Success(42)
val tryFailure: Try[Int] = Failure(new Exception("boom"))

// === FUTURE / PROMISE ===

val future: Future[Int] = Future { 42 }
val promise: Promise[Int] = Promise[Int]()
val promisedFuture: Future[Int] = promise.future

// === STRING INTERPOLATION & LITERALS ===

val lang      = "Scala"
val greeting  = s"Hello, $lang! — 2 + 2 = ${2 + 2}"
val formatted = f"Pi is ~${π}%.2f and e is ~${2.71828}%.3f"
val rawStr    = raw"Hello\nWorld"     // \n not escaped
val multi     =
  """This is a
    |multiline string
    |with .stripMargin""".stripMargin

// === LITERALS — numeric, char, symbol, null ===

val intLit: Int       = 42
val longLit: Long     = 123L
val floatLit: Float   = 3.14f
val doubleLit: Double = 3.14159
val hexLit: Int       = 0xFF
val binLit: Int       = 0b1010
val charLit: Char     = 'A'
val boolLit: Boolean  = true
val symLit: Symbol    = 'mySymbol
val nullLit: String   = null

// === TUPLES & DESTRUCTURING ===

val t: (String, Int, Boolean) = ("Alice", 30, true)
val (name, age, isAdmin) = t
val pair: (Int, String) = 1 -> "one"

// === GENERICS — variance, bounds, context bounds ===

class Box[+T](val value: T)                     // covariance
class Container[-T](val dummy: Unit)             // contravariance

def identical[A](a: A): A = a

def upperBound[T <: Comparable[T]](a: T, b: T): Int = a.compareTo(b)
def lowerBound[T >: Null](t: T): String = if (t == null) "null" else "not null"

def sortWithOrdering[T: Ordering](items: List[T]): List[T] =
  items.sorted

// === ABSTRACT / OVERRIDE / FINAL / SEALED ===

abstract class Base {
  def mustImplement: Int
  final def fixed: String = "immutable"
}

class Derived extends Base {
  override def mustImplement: Int = 42
}

sealed trait Direction
case object North extends Direction
case object South extends Direction
case object East  extends Direction
case object West  extends Direction

// === ANNOTATIONS ===

@deprecated("Use newMethod instead", "3.0")
def oldMethod(): Unit = println("legacy")

@scala.annotation.tailrec
def gcd(a: Int, b: Int): Int =
  if (b == 0) a else gcd(b, a % b)

@inline
def fastDouble(x: Int): Int = x * 2

// === REQUIRE / ASSERT ===

def positiveRadius(r: Double): Double = {
  require(r > 0, s"radius $r must be positive")
  r
}

def testAssert(x: Int): Unit = {
  assert(x >= 0, "x must be non-negative")
}

// === THROW / TRY / CATCH / FINALLY ===

def riskyDivide(a: Int, b: Int): Int =
  if (b == 0) throw new IllegalArgumentException("division by zero")
  else a / b

def safeDivide(a: Int, b: Int): Try[Int] = Try {
  if (b == 0) throw new ArithmeticException("/ by zero")
  a / b
} recover {
  case _: ArithmeticException => 0
}

def withFinally(): Unit = {
  val f = scala.io.Source.fromFile("/etc/hostname")
  try {
    println(f.getLines().mkString)
  } catch {
  case e: java.io.IOException => println(s"I/O error: $e")
  } finally {
    f.close()
  }
}

// === NEW ===

val freshCircle = new Circle(2.5, "tiny")

// === SELF TYPE ===

trait Persistence { def save(id: String, data: String): Unit }

trait Logger { self: Persistence =>
  def logAndSave(id: String, data: String): Unit = {
    println(s"Logging $id")
    save(id, data)
  }
}

// === SCALA 3: GIVEN / USING (illustrative) ===

// Scala 3 syntax (commented for Scala 2 compat)
// given Ordering[Int] with
//   def compare(a: Int, b: Int): Int = a - b
//
// def max[A](a: A, b: A)(using ord: Ordering[A]): A =
//   if (ord.compare(a, b) >= 0) a else b

// === SCALA 3: EXTENSION METHODS ===

// extension (x: Int)
//   def cubed: Int = x * x * x

// === SCALA 3: EXPORT ===

// class Exporter(c: Circle):
//   export c.{area, perimeter => perim}
//   export c.description

// === SAMPLE VALUES (to avoid dead-code warnings in some tools) ===

val sampleValues: List[Any] = List(
  freshCircle, sum, foldSum, product, doubled, evens,
  future, someVal, eitherRight, trySuccess, coordinates,
  range, flattened, grouped, big, small, zipped,
  collected, 42.squared, 4.isEven, defaultGreeting
)
