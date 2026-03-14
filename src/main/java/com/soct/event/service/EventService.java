package com.soct.event.service;

import com.soct.event.dto.EventWeatherDTO;
import com.soct.event.model.ActivityLog;
import com.soct.event.model.Event;
import com.soct.event.repository.ActivityLogRepository;
import com.soct.event.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private WeatherService weatherService;

    
    @Autowired
    private ActivityLogRepository activityLogRepository;

    public Event createEvent(Event event){

        event.setRegisteredParticipants(0);

        Event savedEvent = eventRepository.save(event);

        // Create activity log
        ActivityLog log = new ActivityLog();
        log.setStudentId(event.getPublisherId());
        log.setAction("Created event: " + event.getTitle());
        log.setTimestamp(LocalDateTime.now().toString());

        activityLogRepository.save(log);

        return savedEvent;
    }

    public List<Event> getAllEvents(){
        return eventRepository.findAll();
    }

    public List<Event> searchByType(String type){
        return eventRepository.findByType(type);
    }
    
    public List<Event> searchByLocation(String location){
    return eventRepository.findByLocation(location);
}

public List<Event> searchByDate(String date){
    return eventRepository.findByDate(date);
}

public List<Event> searchByTypeAndLocation(String type, String location){
    return eventRepository.findByTypeAndLocation(type, location);
}
    
    public List<Event> getEventsByPublisher(String publisherId){

    return eventRepository.findByPublisherId(publisherId);
}
  public List<EventWeatherDTO> getAllEventsWithWeather(){

    List<Event> events = eventRepository.findAll();

    List<EventWeatherDTO> result = new ArrayList<>();

    for(Event event : events){

        EventWeatherDTO dto = new EventWeatherDTO();

        dto.setTitle(event.getTitle());
        dto.setLocation(event.getLocation());
        dto.setDate(event.getDate());

        String weather = weatherService.getWeather(event.getLocation());

        dto.setWeather(weather);

        result.add(dto);
    }

    return result;
}

    
    
    
    
}








