package com.bikkadit.electronic.store.services.impl;

import com.bikkadit.electronic.store.dtos.UserDto;
import com.bikkadit.electronic.store.entities.User;
import com.bikkadit.electronic.store.exceptions.ResourceNotFondException;
import com.bikkadit.electronic.store.helper.AppConstants;
import com.bikkadit.electronic.store.helper.PageableHelper;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.repositories.UserRepository;
import com.bikkadit.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDto createUser(UserDto userDto) {
        logger.info("Initiating dao request for creating user record");
        String userId = UUID.randomUUID().toString();  //generating unique userId in String format
        userDto.setUserId(userId);
        User user = mapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);
        UserDto convertedUserDto = mapper.map(savedUser, UserDto.class);
        logger.info("Completed dao request for creating user record");
        return convertedUserDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        logger.info("Initiating dao request for updating user record with user id {}:", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFondException(AppConstants.NOT_FOUND + userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setImageName(userDto.getImageName());
        User updatedUser = userRepository.save(user);
        UserDto updatedUserDto = mapper.map(updatedUser, UserDto.class);
        logger.info("Completed dao request for updating user record with user id {}:", userId);
        return updatedUserDto;
    }

    @Override
    public void deleteUser(String userId) {
        logger.info("Initiating dao request for deleting user record with user id {}:", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFondException(AppConstants.NOT_FOUND + userId));
        userRepository.delete(user);
        logger.info("Completed dao request for deleting user record with user id {}:", userId);
    }

    @Override
    public PageableResponse<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        logger.info("Initiating dao request for getting all user records");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<User> userPage = userRepository.findAll(pageable);
        List<User> allUsers = userPage.getContent();
        List<UserDto> allDtos = allUsers.stream().map(user -> mapper.map(user, UserDto.class)).collect(Collectors.toList());

        PageableResponse<UserDto> response = PageableHelper.getPageableResponse(userPage, UserDto.class);
        logger.info("Completed dao request for getting all user records");
        return response;
    }

    @Override
    public UserDto getUserById(String userId) {
        logger.info("Initiating dao request for getting user record with userId {}:", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFondException(AppConstants.NOT_FOUND + userId));
        UserDto userDto = mapper.map(user, UserDto.class);
        logger.info("Completed dao request for getting user record with user id {}:", userId);
        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Initiating dao request for getting user record with user email {}:", email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFondException(AppConstants.NOT_FOUND_EMAIL + email));
        UserDto userDto = mapper.map(user, UserDto.class);
        logger.info("Completed dao request for getting user record with user email {}:", email);
        return userDto;
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        logger.info("Initiating dao request for getting user record with search keywords {}:", keyword);
        List<User> allUsers = userRepository.findByNameContaining(keyword);
        List<UserDto> allUserDtos = allUsers.stream().map(user -> mapper.map(user, UserDto.class)).collect(Collectors.toList());
        logger.info("Completed dao request for getting user record with search keywords {}:", keyword);
        return allUserDtos;
    }
}
