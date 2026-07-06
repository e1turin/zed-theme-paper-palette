// Kotlin sample for Alabaster theme
// Demonstrates common Kotlin language features

package io.github.e1turin.samples

import kotlin.math.PI
import kotlin.math.pow
import kotlin.system.measureTimeMillis

// Global constants
const val MAX_SIZE: Int = 1000
const val PI_CONST: Double = 3.14159
const val DEBUG: Boolean = true
const val DEFAULT_NAME: String = "Unnamed"

// Top-level property
val counter: Int = 0
var mutableCounter = 0

// Type alias
typealias ShapeFactory = (String, DoubleArray) -> Shape?

// Data class
data class Point(val x: Double, val y: Double) {
    fun distanceTo(other: Point): Double {
        val dx = x - other.x
        val dy = y - other.y
        return kotlin.math.sqrt(dx * dx + dy * dy)
    }
}

// Enum class with properties
enum class Status {
    ACTIVE,
    INACTIVE,
    PENDING,
    ERROR
}

// Enum with constructor and methods
enum class Color(val hex: String) {
    RED("#FF0000"),
    GREEN("#00FF00"),
    BLUE("#0000FF");

    fun rgb(): Triple<Int, Int, Int> {
        val r = hex.substring(1..2).toInt(16)
        val g = hex.substring(3..4).toInt(16)
        val b = hex.substring(5..6).toInt(16)
        return Triple(r, g, b)
    }
}

// Sealed class / interface
sealed class Shape {
    abstract fun area(): Double
    abstract fun perimeter(): Double
}

// Inheritance: class extending sealed class
class Circle(val radius: Double, val name: String = "Circle") : Shape() {
    init {
        require(radius > 0) { "Radius must be positive" }
    }

    override fun area(): Double = PI * radius.pow(2)

    override fun perimeter(): Double = 2 * PI * radius
}

class Rectangle(val width: Double, val height: Double, name: String = "Rectangle") : Shape() {
    override fun area(): Double = width * height

    override fun perimeter(): Double = 2 * (width + height)
}

// Object declaration (singleton)
object ShapeRegistry {
    private val shapes = mutableListOf<Shape>()

    fun register(shape: Shape) {
        shapes.add(shape)
    }

    fun all(): List<Shape> = shapes.toList()

    fun clear() {
        shapes.clear()
    }
}

// Companion object and factory pattern
class User private constructor(
    val id: Long,
    val name: String,
    val email: String?
) {
    companion object Factory {
        private var nextId = 1L

        @JvmStatic
        fun create(name: String, email: String? = null): User {
            return User(nextId++, name, email)
        }
    }

    val displayName: String
        get() = email?.let { "$name <$it>" } ?: name

    override fun toString(): String = "User(id=$id, name='$name')"
}

// Interface with default implementation
interface Drawable {
    fun draw(): String

    fun description(): String = "A drawable object"
}

// Multiple interface implementation
class Drawing(private val shapes: List<Shape>) : Drawable {
    override fun draw(): String {
        return shapes.joinToString("\n") { shape ->
            when (shape) {
                is Circle -> "Circle(radius=${shape.radius})"
                is Rectangle -> "Rectangle(w=${shape.width}, h=${shape.height})"
                else -> "Unknown shape"
            }
        }
    }
}

// Extension functions
fun Circle.scaled(factor: Double): Circle {
    return Circle(radius * factor, "$name (scaled)")
}

fun String.isEmail(): Boolean {
    return this.contains("@") && this.contains(".")
}

// Generic function
fun <T> identity(value: T): T = value

// Higher-order function with lambda
fun <T> List<T>.customFilter(predicate: (T) -> Boolean): List<T> {
    val result = mutableListOf<T>()
    for (item in this) {
        if (predicate(item)) {
            result.add(item)
        }
    }
    return result
}

// Inline function
inline fun <reified T> isInstance(value: Any): Boolean {
    return value is T
}

// Suspend function (coroutines demonstration)
suspend fun fetchData(url: String): String {
    // Simulated async work
    kotlinx.coroutines.delay(100)
    return "Data from $url"
}

// Main function
fun main() {
    // Various string types
    val singleQuoted = "Hello, World!"
    val rawString = """Raw
        |multiline
        |string literal""".trimMargin()

    // String templates
    val name = "Kotlin"
    val greeting = "Hello, $name!"
    val expression = "2 + 2 = ${2 + 2}"

    // Numeric literals
    val integer = 42
    val float = 3.14
    val hex = 0xFF
    val binary = 0b1010
    val long = 123L
    val unsigned = 42u

    // Boolean literals
    val isTrue = true
    val isFalse = false

    // Nullable types
    val nullableString: String? = "not null"
    val nullString: String? = null

    // Null safety
    val length: Int? = nullableString?.length
    val safeLength: Int = nullString?.length ?: -1

    // Creating instances
    val circle = Circle(5.0, "My Circle")
    val rect = Rectangle(10.0, 20.0)
    val point = Point(3.0, 4.0)

    // Using objects
    ShapeRegistry.register(circle)
    ShapeRegistry.register(rect)

    // For loop with range
    for (i in 1..10) {
        if (i % 2 == 0) continue
        if (i > 7) break
        println(i)
    }

    // For loop with collection
    val items = listOf("a", "b", "c")
    for ((index, value) in items.withIndex()) {
        println("$index: $value")
    }

    // While loop
    var i = 0
    while (i < 3) {
        println(i)
        i++
    }

    // When expression (replacement for switch)
    val status = Status.ACTIVE
    val message = when (status) {
        Status.ACTIVE -> "Active"
        Status.INACTIVE -> "Inactive"
        Status.PENDING -> "Pending..."
        Status.ERROR -> "Error occurred"
    }

    // Collections
    val list = listOf(1, 2, 3, 4, 5)
    val mutableList = mutableListOf(1, 2, 3)
    val set = setOf(1, 2, 3)
    val map = mapOf("key1" to "value1", "key2" to "value2")

    // Destructuring declarations
    val (x, y) = Point(10.0, 20.0)
    println("Point: x=$x, y=$y")

    // Scope functions
    val result = circle.let {
        "Area: ${it.area()}, Perimeter: ${it.perimeter()}"
    }

    val rectCopy = rect.apply {
        // apply provides 'this' context
        println("Using apply on $this")
    }

    val area = with(rect) {
        area() // same as rect.area()
    }

    // Lambda usage
    val doubled = list.map { it * 2 }
    val evens = list.filter { it % 2 == 0 }
    val sum = list.reduce { acc, n -> acc + n }

    // Generic function
    val intIdentity = identity(42)
    val stringIdentity = identity("hello")

    // Extension function usage
    val scaledCircle = circle.scaled(2.0)
    val isValidEmail = "user@example.com".isEmail()

    // Companion object usage
    val user = User.create("Alice", "alice@example.com")

    // Annotations
    @Suppress("UNUSED_EXPRESSION")
    val unused = 42
}
