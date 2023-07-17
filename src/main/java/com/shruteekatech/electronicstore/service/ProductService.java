package com.shruteekatech.electronicstore.service;

import com.shruteekatech.electronicstore.dtos.ProductDto;
import com.shruteekatech.electronicstore.util.Pagination;


public interface ProductService {
    ProductDto createProduct(ProductDto productDto);  //create

    //delete
    void deleteProduct(String productId, String imagePath);

    ProductDto updateProduct(ProductDto productDto, String productId); //update

    ProductDto getSingleProduct(String productId); // get Single

    Pagination<ProductDto>
    getAllProducts(Integer pageNo, Integer pageSize, String sortDi, String sortBy); //get all products

    Pagination<ProductDto> getAllLive(Boolean live, Integer pageNo, Integer pageSize, String sortOrder, String sortUsing);

    Pagination<ProductDto> searchByTitle(String title, Integer pageNo, Integer pageSize, String sortDi, String sortBy);   // search by title

    Pagination<ProductDto> searchByPrice(Float price, Integer pageNo, Integer pageSize, String sortDi, String sortBy);    //search by price
}