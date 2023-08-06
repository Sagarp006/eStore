package com.shruteekatech.electronicstore.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Category {
    @Id
    private String id;
    @Column(name = "category_title",unique = true)
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "cover_Image")
    private String coverImage;
}
