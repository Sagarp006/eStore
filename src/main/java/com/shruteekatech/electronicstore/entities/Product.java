package com.shruteekatech.electronicstore.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    private String id;
    @Column(name = "product_title")
    private String title;
    @Column(name = "product_description", length = 10000)
    private String description;
    @Column(name = "product_price")
    private Float price;
    @Column(name = "product_discounted_price")
    private Float discountedPrice;
    @Column(name = "product_quantity")
    private Integer quantity;
    @Column(name = "added_date")
    private Date addedDate;
    @Column(name = "isLive")
    private Boolean live;
    @Column(name = "inStock")
    private Boolean stock;
    private String imageName;
}
