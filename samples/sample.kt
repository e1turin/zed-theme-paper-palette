// Kotlin syntax highlighting sample — compact, not compilable.
// Covers as many language constructs as possible in one file.

package io.github.e1turin.samples

import kotlin.math.*
import kotlin.properties.Delegates
import kotlinx.coroutines.*

// === TOP-LEVEL PROPERTIES ===

const val COMPILE_TIME_CONST: Int = 42
val readOnly: String = "top-level val"
var mutable: Double = 3.14

// === TYPEALIAS ===

typealias Predicate<T> = (T) -> Boolean
typealias IntPair = Pair<Int, Int>

// === FUNCTIONS ===

// Standard function with default parameter
fun greet(name: String, greeting: String = "Hello"): String = "$greeting, $name!"

// Named arguments call site (shown later in usage block)
fun createPoint(x: Int = 0, y: Int = 0) = IntPair(x, y)

// Single-expression function
fun square(n: Int): Int = n * n

// Extension function
fun String.exclaim(): String = "$this!"

// Infix function
infix fun Int.plusTimes(times: Int): Int = (this + times) * times

// Operator overloading
data class Vector(val x: Int, val y: Int) {
    operator fun plus(other: Vector) = Vector(x + other.x, y + other.y)
    operator fun inc() = Vector(x + 1, y + 1)
    operator fun get(index: Int): Int = when (index) { 0 -> x; 1 -> y; else -> throw IndexOutOfBoundsException() }
}

// Higher-order function
fun <T> List<T>.customFilter(predicate: (T) -> Boolean): List<T> {
    val result = mutableListOf<T>()
    for (item in this) if (predicate(item)) result.add(item)
    return result
}

// Inline function
inline fun measure(block: () -> Unit): Long {
    val start = System.nanoTime()
    block()
    return System.nanoTime() - start
}

// Inline reified function
inline fun <reified T> isInstance(value: Any): Boolean = value is T

// Suspend function
suspend fun fetchData(url: String): String {
    delay(100)
    return "result from $url"
}

// === CLASSES ===

// Simple class with primary constructor
class Person(val name: String, var age: Int)

// Class with init block, secondary constructor, and property with backing field
class Counter {
    private var _count: Int = 0

    val count: Int
        get() = _count

    var label: String = "counter"
        set(value) {
            if (value.isNotBlank()) field = value
        }

    constructor() : this(0, "default")

    constructor(initial: Int, label: String) {
        _count = initial
        this.label = label
    }

    init {
        _count = 0.coerceAtLeast(0)
    }

    fun increment() { _count++ }
}

// Data class
data class Point(val x: Double, val y: Double)

// Sealed interface
sealed interface Expr

// Sealed class implementing sealed interface
sealed class BinOp : Expr {
    data class Plus(val left: Expr, val right: Expr) : BinOp()
    data class Times(val left: Expr, val right: Expr) : BinOp()
}

object Num : Expr {
    data class Const(val value: Int) : Expr
}

// Enum with properties and method
enum class Suit(val displayName: String, val symbol: String) {
    HEARTS("Hearts", "\u2665"),
    DIAMONDS("Diamonds", "\u2666"),
    CLUBS("Clubs", "\u2663"),
    SPADES("Spades", "\u2660");

    fun isRed(): Boolean = this == HEARTS || this == DIAMONDS
}

// Object declaration (singleton)
object Logger {
    private var level: Int = 0

    fun info(msg: String) { println("[INFO] $msg") }
    fun setLevel(l: Int) { level = l }
}

// Companion object with @JvmStatic
class Database {
    companion object Factory {
        private var instance: Database? = null

        @JvmStatic
        fun getInstance(): Database = instance ?: Database().also { instance = it }
    }
}

// Abstract class
abstract class Animal {
    abstract fun speak(): String
    open fun move(): String = "moves"
}

// Inheritance
class Dog(val name: String) : Animal() {
    override fun speak(): String = "$name says Woof"
    override fun move(): String = "runs"
}

// Interface with default method
interface Identifiable {
    val id: String
    fun describe(): String = "id=$id"
}

// Multiple interface implementation
class Robot(override val id: String, val model: String) : Identifiable {
    override fun describe(): String = "Robot(model=$model, id=$id)"
}

// === DELEGATED PROPERTIES ===

val lazyValue: String by lazy { "computed lazily" }

var observed: Int by Delegates.observable(0) { prop, old, new ->
    println("${prop.name} changed from $old to $new")
}

// === LATEINIT ===

class AppConfig {
    lateinit var configFile: String

    fun load() {
        configFile = "/etc/app.conf"
    }
}

// === NULL SAFETY ===

fun nullSafetyDemo() {
    val a: String? = "hello"
    val b: String? = null

    val len1: Int? = a?.length         // safe call
    val len2: Int = b?.length ?: -1    // elvis
    val len3: Int = a!!.length         // not-null assertion

    a?.let { println("not null: $it") }

    val checked: String = b as? String ?: "fallback"  // safe cast
}

// === SMART CASTS ===

fun smartCastDemo(x: Any) {
    if (x is String) {
        println(x.length)  // smart-cast to String
    }

    when (x) {
        is Int -> println("int: ${x.toDouble()}")
        is String -> println("string len: ${x.length}")
        !is Number -> println("not a number")
    }
}

// === CONTROL FLOW ===

fun controlFlowDemo() {
    // if / else (expression)
    val max = if (1 > 2) 1 else 2

    // when (exhaustive)
    val suit = Suit.HEARTS
    val color: String = when (suit) {
        Suit.HEARTS, Suit.DIAMONDS -> "red"
        Suit.CLUBS, Suit.SPADES -> "black"
    }

    // when without argument
    val x = 5
    val desc = when {
        x < 0 -> "negative"
        x == 0 -> "zero"
        else -> "positive"
    }

    // for with range
    for (i in 1..10) { /* … */ }

    // for with until / downTo / step
    for (i in 1 until 10 step 2) { /* … */ }
    for (i in 10 downTo 1) { /* … */ }

    // for with destructuring
    val items = listOf("a", "b", "c")
    for ((index, value) in items.withIndex()) { /* … */ }

    // while / do while
    var i = 0
    while (i < 3) i++

    do {
        i--
    } while (i > 0)
}

// === COLLECTIONS & LAMBDAS ===

fun collectionsDemo() {
    val list = listOf(1, 2, 3)
    val mutList = mutableListOf(1, 2, 3)
    val set = setOf("a", "b", "c")
    val map = mapOf("x" to 1, "y" to 2)
    val seq = sequenceOf(1, 2, 3)

    // Lambda with explicit parameter
    list.forEach { value -> println(value) }

    // Trailing lambda
    list.map { it * 2 }

    // Lambda as variable
    val isEven: (Int) -> Boolean = { it % 2 == 0 }
}

// === SCOPE FUNCTIONS ===

fun scopeFunctionsDemo() {
    val point = Point(1.0, 2.0)

    val dist = point.let { kotlin.math.sqrt(it.x * it.x + it.y * it.y) }

    val modified = point.apply {
        // this == point
        println("($x, $y)")
    }

    val area = with(point) { x * y }

    val logged = point.also { println("using $it") }

    val r = point.run { x + y }
}

// === DESTRUCTURING ===

fun destructuringDemo() {
    val (x, y) = Point(10.0, 20.0)
    val (key, value) = "key=value".split("=").let { it[0] to it[1] }
}

// === STRING TEMPLATES & RAW STRINGS ===

val stringTemplates = """
    Hello, ${"Kotlin"}!
    name = $readOnly
    sum = ${2 + 2}
""".trimIndent()

// === LITERALS ===

val literals = mapOf(
    "int" to 42,
    "long" to 123L,
    "float" to 3.14f,
    "double" to 2.718,
    "hex" to 0xFF,
    "binary" to 0b1010,
    "unsigned" to 42u,
    "bool-true" to true,
    "bool-false" to false,
    "char" to 'A',
    "string" to "text",
    "null" to null
)

// === ANNOTATIONS ===

@Suppress("UNUSED_PARAMETER")
fun annotated(param: String) {}

annotation class Fancy(val description: String = "")

@Fancy("example")
fun fancyFunction() {}

// === REQUIRE / CHECK / ASSERT ===

fun contractChecks(value: Int, list: List<Int>) {
    require(value >= 0) { "value must be non-negative, was $value" }
    check(list.isNotEmpty()) { "list must not be empty" }
    assert(value <= 100)
}

// === TODO / NOTHING / UNIT / ANY ===

fun todoPlaceholder(): Nothing = TODO("implement later")

fun returnsUnit(): Unit = Unit

fun identity(value: Any?): Any? = value

fun nullableNothing(): Nothing? = null

// === GENERICS WITH VARIANCE AND WHERE ===

class Box<out T>(val value: T)  // covariant out

interface Consumer<in T> {      // contravariant in
    fun consume(item: T)
}

fun <T> singleton(item: T): List<T> = listOf(item)

fun <T : Comparable<T>> maxOf(a: T, b: T): T = if (a >= b) a else b

fun <T> Iterable<T>.filtered(
    predicate: Predicate<T>
): List<T> where T : Any = filter(predicate)

// === TYPE CHECKS & CASTS ===

fun typeChecks(value: Any) {
    val isString = value is String
    val notString = value !is String

    // Safe cast
    val str: String? = value as? String

    // Unsafe cast (would throw)
    // val forced: String = value as String
}

// === USAGE BLOCK (no main needed) ===

fun usageBlock() {
    // Named args
    createPoint(y = 5, x = 3)

    // Infix call
    val result = 3 plusTimes 4  // (3+4)*4 = 28

    // Operator overloads
    var v1 = Vector(1, 2)
    val v2 = Vector(3, 4)
    val sum = v1 + v2       // plus
    v1++                    // inc
    val first = v1[0]       // get

    // Extension
    "hello".exclaim()

    // Delegated
    println(lazyValue)
    observed = 1

    // Smart cast via is + when exhaustive
    smartCastDemo("text")

    // Destructuring
    destructuringDemo()

    // Scope
    scopeFunctionsDemo()

    // Null safety
    nullSafetyDemo()

    // Collections
    collectionsDemo()

    // Type alias & higher-order
    listOf(1, 2, 3).customFilter { it > 1 }

    // String templates
    println(stringTemplates)

    // Enum exhaustive when
    val suit = Suit.HEARTS
    println(suit.isRed())

    // Companion
    val db = Database.getInstance()
}
