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

@Service
public class PixabayService {

    @Value("${pixabay.api.key}")
    private String apiKey;

    @Value("${pixabay.image.url}")
    private String imageUrl;

    @Value("${pixabay.video.url}")
    private String videoUrl;

    // 📸 GET IMAGES
    public List<ImageDTO> getImages(String query){

        try {
            RestTemplate restTemplate = new RestTemplate();

            String url = imageUrl +
                    "?key=" + apiKey +
                    "&q=" + java.net.URLEncoder.encode(query, "UTF-8") +
                    "&image_type=photo&per_page=5";

            String response = restTemplate.getForObject(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            List<ImageDTO> images = new ArrayList<>();

            for(JsonNode node : root.path("hits")){
                ImageDTO dto = new ImageDTO();
                dto.setImageUrl(node.path("webformatURL").asText());
                images.add(dto);
            }

            return images;

        } catch (Exception e){
            throw new RuntimeException("Failed to fetch images from Pixabay: " + e.getMessage());
        }
    }
}