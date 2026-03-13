/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.controller;

import com.soct.event.model.ActivityLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.soct.event.model.User;
import com.soct.event.repository.ActivityLogRepository;
import com.soct.event.repository.UserRepository;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
   
    @Autowired
    private ActivityLogRepository activityLogRepository;
    @PostMapping("/login")
    public String login(@RequestBody User user){

        User dbUser = userRepository.findByStudentId(user.getStudentId());

        if(dbUser == null){
            return "User not found";
        }

        if(!dbUser.getPassword().equals(user.getPassword())){
            return "Invalid password";
        }
        
        // Activity log for login
        ActivityLog log = new ActivityLog();
        log.setStudentId(dbUser.getStudentId());
        log.setAction("User logged in");
        log.setTimestamp(LocalDateTime.now().toString());

        activityLogRepository.save(log);

        return "Login successful : " + dbUser.getRole();
    }
}
