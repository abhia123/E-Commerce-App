package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.dtos.CategoryDto;
import com.bikkadit.electronic.store.dtos.ProductDto;
import com.bikkadit.electronic.store.helper.ApiResponse;
import com.bikkadit.electronic.store.helper.AppConstants;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.helper.UrlConstants;
import com.bikkadit.electronic.store.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(UrlConstants.BASE_URL + UrlConstants.PRODUCT_URL)
public class ProductController {

    @Autowired
    private ProductService productService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    /**
     * @author  Abhijit Chandsare
     * @apiNote Create Product
     * @param   productDto
     * @return  savedProductDto
     * @since   1.0v
     */
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        logger.info("Entering request for create product data");
        ProductDto savedProductDto = productService.createProduct(productDto);
        logger.info("Completed request for create product data");
        return new ResponseEntity<>(savedProductDto, HttpStatus.CREATED);
    }

    /**
     * @author  Abhijit Chandsare
     * @apiNote update Product
     * @param   productId
     * @param   productDto
     * @return  updatedProductDto
     * @throws  'ResourceNotFoundException'
     * @since   1.0v
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId, @RequestBody ProductDto productDto) {
        logger.info("Entering request for update product data with productId :{}", productId);
        ProductDto updatedProductDto = productService.updateProduct(productDto, productId);
        logger.info("Completed request for update product data with productId :{}", productId);
        return new ResponseEntity<>(updatedProductDto, HttpStatus.OK);
    }

    /**
     * @author   Abhijit Chandsare
     * @apiNote  delete Product
     * @param    productId
     * @throws   'ResourceNotFoundException'
     * @since    1.0v
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productId) {
        logger.info("Entering request for delete product data with productId :{}", productId);
        productService.deleteProductById(productId);
        ApiResponse responseMessage = ApiResponse.builder().message(AppConstants.RESOURCE_DELETED).success(true).build();
        logger.info("Completed request for delete product data with productId :{}", productId);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    /**
     * @author  Abhijit Chandsare
     * @apiNote get Product by Id
     * @param   productId
     * @return  productDto
     * @throws  'ResourceNotFoundException'
     * @since    1.0v
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String productId) {
        logger.info("Entering request for get product data with productId :{}", productId);
        ProductDto productDto = productService.getProductById(productId);
        logger.info("Completed request for get product data with productId :{}", productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    /**
     * @author  Abhijit Chandsare
     * @apiNote get all Products
     * @param   pageNumber
     * @param   pageSize
     * @param   sortBy
     * @param   sortDir
     * @return  pageableResponse
     * @since    1.0v
     */
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAllProducts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    ) {
        logger.info("Entering request for get all product data");
        PageableResponse<ProductDto> pageableResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request for get all product data");
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    /**
     * @author  Abhijit Chandsare
     * @apiNote get all live Products
     * @param   pageNumber
     * @param   pageSize
     * @param   sortBy
     * @param   sortDir
     * @return  pageableResponse
     * @since    1.0v
     */
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProducts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    ) {
        logger.info("Entering request for get all live product data");
        PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request for get all live product data");
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    /**
     * @author  Abhijit Chandsare
     * @apiNote search Product by title
     * @param   pageNumber
     * @param   pageSize
     * @param   sortBy
     * @param   sortDir
     * @return  pageableResponse
     * @since    1.0v
     */
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProductByTitle(
            @PathVariable String keywords,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        logger.info("Entering request for get all product data with title keywords :{}",keywords);
        PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(keywords, pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request for get all product data with title keywords :{}",keywords);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

}
