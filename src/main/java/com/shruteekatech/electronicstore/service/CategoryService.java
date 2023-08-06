package com.shruteekatech.electronicstore.service;

import com.shruteekatech.electronicstore.dtos.CategoryDto;
import com.shruteekatech.electronicstore.util.Pagination;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);     //create category

    CategoryDto updateCategory(CategoryDto categoryDto, String categoryId); //update category

    void deleteCategory(String categoryId); //delete category

    CategoryDto getCategory(String categoryId);  //get category

    Pagination<CategoryDto>
    getAllCategories(Integer pageNo, Integer pageSize, String sortDir, String sortBy);//get all categories

    List<CategoryDto> searchCategory(String title);
}
