/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 *
 * @author shanw
 */

@Service
public class SentimentService {

    @Value("${sentiment.api.key}")
    private String apiKey;

    @Value("${sentiment.api.url}")
    private String apiUrl;

    public String analyzeSentiment(String text){

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            String body = "{\"inputs\": \"" + text.replace("\"", "\\\"") + "\"}";

            HttpEntity<String> request = new HttpEntity<>(body, headers);

            String response = restTemplate.postForObject(apiUrl, request, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            JsonNode results = root.get(0);
            if (results == null || !results.isArray()) return "NEUTRAL";

            String label = "NEUTRAL";
            double bestScore = -1;

            for (JsonNode item : results) {
                double score = item.path("score").asDouble();
                if (score > bestScore) {
                    bestScore = score;
                    label = item.path("label").asText().toUpperCase();
                }
            }

            return label;

        } catch (Exception e){
            return "NEUTRAL";
        }
    }
}