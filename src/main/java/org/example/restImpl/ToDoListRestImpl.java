package org.example.restImpl;


import org.example.rest.ToDoListRest;
import org.example.POJO.ToDoList;
import org.example.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todolist")
public class ToDoListRestImpl implements ToDoListRest {

    @Autowired
    private ToDoListService toDoListService;

    @Override
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ToDoList>> getTasksForUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(toDoListService.getAllTasksForUser(userId));
    }

    @Override
    @GetMapping("/{taskId}")
    public ResponseEntity<ToDoList> getTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(toDoListService.getTaskById(taskId));
    }

    @Override
    @PostMapping("/user/{userId}")
    public ResponseEntity<ToDoList> createTask(@RequestBody ToDoList task, @PathVariable Integer userId) {
        return ResponseEntity.ok(toDoListService.createTask(task, userId));
    }

    @Override
    @PutMapping("/{taskId}")
    public ResponseEntity<ToDoList> updateTask(@PathVariable Long taskId, @RequestBody ToDoList updatedTask) {
        return ResponseEntity.ok(toDoListService.updateTask(taskId, updatedTask));
    }

    @Override
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        toDoListService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
