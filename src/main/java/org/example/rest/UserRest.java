package org.example.rest;

import org.example.DTO.UpdateStatusRequest;
import org.example.POJO.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface UserRest {
    ResponseEntity<String> signUp(Map<String, String> requestMap);
    ResponseEntity<List<User>> getAllUsers();
    @DeleteMapping("/users/{id}")
    ResponseEntity<Void> deleteUserById(@PathVariable Integer id);
    @PutMapping("/update-status/{id}")
    ResponseEntity<Map<String, String>> updateUserStatus(
            @PathVariable Integer id,
            @RequestBody UpdateStatusRequest request
    );
}