/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.controller;

import com.soct.event.model.ActivityLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.soct.event.model.User;
import com.soct.event.model.Event;
import com.soct.event.repository.ActivityLogRepository;
import com.soct.event.repository.UserRepository;
import com.soct.event.repository.EventRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActivityLogRepository activityLogRepository;
    @Autowired
    private EventRepository eventRepository;

    // View all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // View all events
    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Delete event
    @DeleteMapping("/events/{id}")
public String deleteEvent(@PathVariable String id){

    eventRepository.deleteById(id);

    ActivityLog log = new ActivityLog();
    log.setStudentId("ADMIN001");
    log.setAction("Deleted event with ID " + id);
    log.setTimestamp(java.time.LocalDateTime.now().toString());

    activityLogRepository.save(log);

    return "Event deleted successfully";
}
    
    @GetMapping("/logs")
public List<ActivityLog> getAllLogs(){

    return activityLogRepository.findAll();
}
    
    
}
