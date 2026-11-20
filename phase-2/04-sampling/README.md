# 04 — Temperature and hallucination

Two quick experiments with a real model through the Anthropic Messages API.

**Temperature.** The same prompt runs 5 times at temperature 0 and 5 times at temperature 1. At 0 the model almost always picks the most likely next token, so the answers should repeat. At 1 it samples more freely, so the answers should vary. Compare with the bigram model in [02](../02-bigram/): it is the same idea at a much larger scale.

**Hallucination.** The model gets three questions about things that do not exist: a Java method, a Spring Boot version and a research paper. A model predicts plausible text, not true text, so it may confidently describe them. Newer models are better at admitting they do not know, which is exactly what this checks.

## Run

Requires Java 21 and an API key from the Claude Console. The whole run is 13 short requests and costs a fraction of a cent with the default model.

```bash
export ANTHROPIC_API_KEY=your-key
java SamplingExperiment.java
```

The program prints each answer and writes them to `RESULTS.md`. Set `MODEL` to compare models. After running, fill in the "Did the model admit it does not exist?" lines in `RESULTS.md`.
