/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final String API_KEY = "9a86738447d73424a192439f7095b537";

    public String getWeather(String location){

        RestTemplate restTemplate = new RestTemplate();

        String url =
        "https://api.openweathermap.org/data/2.5/weather?q="
        + location +
        "&appid=" + API_KEY +
        "&units=metric";

        return restTemplate.getForObject(url,String.class);
    }
}

