package com.shruteekatech.electronicstore.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String id;
    @NotBlank(message = "enter title")
    private String title;

    private String description;
    @NotNull(message = "enter the price of a product")
    private Float price;
    private Float discountedPrice;
    @Min(value = 0,message = "enter the quantity of the product")
    private Integer quantity;
    private Date addedDate;
    @NotNull
    private Boolean live;
    @NotNull
    private Boolean stock;
    private String imageName;
}
