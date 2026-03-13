package com.soct.event.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="activity_logs")
public class ActivityLog {

    @Id
    private String id;

    private String studentId;
    private String action;
    private String timestamp;

    public ActivityLog(){}

    public String getId(){return id;}
    public void setId(String id){this.id=id;}

    public String getStudentId(){return studentId;}
    public void setStudentId(String studentId){this.studentId=studentId;}

    public String getAction(){return action;}
    public void setAction(String action){this.action=action;}

    public String getTimestamp(){return timestamp;}
    public void setTimestamp(String timestamp){this.timestamp=timestamp;}
}