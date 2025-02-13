package com.inn.cafe.rest;


import com.inn.cafe.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(path = "/user")
public interface UserRest {

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String , String> requestMap);

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody(required = true) Map<String,String> requestMap);


    @GetMapping(path = "/get")
    public ResponseEntity<List<UserWrapper>> getAllUser(HttpServletRequest request);

    @PostMapping(path = "/update")
    public ResponseEntity<String> update(@RequestBody(required = true) Map<String,String> requestMap,HttpServletRequest httpRequest);


    @GetMapping(path = "/checkToken")
    public ResponseEntity<String> checkToken(HttpServletRequest request);

    @PostMapping(path = "/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody Map<String,String> requestMap);


    @PostMapping(path = "/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String,String> requestMap);



}
