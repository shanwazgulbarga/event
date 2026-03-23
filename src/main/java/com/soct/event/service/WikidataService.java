/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soct.event.dto.WikidataLocationDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WikidataService {

    private final String SEARCH_URL = "https://www.wikidata.org/w/api.php";
    private final String ENTITY_URL = "https://www.wikidata.org/wiki/Special:EntityData/";

    public WikidataLocationDTO enrichLocation(String cityName){

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "EventApp/1.0 (academic project)");

            // Step 1 — search for the city QID
            String searchUrl = SEARCH_URL +
                    "?action=wbsearchentities" +
                    "&search=" + cityName +
                    "&language=en" +
                    "&type=item" +
                    "&format=json" +
                    "&limit=1";

            HttpEntity<Void> request = new HttpEntity<>(headers);

            org.springframework.http.ResponseEntity<String> searchResponse =
                restTemplate.exchange(searchUrl, org.springframework.http.HttpMethod.GET, request, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode searchRoot = mapper.readTree(searchResponse.getBody());

            JsonNode searchResults = searchRoot.path("search");

            if(searchResults.isEmpty()){
                return fallback(cityName);
            }

            String qid = searchResults.get(0).path("id").asText();
            String wikidataUri = "https://www.wikidata.org/entity/" + qid;

            // Step 2 — fetch entity data for coordinates and country
            String entityUrl = ENTITY_URL + qid + ".json";

            org.springframework.http.ResponseEntity<String> entityResponse =
                restTemplate.exchange(entityUrl, org.springframework.http.HttpMethod.GET, request, String.class);

            JsonNode entityRoot = mapper.readTree(entityResponse.getBody());
            JsonNode claims = entityRoot.path("entities").path(qid).path("claims");

            // P625 = coordinate location
            double latitude = 0.0;
            double longitude = 0.0;

            JsonNode coords = claims.path("P625");
            if(!coords.isMissingNode() && coords.isArray() && coords.size() > 0){
                JsonNode coordValue = coords.get(0).path("mainsnak").path("datavalue").path("value");
                latitude = coordValue.path("latitude").asDouble();
                longitude = coordValue.path("longitude").asDouble();
            }

            // P17 = country
            String countryName = "";
            String countryQid = "";

            JsonNode countryNode = claims.path("P17");
            if(!countryNode.isMissingNode() && countryNode.isArray() && countryNode.size() > 0){
                countryQid = countryNode.get(0).path("mainsnak").path("datavalue").path("value").path("id").asText();
                countryName = resolveLabel(countryQid, headers, restTemplate, mapper);
            }

            WikidataLocationDTO dto = new WikidataLocationDTO();
            dto.setCityName(cityName);
            dto.setWikidataUri(wikidataUri);
            dto.setLatitude(latitude);
            dto.setLongitude(longitude);
            dto.setCountryName(countryName);
            dto.setCountryWikidataUri(countryQid.isEmpty() ? "" : "https://www.wikidata.org/entity/" + countryQid);

            return dto;

        } catch (Exception e){
            return fallback(cityName);
        }
    }

    private String resolveLabel(String qid, HttpHeaders headers, RestTemplate restTemplate, ObjectMapper mapper){

        try {
            String url = SEARCH_URL +
                    "?action=wbgetentities" +
                    "&ids=" + qid +
                    "&props=labels" +
                    "&languages=en" +
                    "&format=json";

            HttpEntity<Void> request = new HttpEntity<>(headers);

            org.springframework.http.ResponseEntity<String> response =
                restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, request, String.class);

            JsonNode root = mapper.readTree(response.getBody());

            return root.path("entities").path(qid).path("labels").path("en").path("value").asText();

        } catch (Exception e){
            return "";
        }
    }

    private WikidataLocationDTO fallback(String cityName){
        WikidataLocationDTO dto = new WikidataLocationDTO();
        dto.setCityName(cityName);
        dto.setWikidataUri("");
        dto.setLatitude(0.0);
        dto.setLongitude(0.0);
        dto.setCountryName("");
        dto.setCountryWikidataUri("");
        return dto;
    }
}
