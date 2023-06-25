package com.shruteekatech.electronicstore.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDto {
    private String id;
    private String title;
    private String description;
    private String coverImage;
}
