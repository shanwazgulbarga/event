/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.service;

import com.soct.event.dto.ExternalEventDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkiddleService {

    @Value("${skiddle.api.key}")
    private String apiKey;

    @Value("${skiddle.api.url}")
    private String apiUrl;

    public List<ExternalEventDTO> getEvents(String location){

        try {
            RestTemplate restTemplate = new RestTemplate();

            String url = apiUrl +
                    "?api_key=" + apiKey +
                    "&keyword=" + location;

            String response = restTemplate.getForObject(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            List<ExternalEventDTO> events = new ArrayList<>();

            JsonNode results = root.path("results");

            for (JsonNode node : results) {

                ExternalEventDTO dto = new ExternalEventDTO();

                dto.setTitle(node.path("eventname").asText());
                dto.setVenue(node.path("venue").path("name").asText());
                dto.setDate(node.path("date").asText());
                dto.setLink(node.path("link").asText());

                events.add(dto);
            }

            return events;

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch external events: " + e.getMessage());
        }
    }
}
