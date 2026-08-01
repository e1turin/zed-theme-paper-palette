# Markdown Sample for Alabaster Theme

## Syntax Highlighting Demonstration

This document demonstrates how Markdown syntax elements appear **inside flowing text** — because syntax highlighting matters most when markup is embedded in real prose, not just listed in isolation.

---

### Headings in Context

# Level 1 Heading

The quick brown fox jumps over the lazy dog. This paragraph follows an H1 heading to show how the thematic break between heading levels and body text renders.

## Level 2 Heading

In publishing and graphic design, **Lorem ipsum** is a placeholder text commonly used to demonstrate the visual form of a document. Here we have an H2 followed by a paragraph that contains `inline code`, a [hyperlink to somewhere](https://example.com), and some *italicised text* for good measure.

### Level 3 Heading

This paragraph sits under an H3. It contains **bold text**, ~~strikethrough~~, and even a bit of `code`. The purpose is to see how these inline elements interact with surrounding prose when highlighted by the theme.

#### Level 4 Heading

An H4 with more body text. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. H~2~O and X^2^ + Y^2^ = Z^2^ demonstrate subscript and superscript.

##### Level 5 Heading

H5 level. This is _emphasised text_ within a longer paragraph that also includes a **bold phrase** and a `monospace fragment`. The highlighting should clearly distinguish these from the plain text around them.

###### Level 6 Heading

H6 is the deepest heading level. Even here, inline formatting like ~~strikethrough~~ and `code` should be visually distinct from the paragraph text that follows the heading.

---

### Inline Formatting in Paragraphs

The following paragraph demonstrates how multiple inline formatting elements interact with surrounding prose:

This is a **bold term** embedded in a sentence, alongside an *italicised phrase*, some ~~strikethrough text~~, and `inline code fragments`. Sometimes you see ***bold and italic*** combined, or a [reference link][ref] followed by a footnote marker[^1]. The theme must clearly distinguish each of these from the plain text that surrounds them.

Another paragraph with a different mix: Here `code` appears mid-sentence, followed by *italic*, then **bold**, then ~~strikethrough~~, then back to plain text. The transition between each formatting mode should be visible in the syntax highlighting.

> A blockquote with **bold**, *italic*, and `code` inline elements. Blockquotes often contain multiple paragraphs, like this one. The quote styling should apply to the entire block while inline formatting within it remains distinct.
>
> > A nested blockquote with its own inline formatting: Here is some **important text** and a `key` reference.

---

### Lists with Real Content

#### Unordered List

- The first item in an unordered list, containing **bold text** and a `config.setting` reference.
- Another list item that includes an [inline link](https://example.com/page) and some *italicised content* for context.
  - A nested item under the second bullet, demonstrating how indentation highlights at different levels.
    - Deeply nested item with `code`, **bold**, and a [link](https://example.com).
- Back to the top level with more prose explaining the context of this list.

#### Ordered List

1. First step in a procedure: configure the `settings.json` file with the appropriate **theme name**.
2. Second step: run the build command and check the *output logs* for any warnings.
3. Third step: verify that all `sample.*` files render correctly under the new theme.
   1. Sub-step A: open each file in the editor.
   2. Sub-step B: visually inspect the syntax highlighting for each language construct.
4. Fourth step: commit the changes and open a pull request for review.

#### Task List

- [x] **Completed task** — the `deploy.yml` workflow has been updated.
- [ ] **Pending task** — write documentation for the new API endpoints.
- [ ] Another pending task that includes a `code reference` and a [task link](https://example.com).

---

### Code Blocks with Prose Context

The paragraph before a code block often introduces what the code does. For example, the following JavaScript function demonstrates a simple greeting:

```javascript
function greet(name) {
  console.log(`Hello, ${name}!`);
}
```

After the code block, the prose continues to explain the output. This is important for seeing how the code block's border and background interact with the surrounding text.

A Python example with type annotations:

```python
def fibonacci(n: int) -> int:
    """Return the nth Fibonacci number."""
    if n <= 1:
        return n
    return fibonacci(n - 1) + fibonacci(n - 2)
```

And a Rust snippet showing ownership:

```rust
fn main() {
    let message = "Hello, Rust!";
    println!("{}", message);
}
```

---

### Tables

The following table summarises configuration options. Table headers should be visually distinct from body cells, and alignment markers (`:---`, `:---:`, `---:`) must be highlighted correctly.

| Setting       | Type    | Default  | Description                          |
| :------------ | :-----: | -------: | :----------------------------------- |
| `theme`       | String  | `"dark"` | The colour theme for the editor      |
| `font.size`   | Number  |    `14`  | Base font size in pixels             |
| `line.height` | Number  |   `1.5`  | Line height as a multiplier          |
| `tab.size`    | Number  |     `4`  | Number of spaces per tab             |

A second table with different column counts and alignment for comparison:

| Left-aligned | Center-aligned | Right-aligned |
| :----------- | :------------: | ------------: |
| apple        |    banana      |        cherry |
| date         |    elderberry  |          fig  |

---

### Links, Images, and References

Links can appear inline as part of a sentence: visit the [Typst website](https://typst.app/) for more information, or check the [documentation](https://typst.app/docs) for reference. Automatic links like <https://example.com> and email addresses like <user@example.com> should also be highlighted.

Reference-style links keep the prose clean: the [Alabaster theme][alabaster] provides a **light colour scheme** for syntax highlighting. Multiple references to the [same link][alabaster] demonstrate how the label system works.

[alabaster]: https://example.com/alabaster "Alabaster Theme"
[ref]: https://example.com/reference "Reference"

Images with alt text: ![Example diagram](https://example.com/diagram.png) The image syntax should not break the surrounding paragraph flow.

Footnotes provide additional context without cluttering the main text[^2]. This is especially useful for technical writing where citations are needed.

[^1]: This footnote contains some `code` and **bold formatting**.
[^2]: A second footnote with *italic text* and a [link](https://example.com).

---

### LaTeX Math

Inline math like $E = mc^2$ appears within the text flow, while block-level equations are displayed on their own:

$$
\frac{d}{dx} \left( \int_{a}^{x} f(t) \, dt \right) = f(x)
$$

The transition from prose to math and back should be clearly visible in the syntax highlighting.

---

### Definition Lists and Abbreviations

**Term One**
: The definition for term one, which may include `code` and **bold** within the definition text. This demonstrates how definition terms and their descriptions are visually separated.

**HTTP**
: The Hypertext Transfer Protocol is the foundation of data communication on the World Wide Web.

The W3C defines web standards. The abbreviation marker should highlight correctly.

*[W3C]: World Wide Web Consortium
*[HTTP]: Hypertext Transfer Protocol

---

### Emoji and Escaped Characters

Emoji shortcodes like :smile: :rocket: :+1: :fire: appear inline and should be highlighted distinctly from regular text.

Escaped characters show literal rendering: \*this is not bold\*, \`not code\`, and \[not a link\]. These should appear with the escape character highlighted.

---

### Comments and HTML

<!-- This is an HTML comment embedded in Markdown. The comment syntax should be clearly highlighted. -->

[comment]: # (This is a reference-style comment, also highlighted.)

<div>
  <p>Inline HTML like this <strong>paragraph</strong> with a <code>span</code> inside should be highlighted as HTML even within a Markdown document.</p>
</div>

<details>
  <summary>Click to expand</summary>
  Hidden content with **bold** and `code` formatting.
</details>
