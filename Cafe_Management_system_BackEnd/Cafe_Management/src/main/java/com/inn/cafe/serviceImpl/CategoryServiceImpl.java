package com.inn.cafe.serviceImpl;

import com.google.common.base.Strings;
import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.POJO.Category;
import com.inn.cafe.contents.CafeConstants;
import com.inn.cafe.dao.CategoryDao;
import com.inn.cafe.service.CategoryService;
import com.inn.cafe.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;


    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> reqestMap) {
      try{

          if(jwtFilter.isAdmin()) {
              if (validateCategoryMap(reqestMap, false)) {
                  categoryDao.save(getCategoryFormMap(reqestMap,false));
                  return  CafeUtils.getResponseEntity("Category Added Successfully", HttpStatus.OK);
              }
          } else {
                  return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);

              }


      } catch (Exception ex) {
           ex.printStackTrace();
      }

      return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try{
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(),HttpStatus.OK);
            }
            return  new ResponseEntity<>(categoryDao.findAll(),HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> reqestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateCategoryMap(reqestMap,true)){
                   Optional optional= categoryDao.findById(Integer.parseInt(reqestMap.get("id")));
                    if(!optional.isEmpty()) {
                        categoryDao.save(getCategoryFormMap(reqestMap,true));
                        return CafeUtils.getResponseEntity("Category Update Successfully", HttpStatus.OK);
                    } else {
                        return  CafeUtils.getResponseEntity("Category id does not exist",HttpStatus.OK);
                    }
                }
                return  CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);
            }else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }


        } catch(Exception ex){
          ex.printStackTrace();
        }
        return new ResponseEntity<>(CafeConstants.SOMTHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private boolean validateCategoryMap(Map<String, String> reqestMap, boolean validateId) {
        if(reqestMap.containsKey("name")){
            if(reqestMap.containsKey("id") && validateId){
                return true;
            }else {
                if (!validateId)
                    return  true;
            }
        }
        return  false;
    }

    private Category getCategoryFormMap(Map<String,String> requestMap, Boolean isAdd){
        Category category = new Category();
        if(isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName((requestMap.get("name")));
        return category;
    }
}
