package com.bikkadit.electronic.store.services;

import com.bikkadit.electronic.store.dtos.UserDto;
import com.bikkadit.electronic.store.entities.User;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.repositories.UserRepository;

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
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;


    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    User user;

    @BeforeEach
    public void init(){
        user = User.builder()
                .name("Abhijeet")
                .email("apc@gmail.com")
                .gender("male")
                .about("Software developer")
                .imageName("apc.png")
                .password("apc")
                .build();
    }

    @Test
    public void createUser() {

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto user1 = userService.createUser(modelMapper.map(user, UserDto.class));
        System.out.println(user1.getName());
        Assertions.assertNotNull(user1);
        Assertions.assertEquals("Abhijeet",user1.getName());

    }

    @Test
    public void updateUserTest(){
        String userId="";

        UserDto userDto = UserDto.builder()
                .name("Abhijeet Chandsare")
                .email("apc@gmail.com")
                .gender("male")
                .about("Software developer")
                .imageName("abhi.png")
                .password("apc@123")
                .build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto updateUser = userService.updateUser(userDto, userId);
        System.out.println(updateUser.getName());
        Assertions.assertNotNull(userDto);
    }

    @Test
    public void deleteUserTest(){

        String userId="userTest";

        Mockito.when(userRepository.findById("userTest")).thenReturn(Optional.of(user));
        userService.deleteUser(userId);

    }
    @Test
    public void getAllUserTest(){

      User  user1 = User.builder()
                .name("ramesh")
                .email("ramesh@gmail.com")
                .gender("male")
                .about("software developer")
                .imageName("ramesh.png")
                .password("ramesh")
                .build();

      User user2 = User.builder()
                .name("kunal")
                .email("kc@gmail.com")
                .gender("male")
                .about("software developer")
                .imageName("abc.png")
                .password("abc")
                .build();

        List<User> userList = Arrays.asList(user,user1,user2);
        Page<User> page=new PageImpl<>(userList);
        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<UserDto> allUsers = userService.getAllUsers(1,2,"name","asc");
        Assertions.assertEquals(3,allUsers.getContent().size());

    }
    @Test
    public void getUserByIdTest(){

        String userId="userIdTest";
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUserById(userId);
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getName(),userDto.getName(),"name not matched");
    }
    @Test
    public void getUserByEmailTest(){

        String emailId="apc@gmail.com";
        Mockito.when(userRepository.findByEmail(emailId)).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUserByEmail(emailId);
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getEmail(),userDto.getEmail(),"email not matched!!");
    }

    @Test
    public void searchUserTest(){
       User user = User.builder()
                .name("ramesh phadatare")
                .email("rp@gmail.com")
                .gender("male")
                .about("java developer")
                .imageName("abc.png")
                .password("abc")
                .build();

       User user1 = User.builder()
                .name("santosh bikkad")
                .email("Pb@gmail.com")
                .gender("male")
                .about("java developer")
                .imageName("abc.png")
                .password("abc")
                .build();

       User user2 = User.builder()
                .name("Rahul Dravid")
                .email("rd@gmail.com")
                .gender("male")
                .about("java developer")
                .imageName("abc.png")
                .password("abc")
                .build();

        String keyword="prashant";
        Mockito.when(userRepository.findByNameContaining(keyword)).thenReturn(Arrays.asList(user,user1,user2));
        List<UserDto> userDtos = userService.searchUser(keyword);
        Assertions.assertEquals(3,userDtos.size(),"size not matched");
    }
}
