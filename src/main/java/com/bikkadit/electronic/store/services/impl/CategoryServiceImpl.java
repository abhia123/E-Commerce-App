package com.bikkadit.electronic.store.services.impl;

import com.bikkadit.electronic.store.dtos.CategoryDto;
import com.bikkadit.electronic.store.entities.Category;
import com.bikkadit.electronic.store.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.AppConstants;
import com.bikkadit.electronic.store.helper.PageableHelper;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.repositories.CategoryRepository;
import com.bikkadit.electronic.store.services.CategoryService;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        logger.info("Initiating DAO call for create category data");
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category saveCat = this.categoryRepository.save(category);
        logger.info("Completed DAO call for create category data");
        return this.modelMapper.map(saveCat, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {

        logger.info("Initiating DAO call for update category data with category id :{}",categoryId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + "id" + categoryId));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category saveCat = this.categoryRepository.save(category);
        logger.info("Completed DAO call for update category data with category id:{}",categoryId);
        return this.modelMapper.map(saveCat, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
        logger.info("Initiating DAO call for get category by id :{}",categoryId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + categoryId));
        logger.info("Completed DAO call for get category by id :{}",categoryId);
        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategories(Integer pageNum, Integer pageSize,String sortBy,String sortDir) {
        logger.info("Initiating DAO call for get all categories by pagination and sorting");
        Sort sort = sortDir.equalsIgnoreCase("dsc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Category> page = this.categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> response = PageableHelper.getPageableResponse(page, CategoryDto.class);
        logger.info("Completed DAO call for get all categories by pagination and sorting");
        return response;
    }

    @Override
    public void deleteCategory(String categoryId) {
        logger.info("Initiating DAO call for delete the category with category id:{}",categoryId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + categoryId));
        logger.info("Completed DAO call for delete the category with category id:{}",categoryId);
        this.categoryRepository.delete(category);
    }
}
