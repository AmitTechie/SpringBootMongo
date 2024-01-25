package com.javatechie.repository;

import com.javatechie.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {
    @Query ("{tenant: ?0}")
    List<Event> findByTenant(String tenant);

    //@Query ("{tenant: ?0, user_id: ?0, url_domain: ?0, category: ?0}")
    //List<Event> findByFilter(String tenant, String user_id, String url_domain, String category);
    //@Query(value = "{ 'status' : ?0 }", fields = "{ 'item' : 1, 'status' : 1 }")
    @Query(value = "{ tenant : ?0}")
    List<Event> findByFilter(String tenant);

    //    List<Event> findByDate(Date from_date, Date to_date);

//    List<Event> findUserId(String user_id);

//    @Query ("{assignee: ?0}")
//    List<Task> getTasksByAssignee(String assignee);
}