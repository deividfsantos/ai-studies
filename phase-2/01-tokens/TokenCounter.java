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
 * Counts how many tokens different kinds of text use, with the
 * Anthropic API's count_tokens endpoint (free to call).
 *
 * Run: ANTHROPIC_API_KEY=... java TokenCounter.java
 */
public class TokenCounter {

    private static final String API_URL = "https://api.anthropic.com/v1/messages/count_tokens";
    private static final String MODEL = System.getenv().getOrDefault("MODEL", "claude-sonnet-5-5");

    public static void main(String[] args) throws Exception {
        String apiKey = System.getenv("ANTHROPIC_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            System.err.println("Set the ANTHROPIC_API_KEY environment variable first.");
            System.exit(1);
        }

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
        results.append("Model: `").append(MODEL).append("`\n\n");
        results.append("| Sample | Characters | Tokens | Characters per token |\n");
        results.append("| --- | --- | --- | --- |\n");

        for (Map.Entry<String, String> sample : samples.entrySet()) {
            int tokens = countTokens(client, apiKey, sample.getValue());
            int chars = sample.getValue().length();
            String ratio = String.format("%.2f", (double) chars / tokens);
            System.out.printf("%-36s %5d chars  %4d tokens  %s chars/token%n",
                    sample.getKey(), chars, tokens, ratio);
            results.append("| ").append(sample.getKey()).append(" | ").append(chars)
                    .append(" | ").append(tokens).append(" | ").append(ratio).append(" |\n");
        }

        results.append("\nNote: the count includes a few tokens of message formatting overhead.\n");
        Files.writeString(Path.of("RESULTS.md"), results.toString());
        System.out.println("\nResults written to RESULTS.md");
    }

    private static int countTokens(HttpClient client, String apiKey, String text) throws Exception {
        String body = """
                {"model": "%s", "messages": [{"role": "user", "content": "%s"}]}"""
                .formatted(MODEL, escapeJson(text));

        HttpRequest request = HttpRequest.newBuilder(URI.create(API_URL))
                .header("x-api-key", apiKey)
                .header("anthropic-version", "2023-06-01")
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IllegalStateException("API error " + response.statusCode() + ": " + response.body());
        }

        Matcher matcher = Pattern.compile("\"input_tokens\"\\s*:\\s*(\\d+)").matcher(response.body());
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
