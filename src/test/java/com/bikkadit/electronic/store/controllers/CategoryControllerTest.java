package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.dtos.CategoryDto;
import com.bikkadit.electronic.store.entities.Category;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.services.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {
    @MockBean
    private CategoryService categoryService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ModelMapper mapper;

    private Category category;

    @BeforeEach
    public void init() {
        category = Category
                .builder()
                .title("Computer and Accessories")
                .description("All Brands Computer and Accessories Available")
                .coverImage("comp.png")
                .build();
    }

    @Test
    public void createCategoryTest() throws Exception {
        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.createCategory(Mockito.any())).thenReturn(categoryDto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/categories/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(category))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }

    private String convertObjectToJsonString(Object user) throws JsonProcessingException {
        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void updateCategoryTest() throws Exception {
        String categoryId = "cateAbc";
        CategoryDto dto = mapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.updateCategory(Mockito.any(), Mockito.anyString())).thenReturn(dto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/categories/" + categoryId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(category))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    public void getAllCategoryTest() throws Exception {
        CategoryDto categoryDto = CategoryDto.builder().title("Computer and Accessories").description("All Brands Computer and Accessories available").coverImage("comp.png").build();
        CategoryDto categoryDto1 = CategoryDto.builder().title("Mobile and Accessories").description("All Brands Mobile and Accessories available").coverImage("mob.png").build();
        CategoryDto categoryDto2 = CategoryDto.builder().title("Earphones").description("All brand, All types Earphones Available").coverImage("earph.png").build();

        PageableResponse<CategoryDto> pagResponse = new PageableResponse<>();
        pagResponse.setContent(Arrays.asList(categoryDto, categoryDto1, categoryDto2));
        pagResponse.setLastPage(false);
        pagResponse.setPageNumber(100);
        pagResponse.setPageSize(10);
        pagResponse.setTotalElements(1000);

        Mockito.when(categoryService.getAllCategories(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pagResponse);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void getCategoryByIdTest() throws Exception {

        String categoryId="catTest";
        CategoryDto dto = mapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.getCategoryById(categoryId)).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/categories/" + categoryId))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void deleteCategoryTest() throws Exception {
        String categoryId="catTest";
        Mockito.doNothing().when(categoryService).deleteCategory(categoryId);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/categories/" + categoryId))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
