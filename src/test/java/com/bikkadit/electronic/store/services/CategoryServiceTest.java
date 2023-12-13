package com.bikkadit.electronic.store.services;

import com.bikkadit.electronic.store.dtos.CategoryDto;
import com.bikkadit.electronic.store.entities.Category;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    private Category category;

    @BeforeEach
    public void init(){

        category=Category
                .builder()
                .title("Computer and Accessories")
                .description("All Brands Computer and Accessories Available")
                .coverImage("comp.png")
                .build();
    }

    @Test
    public void createCategoryTest(){

        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto category1 = categoryService.createCategory(mapper.map(category, CategoryDto.class));
        Assertions.assertNotNull(category1);
        Assertions.assertEquals("Computer and Accessories",category1.getTitle());

    }

    @Test
    public void updateCategoryTest(){

        String categoryId="";

        CategoryDto categoryDto=CategoryDto
                .builder()
                .title("Computer and Accessories")
                .description("All Brands Computer and Accessories Available")
                .coverImage("comp.png")
                .build();

        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);
        Assertions.assertNotNull(categoryDto);
    }

    @Test
    public void getAllCategoriesTest(){

        Category category1=Category
                .builder()
                .title("Computer and Accessories")
                .description("All Brands Computer and Accessories Available")
                .coverImage("comp.png")
                .build();
        Category category2=Category
                .builder()
                .title("Mobile phones")
                .description("All brands mobile phones")
                .coverImage("mobile.png")
                .build();

        List<Category> categories = Arrays.asList(category, category1, category2);
        Page<Category> page=new PageImpl<>(categories);
        Mockito.when(categoryRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<CategoryDto> allCategories = categoryService.getAllCategories(1, 2, "title", "asc");
        Assertions.assertEquals(3,allCategories.getContent().size());
    }

    @Test
    public void getCategoryById(){

        String categoryId="userIdTest";
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        Assertions.assertNotNull(categoryDto);
        Assertions.assertEquals(category.getTitle(),categoryDto.getTitle(),"title not matched");

    }

    @Test
    public void deleteCategoryTest(){

        String categoryId="userIdTest";

        Mockito.when(categoryRepository.findById("userIdTest")).thenReturn(Optional.of(category));
        categoryService.deleteCategory(categoryId);

    }


}
