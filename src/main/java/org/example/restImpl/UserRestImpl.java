package org.example.restImpl;

import org.example.DTO.UpdateStatusRequest;
import org.example.POJO.User;
import org.example.rest.UserRest;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/user")
public class UserRestImpl implements UserRest {

    @Autowired
    private UserService userService;

    @Override
    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody Map<String, String> requestMap) {
        return userService.signUp(requestMap);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> requestMap) {
        return userService.login(requestMap);
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();  // Appelle la méthode du service
    }
    @Override
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
    @Override
    @PutMapping("/update-status/{id}")
    public ResponseEntity<Map<String, String>> updateUserStatus(@PathVariable Integer id, @RequestBody UpdateStatusRequest request) {
        boolean isUpdated = userService.updateUserStatus(id, request.getStatus());

        if (isUpdated) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Statut mis à jour avec succès."));
        } else {
            return ResponseEntity.status(400).body(Collections.singletonMap("error", "Échec de la mise à jour du statut."));
        }
    }
}
