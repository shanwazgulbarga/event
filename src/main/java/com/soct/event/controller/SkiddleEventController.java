/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.controller;

import com.soct.event.dto.ExternalEventDTO;
import com.soct.event.service.SkiddleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author shanw
 */

@RestController
@RequestMapping("/api/events/skiddle")
public class SkiddleEventController {

    @Autowired
    private SkiddleService skiddleService;

    @GetMapping
    public List<ExternalEventDTO> getExternalEvents(
            @RequestParam String location){

        return skiddleService.getEvents(location);
    }
}
