package com.soct.event.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="events")
public class Event {

    @Id
    private String id;

    private String publisherId;
    private String title;
    private String type;
    private String date;
    private String location;
    private int cost;
    private int maxParticipants;
    private int registeredParticipants;

    public Event(){
        this.registeredParticipants = 0;
    }

    public String getId(){return id;}
    public void setId(String id){this.id=id;}

    public String getPublisherId(){return publisherId;}
    public void setPublisherId(String publisherId){this.publisherId=publisherId;}

    public String getTitle(){return title;}
    public void setTitle(String title){this.title=title;}

    public String getType(){return type;}
    public void setType(String type){this.type=type;}

    public String getDate(){return date;}
    public void setDate(String date){this.date=date;}

    public String getLocation(){return location;}
    public void setLocation(String location){this.location=location;}

    public int getCost(){return cost;}
    public void setCost(int cost){this.cost=cost;}

    public int getMaxParticipants(){return maxParticipants;}
    public void setMaxParticipants(int maxParticipants){this.maxParticipants=maxParticipants;}

    public int getRegisteredParticipants(){return registeredParticipants;}
    public void setRegisteredParticipants(int registeredParticipants){this.registeredParticipants=registeredParticipants;}
}