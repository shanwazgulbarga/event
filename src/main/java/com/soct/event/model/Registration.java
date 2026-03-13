/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="registrations")
public class Registration {

    @Id
    private String id;

    private String eventId;
    private String studentId;

    public Registration(){}

    public String getId(){return id;}
    public void setId(String id){this.id=id;}

    public String getEventId(){return eventId;}
    public void setEventId(String eventId){this.eventId=eventId;}

    public String getStudentId(){return studentId;}
    public void setStudentId(String studentId){this.studentId=studentId;}
}