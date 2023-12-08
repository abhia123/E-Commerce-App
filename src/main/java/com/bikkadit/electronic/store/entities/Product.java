package com.bikkadit.electronic.store.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    private String productId;

    @Column(name = "product_title")
    private String title;

    @Column(name = "product_desc")
    private String description;

    @Column(name = "product_price")
    private Double price;

    @Column(name = "product_disc_price")
    private int discountedPrice;

    @Column(name = "product_qty")
    private Integer quantity;

    @Column(name = "product_added_date")
    private Date addedDate;

    @Column(name = "product_live")
    private Boolean isLive;

    @Column(name = "product_stock")
    private Boolean isStock;

    @Column(name = "product_img")
    private String productImageName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private  Category category;
}
