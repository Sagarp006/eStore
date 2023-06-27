package com.shruteekatech.electronicstore.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String productId;
    private String title;
    @NotBlank
    @Min(value = 5)
    private String description;
    private Float price;
    private Float discountedPrice;
    @NotBlank
    private Integer quantity;
    private Date addedDate;
    @NotBlank
    private Boolean live;
    @NotBlank
    private Boolean stock;
    private String imageName;
}
