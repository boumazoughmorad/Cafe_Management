package com.inn.cafe.wrapper;


import javax.persistence.Column;

import lombok.Data;

@Data

public class ProductWrapper {

    private Integer id;

    private String name;

    private String description;

    private Integer price;

    private String status;

    private Integer categoryId;

    private String categoryName;

    private String image_path;
    



    public ProductWrapper() {
    }

    public ProductWrapper(Integer id, String name, String description, Integer price, String status, Integer categoryId, String categoryName, String image_path) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.image_path = image_path;

    }

    public ProductWrapper(Integer id, String name, String image_path) {
        this.id = id;
        this.name = name;
        this.image_path = image_path;

    }

    public ProductWrapper(Integer id, String name, String description, Integer price, String image_path) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image_path = image_path;

    }

}
