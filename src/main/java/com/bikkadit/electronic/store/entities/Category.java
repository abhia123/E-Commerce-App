package com.bikkadit.electronic.store.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "category_title")
    private String title;

    @Column(name = "category_desc")
    private String description;

    @Column(name = "category_img")
    private String coverImage;

}
