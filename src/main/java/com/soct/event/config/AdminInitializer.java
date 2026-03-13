package com.soct.event.config;

import com.soct.event.model.User;
import com.soct.event.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {

        System.out.println("Checking admin user...");

        User admin = userRepository.findByStudentId("ADMIN");

        if(admin == null){

            User newAdmin = new User();

            newAdmin.setStudentId("ADMIN");
            newAdmin.setName("System Admin");
            newAdmin.setEmail("admin@gmail.com");
            newAdmin.setPassword("admin");
            newAdmin.setRole("ADMIN");

            userRepository.save(newAdmin);

            System.out.println("Default admin created!");
        } else {
            System.out.println("Admin already exists.");
        }
    }
}