// === COMMENTS ===
// Single-line comments appear above code and describe intent.
/* Multi-line
   block comments can span several lines. */

// === IMPORTS & INCLUDES ===
#import "@preview/cetz:0.3.1"
#import "utils.typ": greet, format-block
#include "chapter1.typ"

// === SET RULES ===
#set page(paper: "a4", margin: (x: 2cm, y: 2.5cm), numbering: "1")
#set text(font: "New Computer Modern", size: 11pt, fill: rgb("#333333"))
#set par(justify: true, leading: 0.55em, spacing: 0.55em)
#set heading(numbering: "1.1")

// === SHOW RULES ===
#show "Typst": set text(fill: navy, weight: "bold")
#show regex("\bAlabaster\b"): smallcaps
#show heading: set text(fill: rgb("#2D5F8A"))
#show "TODO": it => [
  #box(fill: yellow, inset: 2pt)[#it]
]

// ============================================================
// Body text with embedded formatting
// ============================================================

= Introduction

This document demonstrates how Typst's syntax highlighting applies to a real document with flowing text. The purpose is to show that *inline emphasis*, *strong emphasis*, `inline code`, and other constructs remain visually distinct when embedded inside paragraphs rather than listed in isolation.

All Alabaster theme users should verify that the highlighting looks correct across different constructs. The following sections demonstrate each feature in context.

== Text Formatting

This paragraph contains a mix of formatting elements. Here is some *italicised text* to show how it reads inside a sentence, followed by *bold text* for emphasis, and then a `code snippet` for a configuration key. Sometimes you need ~~strikethrough~~ to mark deprecated content. The combination of #underline[underlined], #strike[strikethrough], and #smallcaps[Small Caps] should each be clearly distinguishable.

"Double smart quotes" and 'single smart quotes' appear differently from straight quotes. Em-dashes --- and en-dashes -- also have distinct typographic treatment, and a non-breaking space~prevents~line~breaks.

== Lists in Context

The following list describes the key configuration options for the Alabaster theme:

- The *primary colour* is derived from the `accent` parameter: this controls most UI elements.
- The *background tint* is specified via `luma(240)` — a light grey that reduces eye strain.
  - Nested lists show how indentation levels are highlighted differently.
    - Deep nesting with `code` and *bold* inside the text.
- Back at the top level, a final item demonstrates continuation after nesting.

The numbered steps for setting up the theme are:

+ First, install the theme file into your `.config/zed/themes/` directory.
+ Next, select *Alabaster* from the theme picker in the editor settings.
+ Finally, verify that all `sample.*` files render correctly under the new theme.
  + Sub-step: open each sample file.
  + Sub-step: inspect the highlighting for each language feature.

Term lists describe concepts inline:

/ Theme: A collection of colour and style settings that define the appearance of the editor.
/ Syntax highlighting: The visual distinction of different language constructs using colour and style.

== Code and Raw Blocks

When writing documentation, code blocks appear mid-paragraph after an introductory sentence. For example, here is a Rust function that computes the nth Fibonacci number:

```rust
fn fib(n: u64) -> u64 {
    match n {
        0 => 0,
        1 => 1,
        _ => fib(n - 1) + fib(n - 2),
    }
}
```

The paragraph after the code block should clearly show that the code block's background and border are distinct from the surrounding prose. Here is a Typst code block demonstrating a simple function:

```typst
#let make-title(body) = {
  set text(size: 24pt, weight: "bold")
  body
}
```

And a raw block with theme options:

```typc
[theme: "dracula", lang: "python"]
def greet(name):
    return f"Hello, {name}!"
```

= Tables

The table below summarises the supported colour formats. Table headers are styled differently from body cells, and alignment markers control column layout.

#table(
  columns: (1fr, auto, auto, auto),
  inset: 10pt,
  align: horizon,
  [Format], [Example], [Range], [Alpha],
  [Hex], [#FF0000], [000000—FFFFFF], [No],
  [RGB], [rgb(255, 0, 0)], [0—255], [Yes],
  [Luma], [luma(128)], [0—255], [No],
  table.hline(),
  table.footer([Total], [3 formats], [], []),
)

A second table with a header and footer shows how spanning and repetition work in practice:

#table(
  columns: 3,
  table.header([*Item*], [*Qty*], [*Price*]),
  [Widget A], [5], [\$10.00],
  [Widget B], [3], [\$15.00],
  table.hline(),
  table.footer([Total], [], [\$25.00]),
)

= Figures and Cross-References

Every figure should have a caption and a label so it can be referenced from the text. For example, the rectangle shown in @fig:sample demonstrates the `blue.lighten(60%)` colour transform. You can also reference sections like @intro to link back to the beginning of the document.

#figure(
  rect(width: 3cm, height: 2cm, fill: blue.lighten(60%)),
  caption: [A sample rectangle with a light blue fill],
) <fig:sample>

Blockquotes are used for cited material:

#quote(block: true)[
  To be or not to be, that is the question. Whether 'tis nobler in the mind to suffer the slings and arrows of outrageous fortune, or to take arms against a sea of troubles.
]

#quote(block: true, attribution: [William Shakespeare])[
  All the world's a stage, and all the men and women merely players.
]

= Mathematical Typesetting

Inline math like $E = m c^2$ appears within the text flow. The transition from prose to math mode should be visibly distinct. Block math is displayed on its own lines:

$ "area" = pi r^2 $

Multi-line equations with alignment show structure:

$ sum_(k=0)^n k
    &= 1 + dots + n \
    &= (n(n+1)) / 2 $

Matrices and vectors appear in context:

$ mat(1, 2; 3, 4) quad mat(a, b; c, d) quad vec(1, 2, 3) $

Piecewise definitions use cases:

$ f(x) = cases(
   x^2, if x > 0,
   0, if x = 0,
   -x^2, if x < 0,
) $

Integrals, sums, and limits all appear in mathematical prose:

$ integral_0^1 f(x) dif x = pi / 4 $ and $ lim_(x -> 0) sin(x) / x = 1 $

Greek letters are common in mathematical notation: $ alpha, beta, gamma, Delta, Sigma, Omega $.

Accents modify variables: $ hat(y) = beta_0 + beta_1 x $ and $ dot(x) = dx / dt $.

= Literals and Values

The theme must correctly highlight literals embedded in text. Numbers like 42, 3.14, 0xff, and booleans like true and false should be visually distinct. Special values like none and auto also have specific highlighting.

Lengths: 1cm, 2pt, 1em, 1in, 1mm.
Angles: 90deg, 1rad.
Colours: `red`, `blue`, `rgb("#336699")`, `luma(240)` — each with their own syntax highlighting.

= Context and Metadata

Dynamic content is provided by context blocks. For example, the current page number is #context[#counter(page).display()] and the active language is #context[#text.lang].

The document metadata is embedded via `#metadata()`:

#metadata((title: "Typst Sample", version: "1.0.0")) <doc-meta>

A table of contents can be generated automatically:

#outline()

= Programmatic Constructs

Closures and higher-order functions appear in code blocks alongside regular prose:

#let double = x => x * 2
#let add = (a, b) => a + b

#let sum = items.fold(0, (acc, x) => acc + x)
#let doubled = items.map(x => x * 2)
#let evens = items.filter(x => calc.mod(x, 2) == 0)

Type introspection: #type(42), #repr((1, 2, 3)), #str(42), #int("42"), #float("3.14").

Comparisons: 1 == 1, 1 != 2, 1 < 2, 2 > 1. Booleans: true and false, true or false, not true.

= Conclusion

This sample demonstrates how Typst syntax highlighting behaves in the context of real document content. Each formatting element — whether *bold*, _italic_, `code`, $math$, or a #smallcaps[function call] — should be clearly distinguishable from the surrounding text when rendered under the theme.
