package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.dtos.UserDto;
import com.bikkadit.electronic.store.helper.ApiResponse;
import com.bikkadit.electronic.store.helper.AppConstants;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.helper.UrlConstants;
import com.bikkadit.electronic.store.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(UrlConstants.BASE_URL+UrlConstants.USER_URL)
public class UserController {
    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);
    /***
     * @author  Abhijit Chandsare
     * @apiNote create user record
     * @param   userDto
     * @return  created UserDto, HttpStatus.CREATED
     * @since   1.0v
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        logger.info("Entering request for create new user record ");
        UserDto createdUserDto = userService.createUser(userDto);
        logger.info("Completed request for create new user record ");
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    /***
     * @author  Abhijit Chandsare
     * @apiNote update user record
     * @param   userId
     * @param   userDto
     * @return  updated UserDto, HttpStatus.OK
     * @throws  'Resource Not Found Exception'
     * @since   1.0v
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String userId,@Valid @RequestBody UserDto userDto){

        logger.info("Entering request for update user record with userId {}:", userId);
        UserDto updatedUser = userService.updateUser(userDto, userId);
        logger.info("Completed request for update user record with userId {}:", userId);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }

    /***
     * @author  Abhijit Chandsare
     * @apiNote delete user record
     * @param   userId
     * @return  ApiResponse(message,success), HttpStatus.OK
     * @throws  'Resource Not Found Exception'
     * @since   1.0v
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        logger.info("Entering request for delete user record with userId {}:", userId);
        userService.deleteUser(userId);
        ApiResponse response = ApiResponse.builder().message(AppConstants.USER_DELETED + userId).success(true).build();
        logger.info("Completed request for delete user record with userId {}:", userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***
     * @author  Abhijit Chandsare
     * @apiNote get all user record
     * @param   pageNumber
     * @param   pageSize
     * @param   sortBy
     * @param   sortDir
     * @return  List<UserDto>
     * @since   1.0v
     */
    @GetMapping
    public  ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ){
        logger.info("Entering request for get all user records");
        PageableResponse<UserDto> allUsers = userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request for get all user records");
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }


    /***
     * @author  Abhijit Chandsare
     * @apiNote get user record by userId
     * @param   userId
     * @return  UserDto, HttpStatus.OK
     * @throws  'Resource Not Found Exception'
     * @since   1.0v
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
        logger.info("Entering request for get user record with userid {}:", userId);
        UserDto userById = userService.getUserById(userId);
        logger.info("Completed request for get user record with userid {}:", userId);
        return new ResponseEntity<>(userById,HttpStatus.OK);
    }

    /***
     * @author  Abhijit Chandsare
     * @apiNote get user record by email
     * @param   email
     * @return  UserDto, HttpStatus.OK
     * @throws  'Resource Not Found Exception'
     * @since   1.0v
     */
    @GetMapping("email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        logger.info("Entering request for get user record with email {}:", email);
        UserDto userByEmail = userService.getUserByEmail(email);
        logger.info("Completed request for get user record with email {}:", email);
        return new ResponseEntity<>(userByEmail,HttpStatus.OK);
    }

    /***
     * @author  Abhijit Chandsare
     * @apiNote get user record by search keyword
     * @param   keywords
     * @return  UserDto, HttpStatus.OK
     * @since   1.0v
     */
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords){
        logger.info("Entering request for get user record with search keywords {}:", keywords);
        List<UserDto> searchUser = userService.searchUser(keywords);
        logger.info("Completed request for get user record with search keywords {}:", keywords);
        return new ResponseEntity<>(searchUser,HttpStatus.OK);
    }
}
