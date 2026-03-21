/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="reviews")
public class Review {

    @Id
    private String id;

    private String eventId;
    private String studentId;
    private String comment;
    private int rating; // 1 to 5

    private String timestamp;

    public Review(){}

    public String getId(){ return id; }
    public void setId(String id){ this.id = id; }

    public String getEventId(){ return eventId; }
    public void setEventId(String eventId){ this.eventId = eventId; }

    public String getStudentId(){ return studentId; }
    public void setStudentId(String studentId){ this.studentId = studentId; }

    public String getComment(){ return comment; }
    public void setComment(String comment){ this.comment = comment; }

    public int getRating(){ return rating; }
    public void setRating(int rating){ this.rating = rating; }

    public String getTimestamp(){ return timestamp; }
    public void setTimestamp(String timestamp){ this.timestamp = timestamp; }
}
