package com.shruteekatech.electronicstore.repositories;

import com.shruteekatech.electronicstore.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,String> {
}
