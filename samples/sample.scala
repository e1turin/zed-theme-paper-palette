// Scala sample for Alabaster theme
// Demonstrates common Scala language features

package io.github.e1turin.samples

import scala.collection.immutable.ListMap
import scala.math.{Pi, pow}
import scala.util.{Try, Success, Failure}

// Global constants
val MaxSize: Int = 1000
val PiConst: Double = 3.14159
val Debug: Boolean = true
val DefaultName: String = "Unnamed"

// Global variables (mutable)
private var counter: Int = 0

// Type alias
type ShapeFactory = (String, Double*) => Shape

// Case class (immutable data type)
case class Point(x: Double, y: Double) {
  def distanceTo(other: Point): Double = {
    val dx = x - other.x
    val dy = y - other.y
    math.sqrt(dx * dx + dy * dy)
  }
}

// Sealed trait with case objects (enum pattern)
sealed trait Status
case object Active extends Status
case object Inactive extends Status
case object Pending extends Status
case class Error(code: Int, message: String) extends Status

// Scala 3 enum syntax (also valid in Scala 2 with -Xsource:3)
// enum Color(val hex: String):
//   case Red extends Color("#FF0000")
//   case Green extends Color("#00FF00")
//   case Blue extends Color("#0000FF")

// Legacy Scala 2 enum style
object Color extends Enumeration {
  type Color = Value
  val Red: Color = Value("#FF0000")
  val Green: Color = Value("#00FF00")
  val Blue: Color = Value("#0000FF")
}

// Abstract class / trait hierarchy
sealed trait Shape {
  def area(): Double
  def perimeter(): Double
}

// Class with primary constructor, default parameters, and inheritance
class Circle(val radius: Double, val name: String = "Circle") extends Shape {
  require(radius > 0, "Radius must be positive")

  override def area(): Double = Pi * pow(radius, 2)

  override def perimeter(): Double = 2 * Pi * radius
}

class Rectangle(val width: Double, val height: Double, name: String = "Rectangle")
    extends Shape {
  override def area(): Double = width * height

  override def perimeter(): Double = 2 * (width + height)
}

// Object declaration (singleton)
object ShapeRegistry {
  private val shapes = scala.collection.mutable.ListBuffer.empty[Shape]

  def register(shape: Shape): Unit = {
    shapes += shape
  }

  def all(): List[Shape] = shapes.toList

  def clear(): Unit = {
    shapes.clear()
  }
}

// Companion object with apply factory method
class User private (val id: Long, val name: String, val email: Option[String]) {
  import User._

  val displayName: String = email match {
    case Some(e) => s"$name <$e>"
    case None    => name
  }

  override def toString: String = s"User(id=$id, name='$name')"
}

object User {
  private var nextId = 1L

  def apply(name: String, email: Option[String] = None): User = {
    val id = nextId
    nextId += 1
    new User(id, name, email)
  }
}

// Trait with default implementation
trait Drawable {
  def draw(): String
  def description: String = "A drawable object"
}

// Mixin trait composition
class Drawing(private val shapes: List[Shape]) extends Drawable {
  override def draw(): String = {
    shapes
      .map {
        case c: Circle    => s"Circle(radius=${c.radius})"
        case r: Rectangle => s"Rectangle(w=${r.width}, h=${r.height})"
        case other        => s"Unknown shape: $other"
      }
      .mkString("\n")
  }
}

// Extension method (Scala 2 implicit class)
implicit class RichCircle(circle: Circle) {
  def scaled(factor: Double): Circle =
    new Circle(circle.radius * factor, s"${circle.name} (scaled)")
}

implicit class RichString(val s: String) extends AnyVal {
  def isEmail: Boolean = s.contains("@") && s.contains(".")
}

// Generic function
def identity[T](value: T): T = value

// Higher-order function
def customFilter[T](items: List[T])(predicate: T => Boolean): List[T] = {
  items.foldLeft(List.empty[T]) { (acc, item) =>
    if (predicate(item)) acc :+ item else acc
  }
}

// By-name parameter and currying
def unless(condition: Boolean)(block: => Unit): Unit = {
  if (!condition) block
}

// Method with implicit parameter
def greet(name: String)(implicit greeting: String): String = {
  s"$greeting, $name!"
}

// Pattern matching in function definition
val describeStatus: Status => String = {
  case Active           => "Active"
  case Inactive         => "Inactive"
  case Pending          => "Pending..."
  case Error(_, msg)    => s"Error: $msg"
}

// Lazy val
lazy val expensiveComputation: Double = {
  println("Computing...")
  42.0
}

// Main function
def main(args: Array[String]): Unit = {
  // String literals
  val singleQuoted = "Hello, World!"
  val multiline = """This is a
                     |multiline string
                     |for testing""".stripMargin

  // String interpolation
  val lang = "Scala"
  val greeting = s"Hello, $lang!"
  val formatted = f"Pi is approximately ${Pi}%.2f"
  val raw = raw"Hello\nWorld"  // raw string, \n is not escaped

  // Numeric literals
  val integer: Int = 42
  val float: Double = 3.14
  val hex: Int = 0xFF
  val binary: Int = 0b1010
  val long: Long = 123L
  val short: Short = 123

  // Boolean literals
  val isTrue: Boolean = true
  val isFalse: Boolean = false

  // Option type
  val someValue: Option[Int] = Some(42)
  val noneValue: Option[Int] = None
  val present: String = someValue.getOrElse("default")
  val absent: String = noneValue.getOrElse("default")

  // Either type
  val success: Either[String, Int] = Right(42)
  val failure: Either[String, Int] = Left("Something went wrong")

  // Try type
  val trySuccess: Try[Int] = Success(42)
  val tryFailure: Try[Int] = Failure(new Exception("Oops"))

  // Creating instances
  val circle = new Circle(5.0, "My Circle")
  val rect = new Rectangle(10.0, 20.0)
  val point = Point(3.0, 4.0)  // using companion apply

  // Object registry
  ShapeRegistry.register(circle)
  ShapeRegistry.register(rect)

  // For comprehension
  val coordinates = for {
    x <- List(1, 2, 3)
    y <- List(4, 5, 6)
    if x < y
  } yield (x, y)

  // For loop with guards
  for {
    i <- 1 to 10
    if i % 2 == 0
    if i > 3
  } println(s"Even number greater than 3: $i")

  // While loop
  var i = 0
  while (i < 3) {
    println(i)
    i += 1
  }

  // Pattern matching
  val status: Status = Active
  val description = status match {
    case Active           => "Active"
    case Inactive         => "Inactive"
    case Pending          => "Pending..."
    case Error(code, msg) => s"Error $code: $msg"
  }

  // Pattern matching with types
  val shape: Shape = circle
  val shapeInfo = shape match {
    case c: Circle    => s"Circle with radius ${c.radius}"
    case r: Rectangle => s"Rectangle ${r.width}x${r.height}"
    case _            => "Unknown shape"
  }

  // Collections
  val list: List[Int] = List(1, 2, 3, 4, 5)
  val vector: Vector[Int] = Vector(1, 2, 3)
  val set: Set[Int] = Set(1, 2, 3)
  val map: Map[String, Int] = Map("one" -> 1, "two" -> 2, "three" -> 3)
  val seq: Seq[Int] = Seq(1, 2, 3)
  val indexedSeq: IndexedSeq[Int] = IndexedSeq(1, 2, 3)

  // Tuple and destructuring
  val tuple: (String, Int, Boolean) = ("Alice", 30, true)
  val (name, age, isAdmin) = tuple

  val coordinates2 = (10.0, 20.0)
  val (x, y) = coordinates2

  // Higher-order functions on collections
  val doubled: List[Int] = list.map(_ * 2)
  val evens: List[Int] = list.filter(_ % 2 == 0)
  val sum: Int = list.reduce(_ + _)
  val grouped: Map[Boolean, List[Int]] = list.partition(_ > 2)
  val flattened: List[Int] = List(List(1, 2), List(3, 4)).flatten
  val zipped: List[(Int, String)] = List(1, 2, 3).zip(List("a", "b", "c"))

  // Fold operations
  val foldSum: Int = list.foldLeft(0)(_ + _)
  val product: Int = list.foldRight(1)(_ * _)

  // Option operations
  val optLength: Option[Int] = someValue.map(_ + 1)
  val flatMapped: Option[Int] = someValue.flatMap(v => if (v > 0) Some(v) else None)
  val filtered: Option[Int] = someValue.filter(_ > 10)

  // For-comprehension with Options
  val result = for {
    a <- someValue
    b <- Some(10)
  } yield a + b

  // Implicit parameter usage
  implicit val defaultGreeting: String = "Hello"
  val greeted: String = greet("Alice")  // uses implicit

  // Lazy val usage
  println(expensiveComputation)
  println(expensiveComputation)  // computed only once

  // By-name parameter
  unless(false) {
    println("This won't print")
  }

  unless(true) {
    println("This will print")
  }

  // Partial function
  val divide: PartialFunction[Int, Int] = {
    case d: Int if d != 0 => 42 / d
  }
  val isDefined: Boolean = divide.isDefinedAt(2)

  // Extension method usage
  val scaledCircle = circle.scaled(2.0)
  val isValidEmail = "user@example.com".isEmail

  // Companion object usage
  val user = User("Alice", Some("alice@example.com"))

  // Annotations
  @deprecated("Use alternative method", "2.0.0")
  def oldMethod(): Unit = {
    println("This is deprecated")
  }
}
