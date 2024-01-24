package com.javatechie.controller;


import com.javatechie.model.Event;
import com.javatechie.model.EventQueryFilter;
import com.javatechie.model.Task;
import com.javatechie.service.EventService;
import com.javatechie.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping("/webhooks/{tenant_name}/query")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Event> getEvent(@PathVariable String tenant_name, @RequestBody EventQueryFilter eventQueryFilter){
        eventQueryFilter.setTenant(tenant_name);
         return eventService.getEventsByFilter(eventQueryFilter);
    }

    @PostMapping("/webhooks/{tenant_name}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public Event sendEvent(@PathVariable String tenant_name, @RequestBody Event event){
        System.out.println("Event entered!!");
        event.setTenant(tenant_name);
        return eventService.addEvent(event);
    }
}
