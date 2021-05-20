package com.zzq.farmproductplatform.jpa;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "productbigcategory")
public class ProductBigCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "product_big_category_name")
    private String categoryName;
}
