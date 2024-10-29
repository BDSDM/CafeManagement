package org.example.service;

import org.example.POJO.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    ResponseEntity<String> signUp(Map<String, String> requestMap);
    ResponseEntity<String> login(Map<String, String> requestMap); // Ajout de la méthode login

    ResponseEntity<List<User>> getAllUsers();
    void deleteUserById(Integer id);
    boolean updateUserStatus(Integer id, String status);
}
