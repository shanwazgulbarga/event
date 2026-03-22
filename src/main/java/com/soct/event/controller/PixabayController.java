/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.controller;

import com.soct.event.dto.ImageDTO;

import com.soct.event.service.PixabayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pixabay")
public class PixabayController {

    @Autowired
    private PixabayService pixabayService;

    @GetMapping("/images")
    public List<ImageDTO> getImages(@RequestParam String query){
        return pixabayService.getImages(query);
    }

  
}
