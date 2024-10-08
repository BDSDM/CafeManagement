package org.example.restImpl;


import org.example.rest.UserRest;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/user")
public class UserRestImpl implements UserRest {

    @Autowired
    private UserService userService;

    @Override
    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody Map<String, String> requestMap) {
        requestMap.forEach((key, value)-> System.out.println(key+ ":"+value));
        return userService.signUp(requestMap);
    }
}

