package com.inn.cafe.serviceImpl;

import com.google.common.base.Strings;
import com.inn.cafe.JWT.CustomerUserDetailsService;
import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.JWT.JwtUtil;
import com.inn.cafe.POJO.User;
import com.inn.cafe.contents.CafeConstants;
import com.inn.cafe.dao.UserDao;
import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.utils.EmailUtils;
import com.inn.cafe.wrapper.UserWrapper;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class UserServiceimpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtils emailUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requsetMap) {
        log.info("Inside signUp {}", requsetMap);
        try {

            if (validateSignUpMap(requsetMap)) {
                User user = userDao.findByEmailId(requsetMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requsetMap));
                    return CafeUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);
                } else {
                    return CafeUtils.getResponseEntity("Email already exits", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Map<String, Object>> login(Map<String, String> requestMap) {
        log.info("Inside login");
        String email = requestMap.get("email");
        String password = requestMap.get("password");
        if (email == null || password == null) {
            return new ResponseEntity<>(Map.of("message", "Email and password are required."), HttpStatus.BAD_REQUEST);
        }
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            if (auth.isAuthenticated()) {
                UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
                String token = jwtUtil.generatedToken(userDetails);
                User user = userDao.findByEmailId(email);
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("user", user);

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("message", "Invalid credentials."), HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception ex) {
            log.error("Error during login: {}", ex.getMessage());
            return new ResponseEntity<>(Map.of("message", "Something went wrong"), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser(HttpServletRequest request) {
        try {
            log.info("Inside getAllUser ");

            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                log.warn("Missing or invalid Authorization header");
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }

            String token = authorizationHeader.substring(7);

            Claims claims = jwtUtil.extratAllClaims(token);

            if (jwtFilter.isAdmin(claims)) {

                return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap, HttpServletRequest httpRequest) {
        try {
            log.info("Inside Update ");

            // Extract token from Authorization header
            String authorizationHeader = httpRequest.getHeader("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                log.warn("Missing or invalid Authorization header");
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

            String token = authorizationHeader.substring(7);
            Claims claims = jwtUtil.extratAllClaims(token);

            // Check if the user is an admin
            if (!jwtFilter.isAdmin(claims)) {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

            // Check if user ID is provided
            if (!requestMap.containsKey("id")) {
                return CafeUtils.getResponseEntity("User ID is required.", HttpStatus.BAD_REQUEST);
            }

            int userId = Integer.parseInt(requestMap.get("id"));
            Optional<User> optionalUser = userDao.findById(userId);

            if (optionalUser.isEmpty()) {
                return CafeUtils.getResponseEntity("User ID does not exist.", HttpStatus.NOT_FOUND);
            }

            // Get user from database
            User user = optionalUser.get();

            // Update user details (if provided in request)
            if (requestMap.containsKey("name")) {
                user.setName(requestMap.get("name"));
            }
            if (requestMap.containsKey("email")) {
                user.setEmail(requestMap.get("email"));
            }
            if (requestMap.containsKey("password")) {
                String encodedPassword = passwordEncoder.encode(requestMap.get("password"));
                user.setPassword(encodedPassword);
            }
            if (requestMap.containsKey("status")) {
                user.setStatus(requestMap.get("status"));
            }

            // Save updated user
            userDao.save(user);

            // Notify admins
            // sendMailToAllAdmin(user.getStatus(), user.getEmail(), userDao.getAllAdmin());

            return CafeUtils.getResponseEntity("User updated successfully.", HttpStatus.OK);
        } catch (NumberFormatException ex) {
            log.error("Invalid user ID format: {}", requestMap.get("id"));
            return CafeUtils.getResponseEntity("Invalid user ID format.", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("Error updating user: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
        allAdmin.remove(jwtFilter.getCurrentUser());
        if (status != null && status.equalsIgnoreCase("true")) {
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Approved",
                    "USER:-" + user + "\n is approved by \n Admin:-" + jwtFilter.getCurrentUser(), allAdmin);

        } else {

            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Disable",
                    "USER:-" + user + "\n is disable by \n Admin:-" + jwtFilter.getCurrentUser(), allAdmin);

        }
    }

    private boolean validateSignUpMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email")
                && requestMap.containsKey("password")) {
            return true;
        }
        return false;
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(passwordEncoder.encode(requestMap.get("password")));

        user.setStatus("false");
        user.setRole(requestMap.get("role"));
        return user;
    }

    public ResponseEntity<String> checkToken(HttpServletRequest request) {
        try {
            // Extract the Authorization header
            log.info("Inside checkToken ");
            log.info("request : {} ",request);


            String authorizationHeader = request.getHeader("Authorization");
            log.info("authorizationHeader : {} ",authorizationHeader);
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                log.warn("Missing or invalid Authorization header");
                return new ResponseEntity<>("Invalid or missing token", HttpStatus.UNAUTHORIZED);
            }
    
            // Extract the token (remove "Bearer " prefix)
            String token = authorizationHeader.substring(7);
    
            // Validate the token and extract claims
            Claims claims = jwtUtil.extratAllClaims(token);
    
            if (claims == null) {
                return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
            }
    
            // Get username and check if the token is still valid
            String username = jwtUtil.extractUsername(token);
            if (username == null || username.isEmpty()) {
                return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
            }
    
            // Get user roles (if available)
            List<String> roles = claims.get("role", List.class);
            log.info("Token is valid for user: {}, roles: {}", username, roles);
    
            return new ResponseEntity<>("Token is valid", HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error validating token: {}", ex.getMessage(), ex);
            return new ResponseEntity<>("Invalid or expired token", HttpStatus.UNAUTHORIZED);
        }
    }
    

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {

            User userObj = userDao.findByEmail(jwtFilter.getCurrentUser());
            if (!userObj.equals(null)) {
                if (userObj.getPassword().equals(requestMap.get("oldPassword"))) {
                    userObj.setPassword(requestMap.get("newPassword"));
                    userDao.save(userObj);
                    emailUtils.sendSimpleMessage(userObj.getEmail(), "Change Password :-",
                            "" + "\n You are to change Password:-", new ArrayList<>());
                    return CafeUtils.getResponseEntity("Password Updated Successfully", HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity("Incorrect Old Password", HttpStatus.BAD_REQUEST);

            }
            return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            User user = userDao.findByEmail(requestMap.get("email"));
            if (!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail()))
                emailUtils.forgotMail(user.getEmail(), "Credentials by Cafe Management System", user.getPassword());
            return CafeUtils.getResponseEntity("Check your mail for Credentials.", HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
