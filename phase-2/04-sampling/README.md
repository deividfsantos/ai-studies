# 04 — Temperature and hallucination

Two quick experiments with a real LLM running locally in Ollama.

**Temperature.** The same prompt runs 5 times at temperature 0 and 5 times at temperature 1. At 0 the model almost always picks the most likely next token, so the answers should repeat. At 1 it samples more freely, so the answers should vary. Compare with the bigram model in [02](../02-bigram/): it is the same idea at a much larger scale.

**Hallucination.** The model gets three questions about things that do not exist: a Java method, a Spring Boot version and a research paper. A model predicts plausible text, not true text, so it may confidently describe them. Small local models hallucinate much more than large frontier models, which makes the effect easy to see.

## Run

Requires Java 21 and [Ollama](https://ollama.com) running locally. Everything is free and offline.

```bash
ollama pull llama3.2      # one-time download, about 2 GB
java SamplingExperiment.java
```

The program prints each answer and writes them to `RESULTS.md`. Set `MODEL` to compare models you have pulled. After running, fill in the "Did the model admit it does not exist?" lines in `RESULTS.md`.
