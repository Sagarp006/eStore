package com.shruteekatech.electronicstore.service.impl;

import com.shruteekatech.electronicstore.dtos.CategoryDto;
import com.shruteekatech.electronicstore.entities.Category;
import com.shruteekatech.electronicstore.exceptions.ResourceNotFoundException;
import com.shruteekatech.electronicstore.helper.AppConstants;
import com.shruteekatech.electronicstore.repositories.CategoryRepository;
import com.shruteekatech.electronicstore.service.CategoryService;
import com.shruteekatech.electronicstore.util.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Value("${image-path-category}")
    private String imageUploadPath;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        log.info("Creating a new category...");

        // Generate a random category ID
        categoryDto.setId(UUID.randomUUID().toString());
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category saveCategory = this.categoryRepository.save(category);

        log.info("New category created with ID: {}", saveCategory.getId());
        return this.modelMapper.map(saveCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        log.info("Updating category with ID: {}", categoryId);

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NA + categoryId));

        // Update the category with new data
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());

        Category updatedCategory = this.categoryRepository.save(category);

        log.info("Category updated successfully.");
        return this.modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {
        log.info("Deleting category with ID: {}", categoryId);

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NA + categoryId));

        String fullPath = imageUploadPath + category.getCoverImage();
        try {
            // Delete the category's cover image from the file system
            Files.delete(Paths.get(fullPath));
        } catch (NoSuchFileException e) {
            log.error("Image not found in folder: {}", e.getMessage());
        } catch (IOException e) {
            log.error("Unable to delete image: {}", e.getMessage());
        }

        this.categoryRepository.delete(category);
        log.info("Category deleted successfully with ID: {}", categoryId);
    }

    @Override
    public Pagination<CategoryDto> getAllCategories(Integer pageNo, Integer pageSize, String sortDi, String sortBy) {
        log.info("Fetching all categories with : pageNo {}, pageSize {}, sortDirection {}, sortBy {}",
                pageNo, pageSize, sortDi, sortBy);

        Pageable pageable = Pagination.from(pageNo, pageSize, sortDi, sortBy);
        Page<Category> page = this.categoryRepository.findAll(pageable);

        log.info("Fetched {} categories.", page.getTotalElements());
        return Pagination.of(page, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(String categoryId) {
        log.info("Fetching category with ID: {}", categoryId);

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NA + categoryId));

        log.info("Category fetched successfully.");
        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> searchCategory(String title) {
        log.info("Searching for categories with title containing: {}", title);
        List<Category> categoryList = this.categoryRepository.findByTitleContaining(title);
        log.info("Found {} categories.", categoryList.size());
        return categoryList.stream()
                .map(category -> this.modelMapper.map(category, CategoryDto.class))
                .toList();
    }
}
