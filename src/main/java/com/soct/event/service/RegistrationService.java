package com.soct.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.soct.event.dto.EventWeatherDTO;
import com.soct.event.model.Event;
import com.soct.event.model.Registration;
import com.soct.event.model.ActivityLog;
import com.soct.event.model.User;
import com.soct.event.repository.ActivityLogRepository;
import com.soct.event.repository.EventRepository;
import com.soct.event.repository.RegistrationRepository;
import com.soct.event.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private ActivityLogRepository activityLogRepository;
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WeatherService weatherService;


    public Registration registerForEvent(Registration registration){

        Event event = eventRepository.findById(registration.getEventId())
                        .orElseThrow(() -> new RuntimeException("Event not found"));

        
         //  Get user
    User user = userRepository.findByStudentId(registration.getStudentId());

    if(user == null){
        throw new RuntimeException("User not found");
    }

    //  Block admin booking
    if("ADMIN".equalsIgnoreCase(user.getRole())){
        throw new RuntimeException("Admin users cannot book events");
    }
        
        
        
        // 🔹 Prevent duplicate booking
        if(registrationRepository.existsByEventIdAndStudentId(
                registration.getEventId(),
                registration.getStudentId())){

            throw new RuntimeException("You have already booked this event");
        }

        //  Check event capacity
        if(event.getRegisteredParticipants() >= event.getMaxParticipants()){
            throw new RuntimeException("Event is full");
        }

        //  Increase participants count
        event.setRegisteredParticipants(
                event.getRegisteredParticipants() + 1
        );

        eventRepository.save(event);

        // 🔹 Save registration
        Registration savedRegistration = registrationRepository.save(registration);

        // 🔹 Create activity log
        ActivityLog log = new ActivityLog();
        log.setStudentId(registration.getStudentId());
        log.setAction("Registered for event " + registration.getEventId());
        log.setTimestamp(LocalDateTime.now().toString());

        activityLogRepository.save(log);

        return savedRegistration;
    }
    // NEW METHOD to get the events by studentID
    public List<Registration> getRegistrationsByStudent(String studentId){

        return registrationRepository.findByStudentId(studentId);
    }
   
    public List<EventWeatherDTO> getRegisteredEventsWithWeather(String studentId){

    List<Registration> registrations =
        registrationRepository.findByStudentId(studentId);

    List<EventWeatherDTO> result = new ArrayList<>();

    for(Registration reg : registrations){

        Event event = eventRepository.findById(reg.getEventId()).orElse(null);

        if(event != null){

            EventWeatherDTO dto = new EventWeatherDTO();
           
            dto.setTitle(event.getTitle());
            dto.setEventId(event.getId());
            dto.setLocation(event.getLocation());
            dto.setDate(event.getDate());

            String weather = weatherService.getWeather(event.getLocation());

            dto.setWeather(weather);

            result.add(dto);
        }
    }

    return result;
}

    
    
    
    
}
