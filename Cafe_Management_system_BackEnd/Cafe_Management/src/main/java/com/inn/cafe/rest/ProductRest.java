package com.inn.cafe.rest;


import com.inn.cafe.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/product")
public interface ProductRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewProduct(@RequestParam("name") String name,
                                        @RequestParam("categoryID") Integer categoryID,
                                        @RequestParam("price") Double price,
                                        @RequestParam("description") String description,
                                        @RequestParam("image") MultipartFile file
                                        );

    
                                         
    @GetMapping(path = "/get")
    ResponseEntity<List<ProductWrapper>> getAllProducts();



    @PostMapping(path = "/update")
    ResponseEntity<String> updateProduit(@RequestBody Map<String,String> requestMap);


    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleyeProduct(@PathVariable Integer id);


    @PostMapping(path = "/updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody Map<String,String> requestMap);

    @GetMapping(path = "/getByCategory/{id}")
    ResponseEntity<List<ProductWrapper>> getByCategory(@PathVariable Integer id);
    @GetMapping(path = "/getById/{id}")
    ResponseEntity<ProductWrapper> getById(@PathVariable Integer id);


}
