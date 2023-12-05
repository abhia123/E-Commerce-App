package com.bikkadit.electronic.store.services.impl;


import com.bikkadit.electronic.store.dtos.ProductDto;
import com.bikkadit.electronic.store.entities.Product;
import com.bikkadit.electronic.store.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.AppConstants;
import com.bikkadit.electronic.store.helper.PageableHelper;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.repositories.ProductRepository;
import com.bikkadit.electronic.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        logger.info("Initiating DAO call for creating product data");
        Product product = mapper.map(productDto, Product.class);
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        product.setAddedDate(new Date());
        Product saveProduct = productRepository.save(product);
        logger.info("Completed DAO call for creating product data");
        return mapper.map(saveProduct, ProductDto.class);

    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        return null;
    }

    @Override
    public void deleteProductById(String productId) {
        logger.info("Initiating DAO call for deleting product data with productId :{}",productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + productId));
        productRepository.delete(product);
        logger.info("Completed DAO call for deleting product data with productId :{}",productId);
    }

    @Override
    public ProductDto getProductById(String productId) {
        logger.info("Initiating DAO call for getting product data with productId :{}",productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + productId));
        logger.info("Completed DAO call for getting product data with productId :{}",productId);
        return mapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Initiating DAO call for getting all product data with pagination and sorting");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findAll(pageable);
        logger.info("Completed DAO call for getting all product data with pagination and sorting");
        return PageableHelper.getPageableResponse(page, ProductDto.class);

    }
}
