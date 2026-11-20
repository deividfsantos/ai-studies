# 01 — Tokens

Models do not read words, they read tokens. Price, rate limits and the context window are all measured in tokens, so it pays to know how much a given text costs.

This experiment sends the same kinds of text a backend developer deals with every day (English, Portuguese, Java code, JSON) to a local model and compares how many tokens each one uses.

## Run

Requires Java 21 and [Ollama](https://ollama.com) running locally. Everything is free and offline.

```bash
ollama pull llama3.2      # one-time download, about 2 GB
java TokenCounter.java
```

The program prints a table and writes it to `RESULTS.md`. Set `MODEL` to try another model you have pulled (each model has its own tokenizer).

## What to look for

- How many more tokens Portuguese needs than English for the same sentence.
- How dense code and JSON are compared to prose.
- What happens with a word that repeats.
