import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Counts how many tokens different kinds of text use, with a local model running in Ollama.
 * Ollama reports how many prompt tokens the model read in "prompt_eval_count".
 *
 * Run: java TokenCounter.java   (Ollama must be running, see README)
 */
public class TokenCounter {

    private static final String OLLAMA_URL = System.getenv().getOrDefault("OLLAMA_URL", "http://localhost:11434");
    private static final String MODEL = System.getenv().getOrDefault("MODEL", "llama3.2");

    public static void main(String[] args) throws Exception {
        Map<String, String> samples = new LinkedHashMap<>();
        samples.put("English sentence",
                "The payment service is down because the database connection pool is exhausted.");
        samples.put("Portuguese sentence (same meaning)",
                "O serviço de pagamento está fora do ar porque o pool de conexões do banco de dados se esgotou.");
        samples.put("Java code",
                """
                public record Order(String id, BigDecimal total) {
                    public boolean isLarge() {
                        return total.compareTo(new BigDecimal("1000")) > 0;
                    }
                }""");
        samples.put("JSON",
                """
                {"orderId": "A-1029", "status": "PAID", "items": [{"sku": "X1", "qty": 2}], "total": 149.90}""");
        samples.put("Repeated word",
                "token token token token token token token token token token");

        HttpClient client = HttpClient.newHttpClient();
        StringBuilder results = new StringBuilder();
        results.append("# Token counting results\n\n");
        results.append("Model: `").append(MODEL).append("` (local, via Ollama)\n\n");
        results.append("| Sample | Characters | Tokens | Characters per token |\n");
        results.append("| --- | --- | --- | --- |\n");

        for (Map.Entry<String, String> sample : samples.entrySet()) {
            int tokens = countTokens(client, sample.getValue());
            int chars = sample.getValue().length();
            String ratio = String.format("%.2f", (double) chars / tokens);
            System.out.printf("%-36s %5d chars  %4d tokens  %s chars/token%n",
                    sample.getKey(), chars, tokens, ratio);
            results.append("| ").append(sample.getKey()).append(" | ").append(chars)
                    .append(" | ").append(tokens).append(" | ").append(ratio).append(" |\n");
        }

        results.append("\nNote: each model has its own tokenizer, so other models give different counts.\n");
        Files.writeString(Path.of("RESULTS.md"), results.toString());
        System.out.println("\nResults written to RESULTS.md");
    }

    private static int countTokens(HttpClient client, String text) throws Exception {
        // raw = true sends the text as-is, without the chat template adding extra tokens.
        // num_predict = 1 stops right away: we only care about the prompt.
        String body = """
                {"model": "%s", "prompt": "%s", "raw": true, "stream": false, "options": {"num_predict": 1}}"""
                .formatted(MODEL, escapeJson(text));

        HttpRequest request = HttpRequest.newBuilder(URI.create(OLLAMA_URL + "/api/generate"))
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IllegalStateException("Ollama error " + response.statusCode() + ": " + response.body());
        }

        Matcher matcher = Pattern.compile("\"prompt_eval_count\"\\s*:\\s*(\\d+)").matcher(response.body());
        if (!matcher.find()) {
            throw new IllegalStateException("Unexpected response: " + response.body());
        }
        return Integer.parseInt(matcher.group(1));
    }

    private static String escapeJson(String text) {
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
