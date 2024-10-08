package org.example.rest;

import org.springframework.http.ResponseEntity;
import java.util.Map;

public interface UserRest {
    ResponseEntity<String> signUp(Map<String, String> requestMap);
}