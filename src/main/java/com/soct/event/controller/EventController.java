/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.controller;

import com.soct.event.dto.EventWeatherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.soct.event.model.Event;
import com.soct.event.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public Event createEvent(@RequestBody Event event){

        return eventService.createEvent(event);
    }

    @GetMapping
    public List<Event> getAllEvents(){

        return eventService.getAllEvents();
    }

    @GetMapping("/search/type")
public List<Event> searchByType(@RequestParam String type){
    return eventService.searchByType(type);
}

@GetMapping("/search/location")
public List<Event> searchByLocation(@RequestParam String location){
    return eventService.searchByLocation(location);
}

@GetMapping("/search/date")
public List<Event> searchByDate(@RequestParam String date){
    return eventService.searchByDate(date);
}

@GetMapping("/search/type-location")
public List<Event> searchByTypeAndLocation(
        @RequestParam String type,
        @RequestParam String location){
        
    return eventService.searchByTypeAndLocation(type, location);
}
    
    @GetMapping("/publisher/{publisherId}")
    public List<Event> getEventsByCreator(@PathVariable String publisherId){    
        
    return eventService.getEventsByPublisher(publisherId);
}
    @GetMapping("/weather")
public List<EventWeatherDTO> getEventsWithWeather(){

    return eventService.getAllEventsWithWeather();
}

}
