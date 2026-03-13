/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.soct.event.model.Event;
import java.util.List;

public interface EventRepository extends MongoRepository<Event,String>{

    List<Event> findByType(String type);

List<Event> findByLocation(String location);

List<Event> findByDate(String date);

List<Event> findByTypeAndLocation(String type, String location);

List<Event> findByTypeAndDate(String type, String date);
    
    List<Event> findByPublisherId(String publisherId);

}