package com.javatechie.service;

import com.javatechie.model.Event;
import com.javatechie.model.Task;
import com.javatechie.repository.EventRepository;
import com.javatechie.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Create, Read, Update, Delete


    public Event addEvent(Event event){
        event.setEvent_id(UUID.randomUUID().toString());
        return eventRepository.save(event);
    }

    public List<Event> findAllEvent(){
        List<Event> all = eventRepository.findAll();
        System.out.println("FOUND:::::: "+all.size());
        return all;
    }

    public List<Event> getEventsByUser(String userId){
        return eventRepository.findAll();
    }

//    public  List<Task> getTaskBySeverity(int severity){
//        return taskRepository.findBySeverity(severity);
//    }
//
//    public  List<Task> getTaskByAssignee(String assignee){
//        System.out.println(assignee);
//        List<Task> tasksByAssignee = taskRepository.getTasksByAssignee(assignee);
//        System.out.println(tasksByAssignee.size());
//        return tasksByAssignee;
//    }
//
//    public Task updateTask(Task taskRequest){
//
//        Task existingTask = taskRepository.findById(taskRequest.getTaskId()).get();
//        existingTask.setDescription(taskRequest.getDescription());
//        existingTask.setAssignee(taskRequest.getAssignee());
//        existingTask.setStoryPoint(taskRequest.getStoryPoint());
//        existingTask.setSeverity(taskRequest.getSeverity());
//        return taskRepository.save(existingTask);
//    }
//
//    public String deleteTask(String taskId){
//        taskRepository.deleteById(taskId);
//        return taskId+"  deleted!";
//    }

}
