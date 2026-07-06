# Markdown Sample for Alabaster Theme
## Demonstrates common Markdown syntax highlighting

### Headings

# Heading Level 1
## Heading Level 2
### Heading Level 3
#### Heading Level 4
##### Heading Level 5
###### Heading Level 6

### Paragraphs and Text Formatting

This is a regular paragraph. Lorem ipsum dolor sit amet, consectetur adipiscing elit.

This is **bold text** using double asterisks.
This is __bold text__ using double underscores.
This is *italic text* using single asterisks.
This is _italic text_ using single underscores.
This is ***bold and italic*** using triple asterisks.
This is ~~strikethrough~~ text.
This is `inline code` within a paragraph.
This is a [hyperlink](https://example.com) with a title.

### Blockquotes

> This is a blockquote.
> It can span multiple lines.
>
> > And it supports nested blockquotes.
>
> This is the outer blockquote again.

### Lists

#### Unordered List

- Item one
- Item two
- Item three
  - Nested item A
  - Nested item B
    - Deeply nested item
- Item four

#### Ordered List

1. First item
2. Second item
3. Third item
   1. Sub-item A
   2. Sub-item B
4. Fourth item

#### Task List

- [x] Completed task
- [ ] Incomplete task
- [ ] Another pending task

### Code Blocks

#### Fenced Code Block (no language)

```
This is a plain fenced code block.
No syntax highlighting applied.
```

#### Fenced Code Block (with language)

```javascript
function greet(name) {
  console.log(`Hello, ${name}!`);
}

const result = greet("World");
```

```python
def fibonacci(n: int) -> int:
    if n <= 1:
        return n
    return fibonacci(n - 1) + fibonacci(n - 2)
```

```rust
fn main() {
    let message = "Hello, Rust!";
    println!("{}", message);
}
```

#### Indented Code Block

    This is an indented code block.
    It uses four spaces of indentation.
    No language identifier needed.

### Horizontal Rules

---

***

___

### Tables

| Syntax       | Description |     Score |
| :----------- | :---------: | --------: |
| Header       |    Title    | $1,000.00 |
| Paragraph    |    Text     |    $25.00 |
| Code         |   Inline    |     $5.00 |

| Left-aligned | Center-aligned | Right-aligned |
| :----------- | :------------: | ------------: |
| cell         |     cell       |         cell  |
| cell         |     cell       |         cell  |

### Links

[Inline link](https://example.com/page)
[Link with title](https://example.com "Example Title")
<https://example.com/automatic-link>
<automatic@email.address>

### Images

![Alt text for image](https://example.com/image.png)
![Image with title](https://example.com/photo.jpg "Photo Title")

### Reference-style Links

This is a [reference link][ref-label].
This is another [reference link][ref-label].

[ref-label]: https://example.com/reference "Reference Title"

### Footnotes

Here is a sentence with a footnote[^1].

[^1]: This is the footnote content.

### Definition Lists

Term One
: Definition for term one.

Term Two
: Definition for term two.
: Another definition for the same term.

### Abbreviations

The HTML specification is maintained by the W3C.

*[W3C]: World Wide Web Consortium

### Inline HTML

<p>This is an inline HTML paragraph with <strong>bold text</strong> and <em>italic text</em>.</p>

<details>
<summary>Click to expand</summary>

Hidden content that can be expanded.

- List item inside details
- Another list item

</details>

### Mathematical Expressions (LaTeX)

Inline math: $E = mc^2$

Block math:

$$
\frac{d}{dx} \left( \int_{a}^{x} f(t) \, dt \right) = f(x)
$$

### Emoji

:smile: :rocket: :+1: :fire: :warning:

### Escaped Characters

\*literal asterisks\*
\`literal backticks\`
\[literal brackets\]

### Comments

<!-- This is an HTML comment that should be highlighted -->

[comment]: # (This is a reference-style comment)

### Superscript and Subscript

H~2~O is water. X^2^ + Y^2^ = Z^2^
