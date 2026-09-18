package com.soct.event.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SentimentService {

    @Value("${sentiment.api.key}")
    private String apiKey;

    @Value("${sentiment.api.url}")
    private String apiUrl;

    public String analyzeSentiment(String comment) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON)); // Fix: router rejects text/plain
            headers.setBearerAuth(apiKey);

            // Safely escape the comment for JSON
            ObjectMapper mapper = new ObjectMapper();
            String escapedComment = mapper.writeValueAsString(comment); // includes surrounding quotes
            String body = "{\"inputs\": " + escapedComment + "}";

            HttpEntity<String> request = new HttpEntity<>(body, headers);

            String response = restTemplate.postForObject(apiUrl, request, String.class);

            JsonNode root = mapper.readTree(response);

            // HuggingFace returns [[{label, score}, ...]]
            JsonNode results = root.isArray() ? root.get(0) : null;
            if (results == null || !results.isArray()) return "NEUTRAL";

            String bestLabel = "NEUTRAL";
            double bestScore = -1;

            for (JsonNode item : results) {
                double score = item.path("score").asDouble();
                if (score > bestScore) {
                    bestScore = score;
                    bestLabel = item.path("label").asText();
                }
            }

            return mapLabel(bestLabel);

        } catch (Exception e) {
            e.printStackTrace(); // helpful during development
            return "NEUTRAL";
        }
    }

    /**
     * Maps model-specific labels to readable sentiment.
     *
     * twitter-roberta-base-sentiment-latest uses:
     *   LABEL_0 = Negative
     *   LABEL_1 = Neutral
     *   LABEL_2 = Positive
     *
     * Some models return "positive"/"negative"/"neutral" directly.
     */
    private String mapLabel(String label) {
        return switch (label.toUpperCase()) {
            case "LABEL_0"   -> "NEGATIVE";
            case "LABEL_1"   -> "NEUTRAL";
            case "LABEL_2"   -> "POSITIVE";
            case "POSITIVE"  -> "POSITIVE";
            case "NEGATIVE"  -> "NEGATIVE";
            case "NEUTRAL"   -> "NEUTRAL";
            default          -> "NEUTRAL";
        };
    }
}