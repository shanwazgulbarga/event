/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.soct.event.model.User;

public interface UserRepository extends MongoRepository<User,String>{

    User findByStudentId(String studentId);
}