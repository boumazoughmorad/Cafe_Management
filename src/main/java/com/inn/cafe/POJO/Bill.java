package com.inn.cafe.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;


@NamedQuery(name = "Bill.getAllBill" , query = "select b from Bill b order by b.id desc ")

@NamedQuery(name = "Bill.getAllBillByUserName" , query = "select b from Bill b where b.createdBy =: username order by b.id desc")

@Data
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "bill")
public class Bill implements Serializable {

    private static  final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;


    @Column(name = "contactnumber")
    private String contactNumber;

    @Column(name = "paymentmethod")
    private String paymentmethode;

    @Column(name = "total")
    private Integer total;


    @Column(name = "productDetail", columnDefinition = "json")
    private String productDetail;

    @Column(name = "creadetby")
    private String createdBy;

}
