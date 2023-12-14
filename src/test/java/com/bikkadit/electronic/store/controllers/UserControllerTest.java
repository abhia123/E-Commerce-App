package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.dtos.UserDto;
import com.bikkadit.electronic.store.entities.User;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.ModelMap;

import java.util.Arrays;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    private User user;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {

        user = User.builder()
                .name("Abhijeet")
                .email("apc@gmail.com")
                .gender("male")
                .about("Software developer")
                .imageName("apc.png")
                .password("Apcd@121")
                .build();
    }

    @Test
    public void createUserTest() throws Exception {

        UserDto userDto = mapper.map(user, UserDto.class);

        Mockito.when(userService.createUser(Mockito.any())).thenReturn(userDto);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());
    }


    @Test
    public void updateUserTest() throws Exception {

        String userId = "user123";

        UserDto userDto = this.mapper.map(user, UserDto.class);

        Mockito.when(userService.updateUser(Mockito.any(), Mockito.anyString())).thenReturn(userDto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/users/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

    private String convertObjectToJsonString(Object user) {
        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void getAllUsersTest() throws Exception {

        UserDto dtoObject1 = UserDto.builder()
                .name("Abhijeet Chandsare")
                .email("apc@gmail.com")
                .gender("male")
                .about("Software developer")
                .imageName("abhi.png")
                .password("apc1@123")
                .build();
        UserDto dtoObject2 = UserDto.builder()
                .name("ramesh")
                .email("ramesh@gmail.com")
                .gender("male")
                .about("software developer")
                .imageName("ramesh.png")
                .password("ramesh@123")
                .build();
        UserDto dtoObject3 = UserDto.builder()
                .name("kunal")
                .email("kc@gmail.com")
                .gender("male")
                .about("software developer")
                .imageName("abc.png")
                .password("abc")
                .build();
        UserDto dtoObject4 = UserDto.builder()
                .name("Rahul Dravid")
                .email("rd@gmail.com")
                .gender("male")
                .about("java developer")
                .imageName("abc.png")
                .password("abcd@1234")
                .build();

        PageableResponse<UserDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(
                dtoObject1, dtoObject2, dtoObject3, dtoObject4
        ));
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);
        Mockito.when(userService.getAllUsers(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByIdTest() throws Exception {
        String userId = "userIdTest";
        UserDto dto = mapper.map(user, UserDto.class);
        Mockito.when(userService.getUserById(Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users/" + userId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserTest() throws Exception {

        String userId = "userDeleteTest";
        Mockito.doNothing().when(userService).deleteUser(userId);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/users/" + userId))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void getUserByEmailTest() throws Exception {

        String email = "apc@gmail.com";
        UserDto dto = mapper.map(user, UserDto.class);
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/users/email/" + email))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void searchUserTest() throws Exception {

        UserDto user = UserDto.builder()
                .name("ramesh phadatare")
                .email("rp@gmail.com")
                .gender("male")
                .about("java developer")
                .imageName("abc.png")
                .password("abc")
                .build();

        UserDto user1 = UserDto.builder()
                .name("santosh bikkad")
                .email("Pb@gmail.com")
                .gender("male")
                .about("java developer")
                .imageName("abc.png")
                .password("abc")
                .build();

        UserDto user2 = UserDto.builder()
                .name("Rahul Dravid")
                .email("rd@gmail.com")
                .gender("male")
                .about("java developer")
                .imageName("abc.png")
                .password("abc")
                .build();

        String keyword = "ramesh";

        Mockito.when(userService.searchUser(Mockito.anyString())).thenReturn(Arrays.asList(user, user1, user2));

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/users/search/" + keyword))
                .andDo(print())
                .andExpect(status().isOk());
    }
}