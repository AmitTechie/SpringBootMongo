package com.javatechie.service;

import com.javatechie.model.Event;
import com.javatechie.model.EventQueryFilter;
import com.javatechie.model.Task;
import com.javatechie.repository.EventRepository;
import com.javatechie.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Create, Read, Update, Delete


    public Event addEvent(Event event){
        event.setEvent_id(UUID.randomUUID().toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        event.setEvent_timestamp(simpleDateFormat.format(System.currentTimeMillis()));
        System.out.println(event.getEvent_timestamp());
        return eventRepository.save(event);
    }

    public List<Event> findAllEvent(){
        List<Event> all = eventRepository.findAll();
        System.out.println("FOUND:::::: "+all.size());
        return all;
    }

    public List<Event> getEventsByFilter(EventQueryFilter eventQueryFilter){
        List<Event> res = new ArrayList<>();
        //List<Event> events = eventRepository.findByTenant(event.getTenant());
        List<Event> events = eventRepository.findByFilter(eventQueryFilter.getTenant());
        System.out.println(eventQueryFilter.toString());
        if(events != null) {

            for (Event e : events) {
                // filter by user_id
                if(eventQueryFilter.getUser_id().isEmpty() || !e.getUser_id().equals(eventQueryFilter.getUser_id())){
                    System.out.println("user_id didn't matched!"+e.getUser_id()+" - "+eventQueryFilter.getUser_id());
                    continue;
                }

                // filter by category
                if(eventQueryFilter.getCategory().isEmpty() || e.getCategory().equals(eventQueryFilter.getCategory())){
                    System.out.println("category didn't matched!"+e.getCategory()+" - "+eventQueryFilter.getCategory());
                    continue;
                }

                // filter by domain
                if(eventQueryFilter.getUrl_domain().isEmpty() || !e.getUrl_domain().equals(eventQueryFilter.getUrl_domain())){
                    System.out.println("domain didn't matched!"+e.getUrl_domain()+" - "+eventQueryFilter.getUrl_domain());
                    continue;
                }

                // filter by date
                if(!eventQueryFilter.getFrom_date().isEmpty() && !eventQueryFilter.getTo_date().isEmpty()){
                    System.out.println("date filter entered!!");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date start_date = simpleDateFormat.parse(eventQueryFilter.getFrom_date());
                        Date end_date = simpleDateFormat.parse(eventQueryFilter.getTo_date());

                        Date event_date = simpleDateFormat.parse(e.getEvent_timestamp());

                        if(event_date.before(start_date) || event_date.after(end_date)){
                            System.out.println("date didn't matched!");
                            continue;
                        }
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                res.add(e);
            }
        }
        return res;
    }
}
