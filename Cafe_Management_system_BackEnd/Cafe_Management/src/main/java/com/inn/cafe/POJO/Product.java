package com.inn.cafe.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Product.getAllProducts", query = "select new com.inn.cafe.wrapper.ProductWrapper(p.id,p.name,p.description,p.price,p.stutus,p.category.id,p.category.name,p.image_path) from Product p  ")

@NamedQuery(name="Product.updateStatus",query = "update Product p set p.stutus=:stutus where p.id=:id")

@NamedQuery(name="Product.getByCategory",query = "select new com.inn.cafe.wrapper.ProductWrapper(p.id,p.name,p.image_path) from Product p where p.category.id=:id and p.stutus ='true'")

@NamedQuery(name="Product.getProductById",query = "select new com.inn.cafe.wrapper.ProductWrapper(p.id,p.name,p.description,p.price,,p.image_path) from Product p where p.id=:id and p.stutus ='true'")



@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "product")
public class Product implements Serializable {

    private static  final long SerialVersionUID = 123456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;


    @Column(name = "description")
    private String description;



    @Column(name = "price")
    private Integer price;

    @Column(name = "image_path")
    private String image_path;


    @Column(name = "stutus")
    private String stutus;


}
