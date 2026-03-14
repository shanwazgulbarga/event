package com.soct.event.dto;

public class EventWeatherDTO {

    private String eventId;
    private String title;
    private String location;
    private String date;
    private String weather;

    public EventWeatherDTO(){}

    public String getEventId(){
        return eventId;
    }

    public void setEventId(String eventId){
        this.eventId = eventId;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getWeather(){
        return weather;
    }

    public void setWeather(String weather){
        this.weather = weather;
    }
}
