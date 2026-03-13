/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.soct.event.model.User;
import com.soct.event.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user){

        User existing = userRepository.findByStudentId(user.getStudentId());

        if(existing != null){
            return "Student already registered";
        }

        user.setRole("STUDENT");

        userRepository.save(user);

        return "Registration successful";
    }
}
