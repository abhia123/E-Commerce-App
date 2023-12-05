package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.dtos.CategoryDto;
import com.bikkadit.electronic.store.dtos.ProductDto;
import com.bikkadit.electronic.store.helper.UrlConstants;
import com.bikkadit.electronic.store.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(UrlConstants.BASE_URL + UrlConstants.PRODUCT_URL)
public class ProductController {

    @Autowired
    private ProductService productService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        logger.info("Entering request for create product data");
        ProductDto savedProductDto = productService.createProduct(productDto);
        logger.info("Completed request for create product data");
        return new ResponseEntity<>(savedProductDto, HttpStatus.CREATED);
    }

}
