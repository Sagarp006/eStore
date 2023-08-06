package com.shruteekatech.electronicstore.controllers;

import com.shruteekatech.electronicstore.dtos.CategoryDto;
import com.shruteekatech.electronicstore.helper.AppConstants;
import com.shruteekatech.electronicstore.helper.ImageResponse;
import com.shruteekatech.electronicstore.service.FileService;
import com.shruteekatech.electronicstore.util.Pagination;
import com.shruteekatech.electronicstore.service.CategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;

/**
 * The CategoryController class handles Category-related operations .
 * It provides methods to create new Category,delete Category, get existing Category, and update Category .
 *
 * @author sagar padwekar
 * @version 1.8
 * @since 2023
 */
@Slf4j
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final FileService fileService;

    @Value("${image-path-category}")
    private String imageUploadPath;


    /* @author sagar padwekar
     * Create a new category.
     *
     * @param categoryDto The CategoryDto object containing category details.
     * @return ResponseEntity with the created category and HTTP status OK.
     */
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        log.info("Creating a new category: {}", categoryDto);
        CategoryDto category = this.categoryService.createCategory(categoryDto);
        log.info("New category created: {}", category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    /* @author sagar padwekar
     * Update an existing category by categoryId.
     *
     * @param categoryDto The CategoryDto object containing updated category details.
     * @param categoryId The ID of the category to be updated.
     * @return ResponseEntity with the updated category and HTTP status OK.
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {
        log.info("Updating category with ID: {}", categoryId);
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);
        log.info("Category with ID {} updated", categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    /* @author sagar padwekar
     * Delete a category by categoryId.
     *
     * @param categoryId The ID of the category to be deleted.
     * @return ResponseEntity with a message and HTTP status OK.
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable String categoryId) {
        log.info("Deleting category with ID: {}", categoryId);
        this.categoryService.deleteCategory(categoryId);
        Map<String, Object> response = Map.of("message", AppConstants.CATE_DEL, "status", HttpStatus.OK);
        log.info("Category with ID {} deleted successfully.", categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /* @author sagar padwekar
     * Get a category by categoryId.
     *
     * @param categoryId The ID of the category to retrieve.
     * @return ResponseEntity with the requested category and HTTP status FOUND.
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable String categoryId) {
        log.info("Fetching category with ID: {}", categoryId);
        CategoryDto categoryDto = this.categoryService.getCategory(categoryId);
        log.info("Category with ID {} retrieved: {}", categoryId, categoryDto);
        return new ResponseEntity<>(categoryDto, HttpStatus.FOUND);
    }

    /* @author sagar padwekar
     * Get all categories with pagination and sorting options.
     *
     * @param pageNo The page number for pagination.
     * @param pageSize The number of categories to be returned per page.
     * @param sortDi The sorting direction (asc or desc).
     * @param sortBy The field to sort by (title, name, email, etc.).
     * @return ResponseEntity with a Pagination object containing categories and HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<Pagination<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(value = "sortDi", required = false, defaultValue = "asc") String sortDi,
            @RequestParam(value = "sortBy", required = false, defaultValue = "title") String sortBy
    ) {
        log.info("Fetching all categories with pageNo: {}, pageSize: {}, sortDi: {}, sortBy: {}", pageNo, pageSize, sortDi, sortBy);
        Pagination<CategoryDto> allCategories = this.categoryService.getAllCategories(pageNo, pageSize, sortDi, sortBy);
        log.info("Fetched all categories: {}", allCategories);
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    /* @author sagar padwekar
     * Search categories by keyword with pagination and sorting options.
     *
     * @param keyword The keyword to search for in category titles.
     * @param pageNo The page number for pagination.
     * @param pageSize The number of categories to be returned per page.
     * @param sortDi The sorting direction (asc or desc).
     * @param sortBy The field to sort by title.
     * @return ResponseEntity with a Pagination object containing matched categories and HTTP status OK.
     */
    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<List<CategoryDto>> searchCategory(@PathVariable String keyword) {
        log.info("Searching categories with keyword: {}", keyword);
        List<CategoryDto> searchCategory = this.categoryService.searchCategory(keyword);
        log.info("Categories matching the search criteria: {}", searchCategory);
        return new ResponseEntity<>(searchCategory, HttpStatus.OK);
    }

    /* @author sagar padwekar
     * Upload an image for a category.
     *
     * @param cateImage The MultipartFile containing the category image.
     * @param categoryId The ID of the category to which the image belongs.
     * @return ResponseEntity with an ImageResponse object containing the image details and HTTP status CREATED.
     */
    @PostMapping("/uploadImage/{cateId}")
    public ResponseEntity<ImageResponse> uploadCategoryImage(
            @RequestParam(value = "cateImage") MultipartFile cateImage, @PathVariable(value = "cateId") String categoryId) {
        log.info("Image upload process is being started for category with ID: {}", categoryId);
        try {
            String imageName = this.fileService.uploadFile(cateImage, imageUploadPath);
            CategoryDto category = this.categoryService.getCategory(categoryId);
            category.setCoverImage(imageName);
            this.categoryService.updateCategory(category, categoryId);
            ImageResponse imageResponse = ImageResponse.of(imageName, categoryId);
            log.info("Image uploaded successfully for category with ID: {}", categoryId);
            return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new UncheckedIOException(e);
        }
    }

    /* @author sagar padwekar
     * Serve the image for a category.
     *
     * @param categoryId The ID of the category to serve the image for.
     * @param response The HttpServletResponse to write the image content to.
     * @throws IOException If an I/O error occurs while serving the image.
     */
    @GetMapping("image/{categoryId}")
    public void serveImage(@PathVariable String categoryId, HttpServletResponse response)  {
        log.info("Initiated request to serve category image with categoryId: {}", categoryId);
        CategoryDto category = this.categoryService.getCategory(categoryId);
        try (InputStream resource = this.fileService.getResource(imageUploadPath, category.getCoverImage())) {
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource, response.getOutputStream());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new UncheckedIOException(e);
        }
        log.info("Completed request to serve category image with categoryId: {}", categoryId);
    }
}
