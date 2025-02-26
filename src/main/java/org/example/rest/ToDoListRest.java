package org.example.rest;


import org.example.POJO.ToDoList;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ToDoListRest {
    ResponseEntity<List<ToDoList>> getTasksForUser(Integer userId);
    ResponseEntity<ToDoList> getTask(Long taskId);
    ResponseEntity<ToDoList> createTask(ToDoList task, Integer userId);
    ResponseEntity<ToDoList> updateTask(Long taskId, ToDoList updatedTask);
    ResponseEntity<Void> deleteTask(Long taskId);
}
