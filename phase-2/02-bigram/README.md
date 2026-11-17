# 02 — Next-token prediction with a bigram model

An LLM writes one token at a time, always picking the next one from a probability distribution. A bigram model does the same thing in its simplest form: it only looks at the current character to guess the next one.

- **Training** is counting how often each character follows another.
- **Generation** is sampling the next character from those counts, again and again.
- **Temperature** reshapes the probabilities before sampling. Low values almost always pick the most likely character; high values give rare characters more chances.

An LLM replaces the counting table with a neural network that looks at thousands of previous tokens instead of one, but the generation loop is the same idea.

## Run

Requires Java 21. No API key needed.

```bash
java BigramModel.java                  # trains on the repository README
java BigramModel.java my-text.txt      # or on any text file
```

The program prints the generated samples and writes them to `RESULTS.md`.

## What to look for

- At temperature 0.2 the text gets stuck repeating the most common sequences.
- At 1.0 it produces word-like chunks that follow the style of the training text.
- At 2.0 it turns into noise.
