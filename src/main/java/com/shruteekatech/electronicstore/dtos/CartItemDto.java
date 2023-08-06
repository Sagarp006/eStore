package com.shruteekatech.electronicstore.dtos;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Integer id;
    private ProductDto product;
    private Integer quantity;
    private Integer totalPrice;
    private CartDto cart;
}
