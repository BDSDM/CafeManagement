package org.example.serviceImpl;

import org.example.dao.UserDao;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.example.POJO.User;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

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
        user.setPassword(requestMap.get("password"));
        user.setStatus("false"); // Initial status for new user
        user.setRole("user");     // Default role
        return user;
    }
}
