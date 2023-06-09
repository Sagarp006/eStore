package com.shruteekatech.electronicstore.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Category {
    private String id;
    @Column(name = "category_title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "cover_Image")
    private String coverImage;
}
