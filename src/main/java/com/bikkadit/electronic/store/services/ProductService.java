package com.bikkadit.electronic.store.services;

import com.bikkadit.electronic.store.dtos.ProductDto;
import com.bikkadit.electronic.store.helper.PageableResponse;

public interface ProductService {


    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto, String productId);

    void deleteProductById(String productId);

    ProductDto getProductById(String productId);

    PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir);



}
