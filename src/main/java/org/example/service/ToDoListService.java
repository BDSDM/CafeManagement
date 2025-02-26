package org.example.service;

import org.example.POJO.ToDoList;

import java.util.List;

public interface ToDoListService {
    List<ToDoList> getAllTasksForUser(Integer userId);
    ToDoList getTaskById(Long taskId);
    ToDoList createTask(ToDoList task, Integer userId);
    ToDoList updateTask(Long taskId, ToDoList updatedTask);
    void deleteTask(Long taskId);
}
