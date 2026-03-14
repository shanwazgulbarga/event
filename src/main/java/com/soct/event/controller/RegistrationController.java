package com.soct.event.controller;

import com.soct.event.dto.EventWeatherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.soct.event.model.Registration;
import com.soct.event.service.RegistrationService;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    // Book an event
    @PostMapping
    public Registration registerEvent(@RequestBody Registration registration){

        return registrationService.registerForEvent(registration);
    }

    // Get bookings for a student
    @GetMapping("/student/{studentId}")
    public List<Registration> getBookingsByStudent(@PathVariable String studentId){

        return registrationService.getRegistrationsByStudent(studentId);
    }
    
    @GetMapping("/student/{studentId}/weather")
    public List<EventWeatherDTO> getBookingsWithWeather(
        @PathVariable String studentId){

    return registrationService.getRegisteredEventsWithWeather(studentId);
}





}


