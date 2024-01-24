package com.javatechie.repository;

import com.javatechie.model.Event;
import com.javatechie.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {
    @Query ("{tenant: ?0}")
    List<Event> findByTenant(String tenant);

//    List<Event> findByDate(Date from_date, Date to_date);

//    List<Event> findUserId(String user_id);

//    @Query ("{assignee: ?0}")
//    List<Task> getTasksByAssignee(String assignee);
}