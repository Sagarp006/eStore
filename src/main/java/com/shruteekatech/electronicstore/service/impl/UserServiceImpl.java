package com.shruteekatech.electronicstore.service.impl;

import com.shruteekatech.electronicstore.dtos.UserDto;
import com.shruteekatech.electronicstore.entities.User;
import com.shruteekatech.electronicstore.exceptions.ResourceNotFoundException;
import com.shruteekatech.electronicstore.helper.AppConstants;
import com.shruteekatech.electronicstore.helper.PageableResponse;
import com.shruteekatech.electronicstore.repositories.UserRepository;
import com.shruteekatech.electronicstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
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
    public PageableResponse<UserDto>
    getAllUsers(Integer pageNo, Integer pageSize, String sortDi, String sortBy) {
        log.info("Starting request to get all users from database");
        Sort sort = (sortDi.equalsIgnoreCase("desc")) ?
                (Sort.by(sortDi).descending()) : (Sort.by(sortDi).ascending());
        PageRequest page = PageRequest.of(pageNo, pageSize, sort);
        Page<User> users = this.userRepository.findAll(page);
        List<UserDto> list = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).toList();
        PageableResponse<UserDto> response = new PageableResponse<>();//PageResponse object
        response.setPageNo(users.getNumber());
        response.setPageSize(users.getSize());
        response.setContent(list);
        response.setTotalPages(users.getTotalPages());
        response.setTotalElement(users.getTotalElements());
        response.setLastPage(users.isLast());
//        PageableResponse response1 = PageableResponse.builder().content(Collections.singletonList(list)).pageNo(users.getNumber())
//                .pageSize(users.getSize())
//                .totalPages(users.getTotalPages())
//                .totalElement(users.getTotalElements())
//                .lastPage(users.isLast()).build();
        return response;
    }

    @Override
    public List<UserDto> getAllUser(Integer pageNo, Integer pageSize, String sortDi, String sortBy) {
        log.info("Starting request to get all users from database");
        Sort sort = (sortDi.equalsIgnoreCase("desc")) ?
                (Sort.by(sortDi).descending()) : (Sort.by(sortDi).ascending());
        PageRequest page = PageRequest.of(pageNo, pageSize, sort);
        Page<User> users = this.userRepository.findAll(page);
        List<UserDto> list = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).toList();

        return list;
    }

    @Override
    public List<UserDto> getUserByName(String keyword) {
        log.info("Starting request to get all users from database having name {}:", keyword);
        List<User> users = this.userRepository.findByNameContaining(keyword);
        log.info("Completed request to get all users list with name:");

        return users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).toList();
    }

}
