package org.example.serviceImpl;


import org.example.dao.ToDoListDao;
import org.example.dao.UserDao;
import org.example.POJO.ToDoList;
import org.example.POJO.User;
import org.example.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoListServiceImpl implements ToDoListService {

    @Autowired
    private ToDoListDao toDoListDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<ToDoList> getAllTasksForUser(Integer userId) {
        return toDoListDao.findByUserId(userId);
    }

    @Override
    public ToDoList getTaskById(Long taskId) {
        return toDoListDao.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Override
    public ToDoList createTask(ToDoList task, Integer userId) {
        User user = userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);
        return toDoListDao.save(task);
    }

    @Override
    public ToDoList updateTask(Long taskId, ToDoList updatedTask) {
        ToDoList existingTask = getTaskById(taskId);
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.isCompleted());
        return toDoListDao.save(existingTask);
    }

    @Override
    public void deleteTask(Long taskId) {
        toDoListDao.deleteById(taskId);
    }
}
