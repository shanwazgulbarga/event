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

    // Automatically set by SentimentService when a comment is provided
    // Values: "POSITIVE", "NEGATIVE", "NEUTRAL"
    private String sentiment;

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

    public String getSentiment(){ return sentiment; }
    public void setSentiment(String sentiment){ this.sentiment = sentiment; }
}