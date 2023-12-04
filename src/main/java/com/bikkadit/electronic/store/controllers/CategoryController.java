package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.dtos.CategoryDto;
import com.bikkadit.electronic.store.helper.*;
import com.bikkadit.electronic.store.services.CategoryService;
import com.bikkadit.electronic.store.services.FileService;
import com.bikkadit.electronic.store.services.impl.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(UrlConstants.BASE_URL+UrlConstants.CATEGORY_URL)
@Slf4j
public class CategoryController {

    @Autowired
    private FileService fileService;

    @Value("${category.profile.image.path}")
    private String path;

    @Autowired
    private CategoryService categoryService;

    private Logger logger = LoggerFactory.getLogger(CategoryController.class);

    /**
     *  @author  Abhijit Chandsare
     *  @apiNote Create Category Record
     *  @param   categoryDto
     *  @return  CategoryDto, HttpStatus.CREATED
     *  @since   1.0v
     */
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        logger.info("Entering request for create category data");
        CategoryDto category = this.categoryService.createCategory(categoryDto);
        logger.info("Completed request for create category data");
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    /**
     * @author  Abhijit Chandsare
     * @apiNote Update Category Record
     * @param   categoryDto
     * @param   categoryId
     * @return  CategoryDto, HttpStatus.OK
     * @since   1.0v
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {
        logger.info("Entering request for update the category data with category id :{}", categoryId);
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);
        logger.info("Completed request for update category with category id :{}", categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    /**
     * @author  Abhijit Chandsare
     * @apiNote Get Category Record By Id
     * @param   categoryId
     * @return  CategoryDto, HttpStatus.OK
     * @since   1.0v
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId) {
        logger.info("Entering request for get category by id :{}", categoryId);
        CategoryDto categoryById = this.categoryService.getCategoryById(categoryId);
        logger.info("Completed request for get category by id :{}", categoryId);
        return new ResponseEntity<>(categoryById, HttpStatus.OK);

    }


    /**
     * @author  Abhijit Chandsare
     * @apiNote Get All Category Records
     * @param   pageNumber
     * @param   pageSize
     * @param   sortBy
     * @param   sortDir
     * @return  PageableResponse<CategoryDto>, HttpStatus.OK
     * @since   1.0v
     */
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        logger.info("Entering request for get all categories by pagination and sorting");
        PageableResponse<CategoryDto> allCategories = this.categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed Request for get All categories By pagination And Sorting");
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }


    /**
     * @author  Abhijit Chandsare
     * @apiNote Delete Category Record
     * @param   categoryId
     * @return  ApiResponse, HttpStatus.OK
     * @since   1.0v
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId) {
        logger.info("Entering Request for delete the category with category id:{}", categoryId);
        this.categoryService.deleteCategory(categoryId);
        ApiResponse response = ApiResponse.builder().message(AppConstants.RESOURCE_DELETED).success(true).build();
        logger.info("Completed request for delete the category with category id :{}", categoryId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @author  Abhijit Chandsare
     * @apiNote Upload Cover Image
     * @param   image
     * @param   categoryId
     * @return  ImageResponse, HttpStatus.CREATED
     * @throws  IOException
     * @since   1.0v
     */
    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCoverImage(@RequestParam MultipartFile image, @PathVariable String categoryId) throws IOException {

        logger.info("Entering request for upload cover image with category id :{}", categoryId);
        String uploadFile = fileService.uploadImage(image, path);
        CategoryDto cat = categoryService.getCategoryById(categoryId);
        cat.setCoverImage(uploadFile);
        categoryService.updateCategory(cat, categoryId);
        ImageResponse imageUploaded = ImageResponse.builder().imageName(uploadFile).message("Image Uploaded").success(true).status(HttpStatus.CREATED).build();
        logger.info("Completed request for upload cover image with category id :{}", categoryId);
        return new ResponseEntity<>(imageUploaded, HttpStatus.CREATED);
    }


    /**
     * @author  Abhijit Chandsare
     * @apiNote Serve Cover Image
     * @param   categoryId
     * @param   response
     * @throws  IOException
     */
    @GetMapping("/image/{categoryId}")
    public void serveCoverImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        logger.info("Entering Request for get cover image with category id :{}", categoryId);
        CategoryDto category = categoryService.getCategoryById(categoryId);
        InputStream resource = fileService.getResource(path, category.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        logger.info("Completed Request for get cover image with category id :{}", categoryId);
    }
}
