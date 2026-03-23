/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String API_KEY;

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