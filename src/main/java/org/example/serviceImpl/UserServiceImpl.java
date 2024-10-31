package org.example.serviceImpl;

import org.example.JWT.JwtUtil;
import org.example.dao.UserDao;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.POJO.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            if (validateSignUpMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (user == null) {
                    userDao.save(getUserFromMap(requestMap));
                    String jsonResponse = "{\"message\": \"User registered successfully\", \"status\": 200}";
                    return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
                } else {
                    String jsonResponse = "{\"message\": \"Email already exists\", \"status\": 400}";
                    return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
                }
            } else {
                String jsonResponse = "{\"message\": \"Invalid data\", \"status\": 400}";
                return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            String jsonResponse = "{\"message\": \"Something went wrong\", \"status\": 500}";
            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            User user = userDao.findByEmailId(requestMap.get("email"));
            if (user != null && passwordEncoder.matches(requestMap.get("password"), user.getPassword())) {
                if ("true".equals(user.getStatus())) {
                    String token = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getName(),user.getId(),user.getStatus());
                    String jsonResponse = "{\"token\": \"" + token + "\"}";
                    return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("{\"message\": \"User is inactive\", \"status\": 403}", HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>("{\"message\": \"Invalid credentials\", \"status\": 400}", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("{\"message\": \"Something went wrong\", \"status\": 500}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean validateSignUpMap(Map<String, String> requestMap) {
        return requestMap.containsKey("name")
                && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email")
                && requestMap.containsKey("password");
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(passwordEncoder.encode(requestMap.get("password")));
        user.setStatus("true"); // Initial status for new user
        user.setRole("user");     // Default role
        return user;
    }
    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userDao.findAll();  // Appelle findAll() pour récupérer tous les utilisateurs
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Pas d'utilisateurs trouvés
            }
            return new ResponseEntity<>(users, HttpStatus.OK); // Renvoie la liste d'utilisateurs avec le code 200 OK
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Gérer les erreurs serveur
        }
    }
    @Override
    public void deleteUserById(Integer id) {
        if (userDao.existsById(id)) {
            userDao.deleteById(id);
        } else {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
    }
    @Override
    public boolean updateUserStatus(Integer id, String status) {
        // Rechercher l'utilisateur par son ID
        User user = userDao.findById(id).orElse(null);

        if (user != null) {
            // Modifier le statut de l'utilisateur
            user.setStatus(status);
            userDao.save(user);
            return true;
        }

        return false;
    }
    @Override
    public User updateUser(Integer userId, User updatedUser) {
        Optional<User> existingUserOptional = userDao.findById(userId);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setContactNumber(updatedUser.getContactNumber());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setStatus(updatedUser.getStatus());
            existingUser.setRole(updatedUser.getRole());

            return userDao.save(existingUser); // Enregistre et retourne l'utilisateur mis à jour
        } else {
            throw new RuntimeException("Utilisateur non trouvé avec l'ID : " + userId);
        }
    }

}
