package com.inn.cafe.dao;

import com.inn.cafe.POJO.Product;
import com.inn.cafe.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductDao extends JpaRepository<Product,Integer> {
    List<ProductWrapper> getAllProducts();

    @Modifying
    @Transactional
    Integer updateStatus(@Param("stutus") String stutus, @Param("id") int id);

    List<ProductWrapper> getByCategory(@Param("id") int id);

    ProductWrapper getProductById(Integer id);

    //List<Product> getAllProduct();
}
