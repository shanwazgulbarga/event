/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.dto;

import java.util.List;
/**
 *
 * @author shanw
 */
public class EventDTO {

    private String id;
    private String publisherId;
    private String title;
    private String type;
    private String date;
    private String location;
    private int cost;
    private int maxParticipants;
    private int registeredParticipants;

    //  External / Computed Fields
    private List<ImageDTO> images;
    private String weather;
    private double averageRating;

    public EventDTO(){}

    //  Getters & Setters

    public String getId(){ return id; }
    public void setId(String id){ this.id = id; }

    public String getPublisherId(){ return publisherId; }
    public void setPublisherId(String publisherId){ this.publisherId = publisherId; }

    public String getTitle(){ return title; }
    public void setTitle(String title){ this.title = title; }

    public String getType(){ return type; }
    public void setType(String type){ this.type = type; }

    public String getDate(){ return date; }
    public void setDate(String date){ this.date = date; }

    public String getLocation(){ return location; }
    public void setLocation(String location){ this.location = location; }

    public int getCost(){ return cost; }
    public void setCost(int cost){ this.cost = cost; }

    public int getMaxParticipants(){ return maxParticipants; }
    public void setMaxParticipants(int maxParticipants){ this.maxParticipants = maxParticipants; }

    public int getRegisteredParticipants(){ return registeredParticipants; }
    public void setRegisteredParticipants(int registeredParticipants){ this.registeredParticipants = registeredParticipants; }

    public List<ImageDTO> getImages(){ return images; }
    public void setImages(List<ImageDTO> images){ this.images = images; }


    public String getWeather(){ return weather; }
    public void setWeather(String weather){ this.weather = weather; }

    public double getAverageRating(){ return averageRating; }
    public void setAverageRating(double averageRating){ this.averageRating = averageRating; }
}