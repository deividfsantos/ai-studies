# 03 — Embeddings and semantic similarity

An embedding model turns a piece of text into a vector of numbers. Texts with similar meaning end up pointing in a similar direction, which is measured with cosine similarity. This is the foundation of semantic search and RAG (Phase 5).

This experiment embeds 10 sentences with a small model that runs inside the JVM (all-MiniLM-L6-v2, through LangChain4j) and compares every pair.

## Run

Requires Java 21 and Maven. No API key needed; the model is downloaded once as a Maven dependency.

```bash
mvn compile exec:java
```

The program prints the closest match for each sentence and writes a full similarity matrix to `RESULTS.md`.

## What to look for

- "The server is down" and "The API is not responding" share no important words but should score high.
- "Deploy the new version on Friday" and "Release the update at the end of the week" should find each other.
- Unrelated pairs (outage vs. bakery) should score close to 0.
- Cosine similarity is implemented by hand in `cosineSimilarity`: it is just a dot product divided by the vector lengths.
