/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soct.event.dto.ImageDTO;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Service
public class PixabayService {

    @Value("${unsplash.api.key}")
    private String apiKey;

    @Value("${unsplash.image.url}")
    private String imageUrl;

    public List<ImageDTO> getImages(String query) {
        try {
            if (apiKey == null || apiKey.isBlank()) {
                throw new RuntimeException("Unsplash API key is not configured!");
            }

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Client-ID " + apiKey);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            String url = imageUrl +
                    "/search/photos?query=" + java.net.URLEncoder.encode(query, "UTF-8") +
                    "&per_page=5";

            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, String.class
            );

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            List<ImageDTO> images = new ArrayList<>();
            for (JsonNode node : root.path("results")) {
                ImageDTO dto = new ImageDTO();
                dto.setImageUrl(node.path("urls").path("regular").asText());
                images.add(dto);
            }
            return images;

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch images from Unsplash: " + e.getMessage());
        }
    }
}