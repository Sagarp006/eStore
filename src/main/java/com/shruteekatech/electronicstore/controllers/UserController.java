package com.shruteekatech.electronicstore.controllers;

import com.shruteekatech.electronicstore.dtos.UserDto;
import com.shruteekatech.electronicstore.helper.ApiResponse;
import com.shruteekatech.electronicstore.helper.AppConstants;
import com.shruteekatech.electronicstore.helper.PageableResponse;
import com.shruteekatech.electronicstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The UserController class handles User-related operations .
 * It provides methods to create new users,delete user, get existing users, and update user .
 *
 * @author sagar padwekar
 * @version 1.8
 * @since 2023
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;


    /**
     * @param userDto The entity of the User.
     * @return created user info
     * @author sagar padwekar
     * @apiNote This Api Creates a new user with the provided username and password.
     */
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Starting request to create user");
        UserDto user = this.userService.createUser(userDto);
        log.info("user created successfully ");
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }


    /**
     * @param userDto body containing the updated user details.
     * @return The updated user info.
     * @author sagar padwekar
     * @apiNote This Api  Updates the profile of the user.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {
        log.info("Starting request to update user with userId:{}", userId);
        UserDto user = this.userService.updateUser(userDto, userId);
        log.info("Completed request to update user with userId:{}", userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    /**
     * @param userId, The existing user_id of the User
     * @return found user info if the user is found ,
     * @author sagar padwekar
     * @apiNote This Api Gets an existing user with the provided userId .
     * throws Exception if user with provided id is not available
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        log.info("Starting request to get single user with userId:{}", userId);
        UserDto user = this.userService.getUserById(userId);
        log.info("Completed request to get user with userId:{}", userId);
        return new ResponseEntity<>(user, HttpStatus.FOUND);
    }

    /**
     * @param email The user_email of the User
     * @return found user info if the user is found ,
     * throws Exception if user with provided id is not available
     * @author sagar padwekar
     * @apiNote This Api Gets an existing user with the provided userId .
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        log.info("Starting request to get single user with Email:{}", email);
        UserDto user = this.userService.getUserByEmail(email);
        log.info("Completed request to get user with userEmail:{}", email);
        return new ResponseEntity<>(user, HttpStatus.FOUND);
    }

    /**
     * @return the List of Users
     * @author sagar padwekar
     * @apiNote This Api Gets an all users available.
     */
    @GetMapping("/")
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam(value = "pageNo",required = false,defaultValue = "0") Integer pageNo,
            @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize,
            @RequestParam(value = "sortDi",required = false,defaultValue = "asc") String sortDi,  //sorting direction
            @RequestParam(value = "sortBy",required = false,defaultValue = "name") String sortBy   //sort using name,email,id,etc.
    ) {
        log.info("Starting request to get all users");
        PageableResponse<UserDto> users = this.userService.getAllUsers(pageNo, pageSize, sortDi, sortBy);
        log.info("Completed request to get all users list");
        return new ResponseEntity<>(users,HttpStatus.FOUND);
    }

    /**
     * @return the List of Users whose name is @param name
     * @author sagar padwekar
     * @apiNote This Api Gets an all users available whose name is provided.
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserDto>> getUserContainingName(@PathVariable String name) {
        log.info("Starting request to get all users containing name:{}", name);
        List<UserDto> users = this.userService.getUserByName(name);
        log.info("Completed request to get all users list with name:{}", name);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    /**
     * @param userId , to find the user
     * @author sagar padwekar
     * @apiNote This Api is to delete user with the provided userId .
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        log.info("Starting request to delete user with userId:{}", userId);
        this.userService.deleteUser(userId);
        log.info("Completed request to delete user with userId:{}", userId);
        return new ResponseEntity<>(ApiResponse.builder().message(AppConstants.USER_DEL)
                .status(HttpStatus.OK).success(true).build(), HttpStatus.OK);
    }

}
