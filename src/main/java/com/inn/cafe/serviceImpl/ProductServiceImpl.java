package com.inn.cafe.serviceImpl;

import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.POJO.Category;
import com.inn.cafe.POJO.Product;
import com.inn.cafe.contents.CafeConstants;
import com.inn.cafe.dao.ProductDao;
import com.inn.cafe.service.ProductService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.wrapper.ProductWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
             if(jwtFilter.isAdmin()){

                 if(validateProductMap(requestMap,false)){

                     productDao.save(getProductFormMap(requestMap,false));
                    return CafeUtils.getResponseEntity("Product Added Seccessfully. ",HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);
            }
            return  CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProducts() {
        try{
            return  new ResponseEntity<>(productDao.getAllProducts(),HttpStatus.OK);

        } catch(Exception ex){
            ex.printStackTrace();
        }
        return  new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduit(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateProductMap(requestMap,true)){
                    Optional<Product> optional= productDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()) {
                        Product product=getProductFormMap(requestMap,true);
                        product.setStutus(optional.get().getStutus());
                        productDao.save(product);
                        return CafeUtils.getResponseEntity("Produit Update Successfully", HttpStatus.OK);
                    } else {
                        return  CafeUtils.getResponseEntity("Produit id does not exist",HttpStatus.OK);
                    }
                }
                return  CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);
            }else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> deleyeProduct(Integer id) {
        try {
            if(jwtFilter.isAdmin()){

                    Optional optional= productDao.findById(id);
                    if(!optional.isEmpty()) {
                         productDao.deleteById(id);
                        return CafeUtils.getResponseEntity("Produit Deleted Successfully", HttpStatus.OK);
                    } else {
                        return  CafeUtils.getResponseEntity("Produit id does not exist",HttpStatus.OK);
                    }

              }else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                    Optional<Product> optional= productDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()) {
                           productDao.updateStatus(requestMap.get("stutus"),Integer.parseInt((requestMap.get("id"))));

                        return CafeUtils.getResponseEntity("Produit Status Updated Successfully", HttpStatus.OK);
                    } else {
                        return  CafeUtils.getResponseEntity("Produit id does not exist",HttpStatus.OK);
                    }

               }else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
        try{
            return  new ResponseEntity<>(productDao.getByCategory(id),HttpStatus.OK);

        } catch(Exception ex){
            ex.printStackTrace();
        }
        return  new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getById(Integer id) {

    try{
           return  new ResponseEntity<>(productDao.getProductById(id),HttpStatus.OK);


    } catch(Exception ex){
        ex.printStackTrace();
    }

        return  new ResponseEntity<>(new ProductWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
}

    private boolean validateProductMap(Map<String, String> requestMap, boolean isValidatId) {

        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && isValidatId) {
                return true;
            }else if(!isValidatId){
                return true;
            }
        }
        return false;
    }

    private Product getProductFormMap(Map<String,String> requestMap, Boolean isAdd){
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("category_fk")));

        Product product = new Product();
         if(isAdd){
            product.setId(Integer.parseInt(requestMap.get("id")));
        } else {
            product.setStutus("true");

        }
        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));


        return product;
    }
}
