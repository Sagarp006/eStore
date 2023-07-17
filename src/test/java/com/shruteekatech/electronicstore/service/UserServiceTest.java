package com.shruteekatech.electronicstore.service;

import com.shruteekatech.electronicstore.dtos.UserDto;
import com.shruteekatech.electronicstore.entities.User;
import com.shruteekatech.electronicstore.repositories.UserRepository;
import com.shruteekatech.electronicstore.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class UserServiceTest {
    @Autowired
    @InjectMocks
    private UserServiceImpl userService;
    @MockBean
    private UserRepository userRepository;
    private User user;
    @Autowired
    private ModelMapper mapper;
    @BeforeEach
    public void init() {
         user = User.builder()
                 .email("sagar@gmail.com")
                 .name("sagar")
                 .about("trainee")
                 .gender("male")
                 .image("7fn94.png")
                 .password("****")
                 .build();
    }

    @Test
    void createUserTest() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto createdUser = userService.createUser(mapper.map(user, UserDto.class));
        Assertions.assertNotNull(createdUser);
    }
}
