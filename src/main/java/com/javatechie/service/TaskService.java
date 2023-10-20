package com.javatechie.service;

import com.javatechie.model.Task;
import com.javatechie.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Create, Read, Update, Delete


    public Task addTask(Task task){
        task.setTaskId(UUID.randomUUID().toString());
        return taskRepository.save(task);
    }

    public List<Task> findAllTask(){
        List<Task> all = taskRepository.findAll();
        System.out.println("FOUND:::::: "+all.size());
        return all;
    }

    public Task getTaskByTaskId(String taskId){
        if (taskRepository.findById(taskId).isPresent()){
            return taskRepository.findById(taskId).get();
        }
        return null;
    }

    public  List<Task> getTaskBySeverity(int severity){
        return taskRepository.findBySeverity(severity);
    }

    public  List<Task> getTaskByAssignee(String assignee){
        System.out.println(assignee);
        List<Task> tasksByAssignee = taskRepository.getTasksByAssignee(assignee);
        System.out.println(tasksByAssignee.size());
        return tasksByAssignee;
    }

    public Task updateTask(Task taskRequest){

        Task existingTask = taskRepository.findById(taskRequest.getTaskId()).get();
        existingTask.setDescription(taskRequest.getDescription());
        existingTask.setAssignee(taskRequest.getAssignee());
        existingTask.setStoryPoint(taskRequest.getStoryPoint());
        existingTask.setSeverity(taskRequest.getSeverity());
        return taskRepository.save(existingTask);
    }

    public String deleteTask(String taskId){
        taskRepository.deleteById(taskId);
        return taskId+"  deleted!";
    }

}
