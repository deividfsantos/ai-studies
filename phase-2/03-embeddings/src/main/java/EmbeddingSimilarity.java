import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Turns sentences into embedding vectors and compares them with cosine similarity.
 * Sentences with similar meaning should score close to 1, even with no words in common.
 *
 * Run: mvn compile exec:java
 */
public class EmbeddingSimilarity {

    private static final List<String> SENTENCES = List.of(
            "The server is down",
            "The API is not responding",
            "Production is having an outage",
            "The database query is too slow",
            "We should add an index to the orders table",
            "I bought bread at the bakery",
            "The coffee shop opens at seven",
            "Deploy the new version on Friday",
            "Release the update at the end of the week",
            "The cat is sleeping on the sofa"
    );

    public static void main(String[] args) throws Exception {
        EmbeddingModel model = new AllMiniLmL6V2EmbeddingModel();

        List<float[]> vectors = new ArrayList<>();
        for (String sentence : SENTENCES) {
            vectors.add(model.embed(sentence).content().vector());
        }
        System.out.println("Each sentence became a vector with " + vectors.get(0).length + " numbers.");

        StringBuilder results = new StringBuilder();
        results.append("# Embedding similarity results\n\n");
        results.append("Model: all-MiniLM-L6-v2 (").append(vectors.get(0).length).append(" dimensions)\n\n");

        results.append("## Most similar sentence for each sentence\n\n");
        results.append("| Sentence | Closest match | Similarity |\n");
        results.append("| --- | --- | --- |\n");
        for (int i = 0; i < SENTENCES.size(); i++) {
            int best = -1;
            double bestScore = -1;
            for (int j = 0; j < SENTENCES.size(); j++) {
                if (i == j) continue;
                double score = cosineSimilarity(vectors.get(i), vectors.get(j));
                if (score > bestScore) {
                    bestScore = score;
                    best = j;
                }
            }
            System.out.printf("%-45s -> %-45s %.2f%n", SENTENCES.get(i), SENTENCES.get(best), bestScore);
            results.append("| ").append(SENTENCES.get(i)).append(" | ").append(SENTENCES.get(best))
                    .append(" | ").append(String.format("%.2f", bestScore)).append(" |\n");
        }

        results.append("\n## Full similarity matrix\n\n|   |");
        for (int i = 0; i < SENTENCES.size(); i++) results.append(" ").append(i + 1).append(" |");
        results.append("\n| --- |");
        for (int i = 0; i < SENTENCES.size(); i++) results.append(" --- |");
        results.append("\n");
        for (int i = 0; i < SENTENCES.size(); i++) {
            results.append("| ").append(i + 1).append(". ").append(SENTENCES.get(i)).append(" |");
            for (int j = 0; j < SENTENCES.size(); j++) {
                results.append(" ").append(String.format("%.2f", cosineSimilarity(vectors.get(i), vectors.get(j)))).append(" |");
            }
            results.append("\n");
        }

        Files.writeString(Path.of("RESULTS.md"), results.toString());
        System.out.println("\nResults written to RESULTS.md");
    }

    /** 1 = same direction (same meaning), 0 = unrelated. */
    static double cosineSimilarity(float[] a, float[] b) {
        double dot = 0, normA = 0, normB = 0;
        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
