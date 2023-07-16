package com.shruteekatech.electronicstore.repositories;

import com.shruteekatech.electronicstore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findByTitle(String keyword,Pageable pageable);

    Page<Product> findByLive(Boolean live,Pageable pageable);

    Page<Product> findByPrice(Float price,Pageable pageable);
}
