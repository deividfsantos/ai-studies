import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A character-level bigram model: the simplest possible "predict the next token" model.
 *
 * Training = counting which character follows which.
 * Generation = repeatedly sampling the next character from those counts,
 * with a temperature that controls how random the choice is.
 *
 * Run: java BigramModel.java [path/to/training-text.txt]
 */
public class BigramModel {

    private final Map<Character, Map<Character, Integer>> counts = new HashMap<>();
    private final Random random = new Random(42);

    public static void main(String[] args) throws Exception {
        Path trainingFile = Path.of(args.length > 0 ? args[0] : "../../README.md");
        // Keep only letters and basic punctuation so markdown symbols do not dominate.
        String text = Files.readString(trainingFile).toLowerCase()
                .replaceAll("[^a-z.,' ]+", " ")
                .replaceAll(" +", " ");

        BigramModel model = new BigramModel();
        model.train(text);

        StringBuilder results = new StringBuilder();
        results.append("# Bigram model results\n\n");
        results.append("Trained on `").append(trainingFile.getFileName()).append("` (")
                .append(text.length()).append(" characters, ")
                .append(model.counts.size()).append(" distinct characters).\n\n");

        results.append("## Most likely next character\n\n");
        results.append("| After | Most likely next | Probability |\n");
        results.append("| --- | --- | --- |\n");
        for (char c : new char[] {'q', 't', ' ', 'j'}) {
            Map<Character, Integer> next = model.counts.get(c);
            if (next == null) continue;
            int total = next.values().stream().mapToInt(Integer::intValue).sum();
            Map.Entry<Character, Integer> best = next.entrySet().stream()
                    .max(Map.Entry.comparingByValue()).orElseThrow();
            results.append("| `").append(show(c)).append("` | `").append(show(best.getKey()))
                    .append("` | ").append(String.format("%.0f%%", 100.0 * best.getValue() / total))
                    .append(" |\n");
        }

        results.append("\n## Generated text by temperature\n\n");
        for (double temperature : new double[] {0.2, 1.0, 2.0}) {
            String sample = model.generate('t', 300, temperature);
            System.out.printf("--- temperature %.1f ---%n%s%n%n", temperature, sample);
            results.append("### Temperature ").append(temperature).append("\n\n```\n")
                    .append(sample).append("\n```\n\n");
        }

        Files.writeString(Path.of("RESULTS.md"), results.toString());
        System.out.println("Results written to RESULTS.md");
    }

    void train(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            char current = text.charAt(i);
            char next = text.charAt(i + 1);
            counts.computeIfAbsent(current, k -> new HashMap<>()).merge(next, 1, Integer::sum);
        }
    }

    String generate(char start, int length, double temperature) {
        StringBuilder output = new StringBuilder().append(start);
        char current = start;
        for (int i = 0; i < length; i++) {
            Map<Character, Integer> next = counts.get(current);
            if (next == null) break;
            current = sample(next, temperature);
            output.append(current);
        }
        return output.toString();
    }

    /**
     * Picks the next character. Each count is raised to the power 1/temperature:
     * low temperature exaggerates the most common choice, high temperature flattens
     * the distribution so rare characters show up more often.
     */
    private char sample(Map<Character, Integer> next, double temperature) {
        Map<Character, Double> weights = new HashMap<>();
        double total = 0;
        for (Map.Entry<Character, Integer> entry : next.entrySet()) {
            double weight = Math.pow(entry.getValue(), 1.0 / temperature);
            weights.put(entry.getKey(), weight);
            total += weight;
        }

        double target = random.nextDouble() * total;
        for (Map.Entry<Character, Double> entry : weights.entrySet()) {
            target -= entry.getValue();
            if (target <= 0) return entry.getKey();
        }
        return next.keySet().iterator().next();
    }

    private static String show(char c) {
        return c == ' ' ? "space" : c == '\n' ? "\\n" : String.valueOf(c);
    }
}
