package com.inn.cafe.service;

import com.inn.cafe.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

     ResponseEntity<String> signUp(Map<String,String> requsetMap);


     ResponseEntity<Map<String, Object>> login(Map<String,String> requsetMap);

     ResponseEntity<List<UserWrapper>> getAllUser(HttpServletRequest request);

     ResponseEntity<String> update(Map<String,String> requestMap,HttpServletRequest httpRequest);

     ResponseEntity<String> checkToken(HttpServletRequest request);
     ResponseEntity<String> changePassword(Map<String ,String> requestMap);


     ResponseEntity<String> forgotPassword(Map<String ,String> requestMap);
}
