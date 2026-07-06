// Typst sample theme demonstrates common Typst syntax features

#import "@preview/cetz:0.3.1"
#import "@preview/fletcher:0.5.4"

// Set rules for global styling
#set page(paper: "a4", margin: (x: 2cm, y: 2.5cm), numbering: "1")
#set text(font: "New Computer Modern", size: 11pt, fill: rgb("#333333"))
#set par(justify: true, leading: 0.55em, spacing: 0.55em)
#set heading(numbering: "1.1")

// Show rule: text replacement
#show "Typst": set text(fill: navy, weight: "bold")
#show regex("\bAlabaster\b"): smallcaps

// ============================================
//  Constants and Variables
// ============================================

#let max-size = 1000
#let pi-const = 3.14159
#let debug = true
#let default-name = "Unnamed"

// Mutable counter
#let counter = 0
#let mut-value = 42

// ============================================
//  Functions
// ============================================

#let identity(value) = value

// Function with named arguments and defaults
#let circle-area(radius) = {
  if radius <= 0 {
    return 0.0
  }
  let result = pi-const * radius * radius
  result
}

// Function returning content
#let greet(name) = [
  Hello, #name!
]

// Higher-order function
#let apply-twice(f, value) = f(f(value))

// Function with body block
#let format-block(body, size: 11pt) = {
  set text(size: size)
  body
}

// ============================================
//  Math and Equations
// ============================================

// Inline math
Inline: $E = m c^2$, $x = (-b plus.minus sqrt(b^2 - 4a c)) / (2a)$

// Block math
$ "area" = pi r^2 $

// Multi-line equation with alignment
$ sum_(k=0)^n k
    &= 1 + ... + n \
    &= (n(n+1)) / 2 $

// Matrix
$ mat(1, 2; 3, 4) $

// Vector
$ vec(1, 2, 3) $

// Cases
$ cases(
   x + y = 6,
   x - y = 4,
) $

// Integrals, sums, products
$ integral_0^1 f(x) dif x = pi / 4 $
$ product_(i=1)^m i = m! $
$ lim_(x -> 0) sin(x) / x = 1 $

// Greek letters and symbols
$ alpha, beta, gamma, Gamma, pi, Sigma, omega, Omega $

// ============================================
//  Headings
// ============================================

= Level 1 Heading
== Level 2 Heading
=== Level 3 Heading
==== Level 4 Heading
===== Level 5 Heading

// ============================================
//  Text Formatting
// ============================================

*Bold text* with double asterisks.
_Italic text_ with underscores.
`Inline code` with backticks.
#underline[Underlined text.]
#strike[Strikethrough text.]
#smallcaps[Small capitals.]

// Combined formatting
*Bold and _italic_ within bold.*
_A text with `code` inside it._

// Smart quotes
"Double smart quotes" and 'single smart quotes'.

// Dashes
Em-dash --- and en-dash -- and non-breaking space~between.

// ============================================
//  Lists
// ============================================

// Bullet list
- First item
- Second item
  - Nested item A
  - Nested item B
    - Deeply nested item
- Third item

// Numbered list
+ Step one
+ Step two
  + Sub-step A
  + Sub-step B
+ Step three

// Term list
/ Term: Description of the term.
/ Another term: Its description spanning
  multiple lines of explanation.

// ============================================
//  Code / Raw Blocks
// ============================================

// Fenced code block with language
```rust
fn main() {
    let msg = "Hello, Typst!";
    println!("{}", msg);
}
```

// Typst syntax in code block
```typst
#let hello(name) = [
  Hello, #name!
]
```

// Raw block with options
```typc
[theme: "dracula", lang: "python", line-numbers: true]
def fib(n: int) -> int:
    if n <= 1:
        return n
    return fib(n - 1) + fib(n - 2)
```

// ============================================
//  Tables
// ============================================

// Basic table
#table(
  columns: (1fr, auto, auto),
  inset: 10pt,
  align: horizon,
  [Name], [Age], [Score],
  [Alice], [28], [95],
  [Bob], [32], [87],
  [Charlie], [24], [92],
)

// Table with header
#table(
  columns: 3,
  table.header([*Item*], [*Qty*], [*Price*]),
  [Widget], [5], [\$10.00],
  [Gadget], [3], [\$15.00],
  table.footer([Total], [], [\$25.00]),
)

// ============================================
//  Figures and References
// ============================================

// Label and reference
= Introduction <intro>
See the @intro section for details.
See page #ref(<intro>, form: "page").

// Figure with caption
#figure(
  rect(width: 3cm, height: 2cm, fill: blue.lighten(60%)),
  caption: [Sample rectangle],
) <fig:sample>

// Cross-reference to figure
As shown in @fig:sample.

// ============================================
//  Blockquotes and Admonitions
// ============================================

#quote(block: true)[
  To be or not to be, that is the question.
]

#quote(
  block: true,
  attribution: [William Shakespeare],
)[
  All the world's a stage.
]

// ============================================
//  Links
// ============================================

https://typst.app/
#link("https://typst.app/")[Typst]
#link("mailto:hello@typst.app")

// ============================================
//  Numeric and Boolean Literals
// ============================================

#let integer = 42
#let float = 3.14
#let hex = 0xff
#let boolean-true = true
#let boolean-false = false
#let nothing = none
#let default = auto

// Lengths
#let h-length = 1cm
#let v-length = 2pt
#let em-space = 1em

// ============================================
//  Conditionals and Loops
// ============================================

#if debug [
  Debug mode is enabled.
] else [
  Debug mode is disabled.
]

#let status = if integer > 10 { "big" } else { "small" }

// For loop
#for item in ("a", "b", "c") [
  - #item
]

// For with index
#for (i, val) in ("x", "y", "z").enumerate() [
  #i. #val
]

// While loop
#let i = 0
#while i < 3 [
  #i \  // line break after
  #(i = i + 1)
]

// ============================================
//  Arrays, Dictionaries, and Content
// ============================================

// Array
#let items = (1, 2, 3, 4, 5)
#let mixed = (42, "text", true, none)

// Dictionary
#let config = (
  title: "Typst Sample",
  version: "1.0.0",
  debug: true,
  font: "New Computer Modern",
)

// Content block
#let info-box(body) = block(
  fill: rgb("#e8f4f8"),
  inset: 1em,
  radius: 4pt,
  width: 100%,
)[
  *Note:* #body
]

#info-box[This is a sample callout box.]

// ============================================
//  Colors
// ============================================

#let primary = rgb("#336699")
#let secondary = blue
#let bg = luma(240)
#let light = primary.lighten(30%)
#let dark = primary.darken(20%)
#let semi = primary.transparentize(50%)

// ============================================
//  Imports and Includes
// ============================================

// #import "utils.typ": greet
// #include "chapter1.typ"

// ============================================
//  Context Expressions and Metadata
// ============================================

#context [
  Current language: #text.lang
]

#metadata((title: "Sample", version: "1.0.0")) <doc-meta>

// ============================================
//  Patterns: Closures and Folds
// ============================================

#let double = x => x * 2
#let add = (a, b) => a + b

#let sum-items = items.fold(0, (acc, x) => acc + x)
