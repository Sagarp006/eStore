package com.shruteekatech.electronicstore.service;


import com.shruteekatech.electronicstore.dtos.AddItemToCartRequest;
import com.shruteekatech.electronicstore.dtos.CartDto;

public interface CartService {
    //adding items to cart
    CartDto createCart(String userId, AddItemToCartRequest itemToCartRequest);

    //remove item from cart
    void removeItemFromCart(String userId,Integer cartItemId);
    void clearCart(String userId);
}
