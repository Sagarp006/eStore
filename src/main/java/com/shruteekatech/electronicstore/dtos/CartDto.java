package com.shruteekatech.electronicstore.dtos;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private String id;
    private Date createdAt;
    private UserDto user;
    private List<CartItemDto> items;
}