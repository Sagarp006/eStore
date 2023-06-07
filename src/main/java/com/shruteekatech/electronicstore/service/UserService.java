package com.shruteekatech.electronicstore.service;

import com.shruteekatech.electronicstore.dtos.UserDto;

import java.util.List;

public interface UserService {
    //create user
    UserDto createUser(UserDto userDto);

    //delete user
    void deleteUser(String userId);

    //update user
    UserDto updateUser(UserDto userDto,String userId);

    //get single user by id
    UserDto getUserById(String userId);

    //get single user by email
    UserDto getUserByEmail(String userEmail);

    //get all user
    List<UserDto> getAllUser();

    //search user by keyword
    List<UserDto> getUserByName(String keyword);
}
