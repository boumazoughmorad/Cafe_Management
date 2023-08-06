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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

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


    @Override
    public ResponseEntity<String> signUp(Map<String, String> requsetMap) {
        log.info("Inside signUp {}",requsetMap);
        try {


        if(validateSignUpMap(requsetMap)){
             User user=userDao.findByEmailId(requsetMap.get("email"));
             if(Objects.isNull(user)){
                 userDao.save(getUserFromMap(requsetMap));
                 return CafeUtils.getResponseEntity("Successfully Registered",HttpStatus.OK);
             }
             else{
                 return CafeUtils.getResponseEntity("Email already exits",HttpStatus.BAD_REQUEST);
             }
        }else {
        return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }}catch (Exception ex){
            ex.printStackTrace();
        }
     return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requsetMap) {
        log.info("Inside login");
        try{
            Authentication auth= authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requsetMap.get("email"),requsetMap.get("password"))
            );

            if(auth.isAuthenticated()){
                if(customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                   return new ResponseEntity<String>("{\"token\":\""+
                           jwtUtil.generatedToken(customerUserDetailsService.getUserDetail().getEmail(),
                           customerUserDetailsService.getUserDetail().getRole())+"\"}",HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval."+"\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception e){
            log.error("{}",e);
        }
        return new ResponseEntity<String>("{\"message\":\""+"Bad Credentials."+"\"}",HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {

            if(jwtFilter.isAdmin()){

                  return new ResponseEntity<>(userDao.getAllUser(),HttpStatus.OK);
            }else{
                return  new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                 Optional<User> optional= userDao.findById(Integer.parseInt(requestMap.get("id")));

                 if(!optional.isEmpty()){
                     userDao.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                     sendMailToAllAdmin(requestMap.get("status"),optional.get().getEmail(),userDao.getAllAdmin());
                     return CafeUtils.getResponseEntity("User Status Updated Successfully",HttpStatus.OK);
                 }else{
                     return  CafeUtils.getResponseEntity("User id doesn not exist",HttpStatus.OK);
                 }
            }else{
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(CafeConstants.SOMTHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }


    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
        allAdmin.remove(jwtFilter.getCurrentUser());
        if(status!=null && status.equalsIgnoreCase("true")){
          emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account Approved","USER:-"+user+"\n is approved by \n Admin:-"+jwtFilter.getCurrentUser(),allAdmin);

        } else {

            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account Disable","USER:-"+user+"\n is disable by \n Admin:-"+jwtFilter.getCurrentUser(),allAdmin);

        }
    }

    private boolean validateSignUpMap(Map<String,String> requestMap){
       if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email")
                && requestMap.containsKey("password")){
           return true;
       }
       return false;
    }

    private User getUserFromMap(Map<String,String> requestMap){
        User user=new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return  user;
    }

    @Override
    public ResponseEntity<String> checkToken() {
        return CafeUtils.getResponseEntity("true",HttpStatus.OK);


    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {

          User userObj=userDao.findByEmail(jwtFilter.getCurrentUser());
          if(!userObj.equals(null)){
              if(userObj.getPassword().equals(requestMap.get("oldPassword"))){
                 userObj.setPassword(requestMap.get("newPassword"));
                 userDao.save(userObj);
                emailUtils.sendSimpleMessage(userObj.getEmail(),"Change Password :-",""+"\n You are to change Password:-", new ArrayList<>());
                  return CafeUtils.getResponseEntity("Password Updated Successfully",HttpStatus.OK);
              }
              return CafeUtils.getResponseEntity("Incorrect Old Password",HttpStatus.BAD_REQUEST);

          }
            return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

        } catch(Exception ex){
         ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
       try{
           User user = userDao.findByEmail(requestMap.get("email"));
           if(!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail()))
               emailUtils.forgotMail(user.getEmail(), "Credentials by Cafe Management System",user.getPassword());
               return CafeUtils.getResponseEntity("Check your mail for Credentials.",HttpStatus.OK);


       } catch (Exception ex){
           ex.printStackTrace();
       }
        return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
