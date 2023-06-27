package com.shruteekatech.electronicstore.repositories;

import com.shruteekatech.electronicstore.entities.Product;
import com.shruteekatech.electronicstore.util.PageableResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,String> {
    PageableResponse<Product> findByTitle(String keyword);
    PageableResponse<Product> findByLive(Boolean ifTrue);
    PageableResponse<Product> findByPrice(Float price);
}
