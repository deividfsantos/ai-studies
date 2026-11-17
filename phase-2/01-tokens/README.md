# 01 — Tokens

Models do not read words, they read tokens. Price, rate limits and the context window are all measured in tokens, so it pays to know how much a given text costs.

This experiment sends the same kinds of text a backend developer deals with every day (English, Portuguese, Java code, JSON) to the Anthropic API's `count_tokens` endpoint and compares the results.

## Run

Requires Java 21 and an API key from the Claude Console. Counting tokens is free.

```bash
export ANTHROPIC_API_KEY=your-key
java TokenCounter.java
```

The program prints a table and writes it to `RESULTS.md`. Set `MODEL` to try a different model.

## What to look for

- How many more tokens Portuguese needs than English for the same sentence.
- How dense code and JSON are compared to prose.
- What happens with a word that repeats.
