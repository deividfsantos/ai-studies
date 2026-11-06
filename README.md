# AI Study Roadmap for Java Developers

A 6-month plan (26 weeks, 4 to 6 hours per week) to go from *using* AI tools like Claude to *building* production systems with large language models, written for backend Java developers.

> Library versions and release dates checked on October 23, 2026.

## Table of contents

- [How to use this roadmap](#how-to-use-this-roadmap)
- [Schedule](#schedule)
- [Phase 1 — Core concepts](#phase-1--core-concepts-weeks-1-to-2)
- [Phase 2 — How LLMs work under the hood](#phase-2--how-llms-work-under-the-hood-weeks-3-to-5)
- [Phase 3 — Using LLMs as an engineer](#phase-3--using-llms-as-an-engineer-weeks-6-to-9)
- [Phase 4 — AI in the Java ecosystem](#phase-4--ai-in-the-java-ecosystem-weeks-10-to-13)
- [Phase 5 — RAG and vector databases](#phase-5--rag-and-vector-databases-weeks-14-to-17)
- [Phase 6 — Agents and MCP](#phase-6--agents-and-mcp-weeks-18-to-22)
- [Phase 7 — AI in production](#phase-7--ai-in-production-weeks-23-to-26)
- [Phase 8 — Optional deep dive](#phase-8--optional-deep-dive-after-month-6)
- [Portfolio projects](#portfolio-projects)
- [Recommended resources](#recommended-resources)

## How to use this roadmap

The plan has 7 phases over **6 months (26 weeks) at 4 to 6 hours per week**, plus an optional Phase 8 for later. The goal is to move from "Claude user" to **AI engineer**: someone who understands what happens inside the model and can build systems around it.

You already know backend, APIs and databases, and most real AI work is software engineering around a model. So the focus is on building in Java; the math comes last.

- **Every phase ends with a small project.** The projects add up to a single system.
- **Do Phases 1 to 3 in order;** Phases 4 to 7 can be reordered to match your work.
- **Use Claude as a tutor:** after each topic, ask it to quiz you and explain what you got wrong.

## Schedule

| Phase | Weeks | Focus |
| --- | --- | --- |
| **1** | 1–2 | Core concepts |
| **2** | 3–5 | How LLMs work |
| **3** | 6–9 | LLMs via API |
| 4 | 10–13 | Java ecosystem |
| 5 | 14–17 | RAG and vectors |
| 6 | 18–22 | Agents and MCP |
| 7 | 23–26 | Production |
| 8 *(optional)* | after week 26 | Deep dive |

Phases 1 to 3 (in bold) are the foundation and should be done in order. After week 9, prioritize whichever phase your job needs first.

**Weekly routine (4 to 6 hours):** one theory session of 1.5 to 2 hours (videos or reading) and one practice session of 2.5 to 4 hours building the phase project. If a week gets tight, cut theory, not practice.

## Phase 1 — Core concepts (weeks 1 to 2)

Goal: be able to explain to a colleague what an LLM is and where it fits within "AI".

- **Hierarchy of terms:** AI ⊃ Machine Learning ⊃ Deep Learning ⊃ Generative AI and LLMs.
- **Types of learning:** supervised, unsupervised and reinforcement learning, with one example of each.
- **Model, training and inference:** a model is a function with billions of parameters tuned during training (expensive, done once); inference is using the finished model.
- **Neural networks, intuitively:** layers, gradient descent and backpropagation, no calculus.
- **The landscape:** the main labs and the difference between closed and open-weights models.

**Practice:** watch 3Blue1Brown's neural network series, then ask Claude for a 10-question quiz on it and review the ones you miss.

- [ ] I can explain the difference between AI, ML, Deep Learning and LLMs

### Quiz — Phase 1 fundamentals

Answer each question before expanding the answer.

**1. Which option correctly describes the relationship between these terms?**

- A) AI ⊃ Machine Learning ⊃ Deep Learning ⊃ LLMs
- B) Machine Learning ⊃ AI ⊃ Deep Learning ⊃ LLMs
- C) Deep Learning ⊃ Machine Learning ⊃ AI ⊃ LLMs
- D) LLMs ⊃ Deep Learning ⊃ Machine Learning ⊃ AI

<details>
<summary>Answer</summary>

**A** — AI is the broadest field. Machine Learning is the part of AI that learns from data, Deep Learning is ML with deep neural networks, and LLMs are one kind of Deep Learning model.

</details>

**2. A model learns to flag emails as spam from thousands of emails that were already labeled. What type of learning is this?**

- A) Unsupervised
- B) Supervised
- C) Reinforcement
- D) Generative

<details>
<summary>Answer</summary>

**B** — Supervised learning uses examples that come with the right answer (the "spam" / "not spam" label) so the model can predict labels for new data.

</details>

**3. Grouping customers by purchasing behavior, without knowing in advance which groups exist, is an example of:**

- A) Supervised learning
- B) Reinforcement learning
- C) Unsupervised learning
- D) Fine-tuning

<details>
<summary>Answer</summary>

**C** — Unsupervised learning has no labels: the model finds structure on its own, as in clustering algorithms.

</details>

**4. An agent learns to play chess by gaining points when it wins and losing points when it loses. What type of learning is this?**

- A) Supervised
- B) Unsupervised
- C) Reinforcement
- D) Transfer

<details>
<summary>Answer</summary>

**C** — In reinforcement learning, an agent acts in an environment and learns from rewards and penalties, without labeled examples of each correct move.

</details>

**5. What are a model's parameters (or weights)?**

- A) The settings a user picks when calling the API, such as temperature
- B) The internal numbers tuned during training that define the model's behavior
- C) Rules written by hand by engineers
- D) The documents used for training

<details>
<summary>Answer</summary>

**B** — Parameters are the numeric values that training adjusts; an LLM has billions of them. Temperature is an inference setting, a different concept.

</details>

**6. What is the difference between training and inference?**

- A) Training adjusts the weights and is expensive; inference uses the finished model to produce outputs
- B) Training happens on every user request; inference happens only once
- C) They are the same thing with different names
- D) Inference adjusts the weights; training only reads data

<details>
<summary>Answer</summary>

**A** — Training runs once (or a few times) with lots of data and compute. Every time you use a chat assistant you are running inference: the weights do not change.

</details>

**7. What role does gradient descent play when training a neural network?**

- A) Splitting text into tokens
- B) Gradually adjusting the weights in the direction that reduces the error
- C) Choosing the network architecture
- D) Generating the final answer for the user

<details>
<summary>Answer</summary>

**B** — Gradient descent works out which way each weight should move to lower the loss function (the error) and takes small steps in that direction, over and over.

</details>

**8. What does backpropagation compute?**

- A) How much each weight contributed to the error, propagating the error from the output back through earlier layers
- B) The next word of the answer
- C) How many layers the network should have
- D) The similarity between two texts

<details>
<summary>Answer</summary>

**A** — Backpropagation computes the gradients: each weight's share of the error, from the last layer to the first. Gradient descent then uses those gradients to update the weights.

</details>

**9. A model scores 99% on its training data but only 60% on new data. What is the most likely problem?**

- A) Underfitting
- B) Overfitting
- C) Missing inference parameters
- D) Hallucination

<details>
<summary>Answer</summary>

**B** — Overfitting means the model memorized the training data instead of learning the general pattern. That is why a separate test set is used: to measure performance on data the model has never seen.

</details>

**10. What does it mean for a model to be "open weights"?**

- A) Anyone can use it for free through the lab's API
- B) The trained weights are published, so you can download and run the model on your own hardware
- C) The training code and data are all public
- D) The model has no safety filters

<details>
<summary>Answer</summary>

**B** — Open weights means the weights are available for download (e.g. Llama, Qwen). It does not guarantee that the training data or code are open. Closed models are only available through an API.

</details>

### Intermediate quiz — Phase 1

Same format as above, with trickier options. Answer each question before expanding the answer.

**1. What does an artificial neuron compute?**

- A) Sums the inputs, multiplies the total by a single weight and applies the activation
- B) Multiplies each input by a weight and returns the largest one, with no bias
- C) Multiplies each input by a weight, sums everything with a bias and applies the activation
- D) Applies the activation to each input, then takes the weighted average using the weights

<details>
<summary>Answer</summary>

**C** — Each input has its own weight; the weighted sum gets the bias added and only then goes through the activation. A single weight for all inputs would make it impossible to give each input a different importance.

</details>

**2. What is the main reason to use activation functions such as ReLU between layers?**

- A) To normalize outputs so they always fall between 0 and 1
- B) To reduce overfitting by switching off some neurons during training
- C) To guarantee that gradients never become too small during training
- D) To let the network represent non-linear relationships between input and output

<details>
<summary>Answer</summary>

**D** — Without activations, several layers collapse into a single linear computation. Squashing outputs into 0–1 is something only the sigmoid does (ReLU does not), and switching off neurons is dropout.

</details>

**3. What is the role of the loss function in training?**

- A) To measure the model's final accuracy on the test set
- B) To turn the prediction error into a number that training tries to minimize
- C) To compute, for each weight, which direction it should move
- D) To set the size of each weight-update step

<details>
<summary>Answer</summary>

**B** — The loss says how wrong the model is. The direction of each update comes from the gradients (backpropagation), and the step size is the learning rate.

</details>

**4. What usually happens when the learning rate is too small?**

- A) The loss oscillates and may diverge because updates overshoot the optimum
- B) The gradients get smaller, causing vanishing gradients
- C) The loss decreases steadily but so slowly that training may stop before reaching a good result
- D) The model memorizes the training data faster and starts overfitting

<details>
<summary>Answer</summary>

**C** — The learning rate is the step size. Steps that are too small mean slow progress; oscillating and diverging are symptoms of steps that are too large. Vanishing gradients depend on the architecture, not on the learning rate.

</details>

**5. What is the difference between a parameter and a hyperparameter?**

- A) Parameters are set before training; hyperparameters are adjusted by gradient descent
- B) Weights are parameters and the bias is a hyperparameter, because it stays fixed during training
- C) Hyperparameters are the parameters of the last layers, which control the output
- D) Parameters are learned during training; hyperparameters are chosen beforehand, like the learning rate

<details>
<summary>Answer</summary>

**D** — Weights and biases are parameters: training finds their values (the bias is learned too). Learning rate, number of layers and number of epochs are hyperparameters that you set.

</details>

**6. A model performs poorly on both the training data and the test data. What is the most likely diagnosis?**

- A) Overfitting: the model memorized the training data and does not generalize
- B) Underfitting: the model is too simple or was not trained enough
- C) Data leakage: information from the test set leaked into training
- D) High variance: the model reacts too strongly to small changes in the data

<details>
<summary>Answer</summary>

**B** — Overfitting and high variance show up as great on training data and poor on test data. Data leakage makes the test results look too good. Poor on both means the model never learned the pattern.

</details>

**7. Why split data into training, validation and test sets instead of just training and test?**

- A) To have a second training set and increase the amount of data
- B) To measure final performance on the validation set and use the test set to tune hyperparameters
- C) To tune hyperparameters on the validation set and keep the test set only for the final measurement
- D) To compute the loss, which cannot be computed on the training set

<details>
<summary>Answer</summary>

**C** — Once you tune a model by looking at a dataset, that dataset no longer measures performance on truly new data. Decisions go to the validation set and the test set is saved for the end. The option with the roles swapped is the classic mistake.

</details>

**8. In a fraud detection system, 95% of transactions are legitimate. A model that always predicts "legitimate" reaches 95% accuracy. What does this show?**

- A) The model is overfitting because it memorized the most common class
- B) The model has high recall because it never misses a legitimate transaction
- C) The model is reasonable but needs more fraud examples to get past 95%
- D) Accuracy is misleading with imbalanced classes: the model catches no fraud at all

<details>
<summary>Answer</summary>

**D** — Recall for the fraud class is 0%: not a single real fraud was detected. The high accuracy comes entirely from the majority class. The model is not reasonable; it is useless for its purpose.

</details>

**9. What does the "7B" in a model name like "Llama 7B" indicate?**

- A) About 7 billion tokens used for training
- B) About 7 billion parameters
- C) About 7 billion neurons
- D) About 7 GB of model file size

<details>
<summary>Answer</summary>

**B** — The number counts parameters (weights and biases), not neurons: each neuron has many weights. Models of this size are trained on trillions of tokens, and in FP16 the file would be around 14 GB.

</details>

**10. A model trained mostly on English text performs worse in Portuguese. Which concept explains this?**

- A) Training data bias: underrepresented languages are learned less well
- B) Tokenization: the tokenizer cannot represent accented characters, so the model loses information
- C) Context window: secondary languages get a smaller window during inference
- D) Overfitting: the model memorized English and therefore forgot other languages

<details>
<summary>Answer</summary>

**A** — A model learns what is in its data, in the proportion it appears. Modern tokenizers handle accented characters fine, and the context window does not change by language.

</details>

## Phase 2 — How LLMs work under the hood (weeks 3 to 5)

Goal: understand why Claude answers, fails and costs what it does. This phase changes how you use the tool the most.

1. **Tokens:** the model reads chunks of text, not words; pricing and limits are measured in tokens.
2. **Embeddings:** texts with similar meaning become nearby vectors. This is the basis of RAG (Phase 5).
3. **Transformers and attention:** each token "looks" at the others to decide what matters. Intuition only.
4. **Next-token prediction:** responses are generated one token at a time, by probability; this is where *temperature* and *top-p* come in.
5. **Training:** pre-training on internet text → instruction tuning → alignment (RLHF, Constitutional AI).
6. **Context and limitations:** the model has no memory between calls, hallucinates and has a knowledge cutoff.

**Practice:** watch Andrej Karpathy's *Intro to Large Language Models* and run a snippet of Java code through an online tokenizer.

- [ ] I can explain tokens, embeddings and attention, and why models hallucinate

## Phase 3 — Using LLMs as an engineer (weeks 6 to 9)

Goal: leave the chat window and talk to the model directly through the API. This is where you stop being a user and become an integrator.

- **Messages API:** the *system*, *user* and *assistant* roles; you resend the full history on every call.
- **Parameters and cost:** `model`, `max_tokens`, `temperature`, price per token, and when to use Haiku, Sonnet or Opus.
- **Streaming:** receiving the response token by token.
- **Prompts and context:** clear instructions, examples, XML tags, and what to put in or leave out of the context window. Read Anthropic's official prompt engineering guide.
- **Structured output:** the model returns JSON that maps to a Java `record`.
- **Tool use:** the model asks to call a function, your code runs it and returns the result. This is the foundation of agents.
- **Prompt caching:** lowering cost for repeated context.

**Practice:** create an API key in the Claude Console, make a call with `curl`, then use the official Java SDK (`com.anthropic:anthropic-java`).

**Project:** a CLI that takes an error log and returns structured JSON (`severity`, `likely cause`, `suggestion`), with a tool that looks up local runbooks.

- [ ] I implemented tool use and structured output in Java

## Phase 4 — AI in the Java ecosystem (weeks 10 to 13)

Goal: pick and master an AI framework for Java. If you already use Spring Boot, the recommendation is **Spring AI 2.0** as your main choice and LangChain4j as a second option worth knowing.

| Option | When to use it | Current status |
| --- | --- | --- |
| [Spring AI](https://spring.io/blog/2026/06/12/spring-ai-2-0-0-GA-available-now/) | Spring Boot projects; switch providers without changing code | 2.0 released June 2026 on Spring Boot 4; [2.0.1](https://spring.io/blog/2026/08/21/spring-ai-2-0-1-available-now) in August 2026 |
| [LangChain4j](https://opencollective.com/langchain4j/updates) | Quarkus or non-Spring projects | Near-monthly releases; 1.18.0 in July 2026 |
| [Official Anthropic Java SDK](https://github.com/anthropics/anthropic-sdk-java) | Full control and first access to new Claude features | Maintained by Anthropic, Java 8+ |

**What to study in Spring AI:** `ChatClient`, *advisors* (the chain that memory, RAG and tools flow through), structured output to a `record`, tools with `@Tool`, `ChatMemory` and the Micrometer integration.

**Project:** turn the Phase 3 CLI into a Spring Boot service with a REST endpoint, conversation memory in Postgres and a `@Tool` that queries your database.

- [ ] I have a Spring AI service running with memory and tools

## Phase 5 — RAG and vector databases (weeks 14 to 17)

Goal: make the model answer from **your** data without training anything. RAG (*Retrieval-Augmented Generation*) is the most common pattern in enterprise AI.

**How it works:** during ingestion, you split documents into pieces (*chunking*), generate an embedding for each and store them in a vector database. At query time, you find the pieces most similar to the question and put them in the prompt.

- **Embedding models** and cosine similarity, intuition only.
- **pgvector on Postgres:** start with the database you already know.
- **Chunking:** fixed size, by paragraph or by heading. This is where most RAG systems fail.
- **Hybrid search and reranking:** combining vector and keyword search, then reordering results.
- **When not to use RAG:** if the documents fit in the context window, sometimes sending them whole is enough.

**Project:** an "ask the docs" service with Spring AI and pgvector over your team's READMEs and ADRs, citing the source file. Ask Claude to generate 20 questions with known answers from those docs, skim them for accuracy, and measure how many your system gets right.

- [ ] I have a RAG system on pgvector and can measure its accuracy

## Phase 6 — Agents and MCP (weeks 18 to 22)

Goal: build systems where the model decides the next steps by using tools in a loop, the way Claude does in your daily work.

- **The agentic loop:** task → the model calls a tool → your code runs it → the result goes back → repeat until done.
- **Workflows vs. agents:** read Anthropic's *Building Effective Agents*; simple patterns solve most cases.
- **MCP (Model Context Protocol):** an open standard for connecting models to tools and data. Use the [official MCP Java SDK](https://java.sdk.modelcontextprotocol.io/latest) or the Spring AI 2.0 support.
- **Claude Code internals:** subagents, skills, hooks and CLAUDE.md.
- **Human-in-the-loop:** when an agent should stop and ask for approval.

**Project:** implement the agentic loop by hand with the Java SDK (3 tools, no framework). Then build an **MCP server in Java** that exposes something from your job (logs, a staging database, tickets) and use it from Claude.

- [ ] I published an MCP server in Java and use it from Claude

## Phase 7 — AI in production (weeks 23 to 26)

Goal: run an LLM-based system with the same rigor as any other service. This is where your backend experience becomes a real advantage.

- **Evals:** a set of test cases with metrics, including *LLM-as-a-judge*, running in CI on every prompt change.
- **Observability:** prompt, response, tokens, latency and cost per request, with OpenTelemetry or Langfuse.
- **Cost and resilience:** the smallest model that works, caching, timeouts, retries and model fallback (Resilience4j works).
- **Security:** *prompt injection* (malicious instructions hidden in documents the model reads), least-privilege tools, and the OWASP Top 10 for LLM Applications.
- **Data privacy:** what can and cannot be sent to the API under your local regulations (GDPR, LGPD, etc.).

**Project:** take the Phase 5 RAG system to "production": evals in CI, tracing, cost per request and a prompt injection test.

- [ ] I have automated evals and know the cost of each AI endpoint

## Phase 8 — Optional deep dive (after month 6)

For those who want to understand the math and how models are trained. It sits outside the 6-month plan; pick a single track, at your own pace, and learn **Python**, the lingua franca of the field.

- **An LLM from scratch:** Andrej Karpathy's *Neural Networks: Zero to Hero* series, where you build a small GPT. It is the best material for understanding LLMs from the inside.
- **Local models:** run Llama, Qwen or DeepSeek with Ollama and plug them into your Spring AI project.
- **Math foundations and classic ML:** linear algebra, probability and Andrew Ng's *Machine Learning Specialization*.
- **Fine-tuning:** LoRA and when it is worth it (rarely, compared to prompting and RAG).

## Portfolio projects

| Phase | Project | What it shows |
| --- | --- | --- |
| 3 | CLI that analyzes error logs and returns JSON, with a runbook tool | API, tool use, structured output |
| 4 | Spring AI service with Postgres memory and `@Tool` | Java framework |
| 5 | "Ask the docs" with pgvector | RAG |
| 6 | MCP server in Java used from Claude | Agents and MCP |
| 7 | The Phase 5 RAG system with evals, tracing and cost tracking | AI in production |

By the end of the 6 months, these projects together form an internal assistant for your team.

## Recommended resources

A short list, one or two resources per phase. If you only pick three: Karpathy's videos, the book *AI Engineering* and Anthropic's documentation.

| Resource | Type | Phase |
| --- | --- | --- |
| 3Blue1Brown — *Neural Networks* series (YouTube) | Video | 1 |
| Andrej Karpathy — *Intro to Large Language Models* (YouTube) | Video | 2 |
| [Anthropic prompt engineering guide](https://docs.claude.com/en/docs/build-with-claude/prompt-engineering/overview) | Docs | 3 |
| [Official Anthropic Java SDK](https://github.com/anthropics/anthropic-sdk-java) | Code + examples | 3 |
| [Spring AI 2.0](https://spring.io/blog/2026/06/12/spring-ai-2-0-0-GA-available-now/) | Docs | 4–7 |
| Anthropic — *Building Effective Agents* (blog) | Article | 6 |
| [Model Context Protocol — Java SDK](https://modelcontextprotocol.io/sdk/java/mcp-overview) | Docs | 6 |
| Chip Huyen — *AI Engineering* (O'Reilly, 2025) | Book | 3–7 |
| [Andrej Karpathy — Neural Networks: Zero to Hero](https://karpathy.ai/zero-to-hero.html) | Video course | 8 |
