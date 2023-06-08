package com.shruteekatech.electronicstore.service.impl;

import com.shruteekatech.electronicstore.dtos.UserDto;
import com.shruteekatech.electronicstore.entities.User;
import com.shruteekatech.electronicstore.exceptions.ResourceNotFoundException;
import com.shruteekatech.electronicstore.helper.AppConstants;
import com.shruteekatech.electronicstore.repositories.UserRepository;
import com.shruteekatech.electronicstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        //generating random id
        userDto.setId(UUID.randomUUID().toString());
        log.info("create user request is passed to database:");
        User user = this.modelMapper.map(userDto, User.class);
        User savedUser = this.userRepository.save(user);
        log.info("user registered in database successfully ");

        return this.modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public void deleteUser(String userId) {
        log.info("request delete user is passed to database , userId:{}", userId);
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.UNF_ID + userId));

        this.userRepository.delete(user);
        log.info("user deleted from database , userId:{}", userId);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        log.info(" request update user is passed to database with userId:{}", userId);
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.UNF_ID + userId));

        user.setAbout(userDto.getAbout());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setImage(userDto.getImage());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());

        log.info("user updated in database successfully , userId:{}", userId);

        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserById(String userId) {
        log.info("Starting request to get single user from database with userId:{}", userId);
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.UNF_ID + userId));
        log.info("Completed request to get user with userId:{}", userId);
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String userEmail) {
        log.info("Starting request to get single user from database with Email:{}", userEmail);
        User user = this.userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.UNF_EMAIL + userEmail));
        log.info("Completed request to get user with userEmail:{}", userEmail);
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUser() {
        log.info("Starting request to get all users from database");
        List<User> users = this.userRepository.findAll();
        log.info("Completed request to get all users list");
        return users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).toList();
    }

    @Override
    public List<UserDto> getUserByName(String keyword) {
        log.info("Starting request to get all users from database having name {}:", keyword);
        List<User> users = this.userRepository.findAllByNameContaining(keyword);
        log.info("Completed request to get all users list with name:");

        return users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).toList();
    }

}
