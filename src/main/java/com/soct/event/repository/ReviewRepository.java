/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import com.soct.event.model.Review;

public interface ReviewRepository extends MongoRepository<Review,String>{

    List<Review> findByEventId(String eventId);

    // prevent duplicate reviews by same user
    boolean existsByEventIdAndStudentId(String eventId, String studentId);
}