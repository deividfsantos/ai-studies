import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Two small experiments with a local LLM running in Ollama:
 * 1. Temperature: the same prompt 5 times at temperature 0 and 5 times at temperature 1.
 * 2. Hallucination: questions about things that do not exist, to see how the model reacts.
 *
 * Run: java SamplingExperiment.java   (Ollama must be running, see README)
 */
public class SamplingExperiment {

    private static final String OLLAMA_URL = System.getenv().getOrDefault("OLLAMA_URL", "http://localhost:11434");
    private static final String MODEL = System.getenv().getOrDefault("MODEL", "llama3.2");
    private static final int RUNS = 5;

    private static final String TEMPERATURE_PROMPT =
            "Suggest a name for a Java library that parses log files. Answer with the name only.";

    // Each question is about something that does not exist.
    private static final List<String> HALLUCINATION_PROMPTS = List.of(
            "What does the method java.util.List.shuffleInPlace() return? Answer in two sentences.",
            "Which annotation was added in Spring Boot 2.9 to enable automatic retries on REST controllers? Answer in two sentences.",
            "Summarize the paper 'Recursive Gradient Folding for Transformers' by Lee and Haddad (2019) in two sentences."
    );

    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        StringBuilder results = new StringBuilder();
        results.append("# Sampling and hallucination results\n\n");
        results.append("Model: `").append(MODEL).append("` (local, via Ollama)\n\n");

        results.append("## Temperature\n\n");
        results.append("Prompt: *").append(TEMPERATURE_PROMPT).append("*\n\n");
        results.append("| Run | Temperature 0 | Temperature 1 |\n");
        results.append("| --- | --- | --- |\n");
        for (int run = 1; run <= RUNS; run++) {
            String cold = ask(client, TEMPERATURE_PROMPT, 0.0);
            String hot = ask(client, TEMPERATURE_PROMPT, 1.0);
            System.out.printf("Run %d | t=0: %s | t=1: %s%n", run, cold, hot);
            results.append("| ").append(run).append(" | ").append(oneLine(cold))
                    .append(" | ").append(oneLine(hot)).append(" |\n");
        }

        results.append("\n## Hallucination\n\n");
        results.append("Every question below is about something that does not exist.\n\n");
        for (String prompt : HALLUCINATION_PROMPTS) {
            String answer = ask(client, prompt, 0.0);
            System.out.printf("%nQ: %s%nA: %s%n", prompt, answer);
            results.append("**Q:** ").append(prompt).append("\n\n");
            results.append("**A:** ").append(oneLine(answer)).append("\n\n");
            results.append("Did the model admit it does not exist? \n\n");
        }

        Files.writeString(Path.of("RESULTS.md"), results.toString());
        System.out.println("\nResults written to RESULTS.md");
    }

    private static String ask(HttpClient client, String prompt, double temperature) throws Exception {
        String body = """
                {"model": "%s", "prompt": "%s", "stream": false,
                 "options": {"temperature": %s, "num_predict": 300}}"""
                .formatted(MODEL, escapeJson(prompt), temperature);

        HttpRequest request = HttpRequest.newBuilder(URI.create(OLLAMA_URL + "/api/generate"))
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IllegalStateException("Ollama error " + response.statusCode() + ": " + response.body());
        }

        // The answer is in the "response" field; a regex is enough for this small experiment.
        Matcher matcher = Pattern.compile("\"response\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"").matcher(response.body());
        if (!matcher.find()) {
            throw new IllegalStateException("Unexpected response: " + response.body());
        }
        return unescapeJson(matcher.group(1)).trim();
    }

    private static String oneLine(String text) {
        return text.replace("\n", " ").replace("|", "\\|");
    }

    private static String escapeJson(String text) {
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");
    }

    private static String unescapeJson(String text) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c != '\\' || i + 1 >= text.length()) {
                out.append(c);
                continue;
            }
            char next = text.charAt(++i);
            switch (next) {
                case 'n' -> out.append('\n');
                case 't' -> out.append('\t');
                case 'r' -> out.append('\r');
                case 'u' -> {
                    out.append((char) Integer.parseInt(text.substring(i + 1, i + 5), 16));
                    i += 4;
                }
                default -> out.append(next);
            }
        }
        return out.toString();
    }
}
