package com.shruteekatech.electronicstore.service.impl;

import com.shruteekatech.electronicstore.dtos.ProductDto;
import com.shruteekatech.electronicstore.entities.Product;
import com.shruteekatech.electronicstore.exceptions.ResourceNotFoundException;
import com.shruteekatech.electronicstore.helper.AppConstants;
import com.shruteekatech.electronicstore.repositories.ProductRepository;
import com.shruteekatech.electronicstore.service.ProductService;
import com.shruteekatech.electronicstore.util.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

//    @Autowired
//    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository) {
//        this.modelMapper = modelMapper;
//        this.productRepository = productRepository;
//    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = this.modelMapper.map(productDto, Product.class);
        product.setId(UUID.randomUUID().toString());  //creating random-unique id in String
        product.setAddedDate(new Date());
        Product savedProduct = this.productRepository.save(product);
        return this.modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId,String imagePath) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NA));
        try {
            Path filePath = Path.of(imagePath+product.getImageName());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Handle any exceptions or log the error message
            e.printStackTrace();
        }
        this.productRepository.delete(product);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NA));

        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
        this.modelMapper.map(productDto,product);

        Product savedProduct = this.productRepository.save(product);

        return this.modelMapper.map(savedProduct,ProductDto.class);

 //        product.setTitle(productDto.getTitle());
//        product.setDiscountedPrice(productDto.getDiscountedPrice());
//        product.setStock(productDto.getStock());
//        product.setLive(productDto.getLive());
//        product.setPrice(productDto.getPrice());
//        product.setDescription(productDto.getDescription());
//        product.setImageName(productDto.getImageName());
//        Product savedProduct = this.productRepository.save(product);
//        return this.modelMapper.map(product,ProductDto.class);
    }

    @Override
    public ProductDto getSingleProduct(String productId) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NA));
        return this.modelMapper.map(product,ProductDto.class);
    }

    @Override
    public Pagination<ProductDto> getAllProducts(Integer pageNo, Integer pageSize, String order, String sortUsing) {
        Pageable pageable = Pagination.from(pageNo, pageSize, order, sortUsing);
        Page<Product> products = this.productRepository.findAll(pageable);
        return Pagination.of(products,ProductDto.class);
    }

    @Override
    public Pagination<ProductDto> getAllLive(Boolean live,Integer pageNo, Integer pageSize, String sortOrder, String sortUsing) {
        Pageable pageable = Pagination.from(pageNo, pageSize, sortOrder, sortUsing);
        Page<Product> products = this.productRepository.findByLive(live,pageable);
        return Pagination.of(products,ProductDto.class);
    }

    @Override
    public Pagination<ProductDto> searchByTitle(String title, Integer pageNo, Integer pageSize, String sortOrder, String sortUsing) {
        Pageable pageable = Pagination.from(pageNo, pageSize, sortOrder, sortUsing);
        Page<Product> products = this.productRepository.findByTitle(title,pageable);
        return Pagination.of(products,ProductDto.class);
    }

    @Override
    public Pagination<ProductDto> searchByPrice(Float price, Integer pageNo, Integer pageSize, String sortOrder, String sortUsing) {
        Pageable pageable = Pagination.from(pageNo, pageSize, sortOrder, sortUsing);
        Page<Product> products = this.productRepository.findByPrice(price,pageable);
        return Pagination.of(products,ProductDto.class);
    }
}
