package com.soct.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soct.event.model.Event;
import com.soct.event.model.Registration;
import com.soct.event.model.ActivityLog;
import com.soct.event.model.User;
import com.soct.event.repository.ActivityLogRepository;
import com.soct.event.repository.EventRepository;
import com.soct.event.repository.RegistrationRepository;
import com.soct.event.repository.UserRepository;

import java.time.LocalDateTime;
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


    public Registration registerForEvent(Registration registration){

        Event event = eventRepository.findById(registration.getEventId())
                        .orElseThrow(() -> new RuntimeException("Event not found"));

        
         // 🔹 Get user
    User user = userRepository.findByStudentId(registration.getStudentId());

    if(user == null){
        throw new RuntimeException("User not found");
    }

    // 🔹 Block admin booking
    if("ADMIN".equalsIgnoreCase(user.getRole())){
        throw new RuntimeException("Admin users cannot book events");
    }
        
        
        
        // 🔹 Prevent duplicate booking
        if(registrationRepository.existsByEventIdAndStudentId(
                registration.getEventId(),
                registration.getStudentId())){

            throw new RuntimeException("You have already booked this event");
        }

        // 🔹 Check event capacity
        if(event.getRegisteredParticipants() >= event.getMaxParticipants()){
            throw new RuntimeException("Event is full");
        }

        // 🔹 Increase participants count
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
    // 🔹 NEW METHOD to get the events by studentID
    public List<Registration> getRegistrationsByStudent(String studentId){

        return registrationRepository.findByStudentId(studentId);
    }
    
}
