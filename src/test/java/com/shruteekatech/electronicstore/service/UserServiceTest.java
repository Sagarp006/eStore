package com.shruteekatech.electronicstore.service;

import com.shruteekatech.electronicstore.dtos.UserDto;
import com.shruteekatech.electronicstore.entities.User;
import com.shruteekatech.electronicstore.repositories.UserRepository;
import com.shruteekatech.electronicstore.service.impl.UserServiceImpl;
import com.shruteekatech.electronicstore.util.Pagination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void testCreateUser() {
        // Test data
        User user = User.builder()
                .name("sagar")
                .password("Pass123")
                .email("sagar@gmail.com")
                .gender("male")
                .about("Test user")
                .image("profile.jpg")
                .build();

        UserDto userDto = UserDto.builder()
                .name(user.getName())
                .password(user.getPassword())
                .email(user.getEmail())
                .gender(user.getGender())
                .about(user.getAbout())
                .image(user.getImage())
                .build();

        // Mocking behavior
        Mockito.when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(modelMapper.map(userDto, User.class)).thenReturn(user);

        // Call the method to be tested
        UserDto savedUser = userService.createUser(userDto);

        // Assertions
        assertNotNull(savedUser);
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getGender(), savedUser.getGender());
        assertEquals(user.getAbout(), savedUser.getAbout());
        assertEquals(user.getImage(), savedUser.getImage());

    }

    // @param String UserId , @returns nothing
    @Test
    void testDeleteUser() {
        String userId = UUID.randomUUID().toString();
        Mockito.when(userRepository.findById(userId))
                .thenReturn(Optional.of(Mockito.mock(User.class)));
        userService.deleteUser(userId);
        Mockito.verify(userRepository, Mockito.times(1))
                .delete(Mockito.any(User.class));
    }
    @Test
    void testUpdateUser() {
        String userId = "userid";

        // Mock the repository
        User user = User.builder()
                .id(userId)
                .name("Old Name")
                .about("Old About")
                .email("sagar1@gmail.com")
                .gender("male")
                .image("oldImage.png")
                .password("oldPassword")
                .build();

        // Test data
        UserDto userDto = UserDto.builder()
                .name("New Name")
                .about("New About")
                .email("sagar2@gmail.com")
                .gender("male")
                .image("newImage.png")
                .password("newPassword")
                .build();

        // Set up mock behavior
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(modelMapper.map(userDto, User.class)).thenReturn(user);

        // Call the method under test
        UserDto updateUser = userService.updateUser(userDto, userId);

        // Verify interactions
        Mockito.verify(modelMapper, Mockito.times(1)).map(userDto, user);
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);

        // Assertions
        assertNotNull(updateUser);
        assertEquals(user.getId(), updateUser.getId());
    }




    //@param int pageNo,pageSize;String sortOrder,sortUsing
    //@returns Pagination<User>
    @Test
    void testGetAllUser() {
        User user1 = User.builder().name("sagar").about("test case").email("sagar@gmail.com").gender("male").image("sagar.png").password("sagar123").build();
        User user2 = User.builder().name("sushil").about("test case").email("sushil@gmail.com").gender("male").image("sushil.png").password("sushil123").build();
        User user3 = User.builder().name("vicky").about("test case").email("vicky@gmail.com").gender("male").image("vicky.png").password("vicky123").build();
        List<User> users = List.of(user1, user2, user3);

        Page<User> page = new PageImpl<>(users);
        PageRequest pageable = PageRequest.of(0, 10, Sort.by("name"));
        Mockito.when(userRepository.findAll(pageable)).thenReturn(page);
        Pagination<UserDto> allUsers = userService.getAllUsers(0, 10, "asc", "name");
        Assertions.assertEquals(users.size(), allUsers.getContent().size());
        System.out.println(users);
    }


    @Test
    void testGetUserById() {
        String id = "userId";
        User user = User.builder()
                .name("sagar")
                .about("test case")
                .email("sagar@gmail.com")
                .gender("male")
                .image("sagar.png")
                .password("sagar123")
                .build();

        UserDto userDto = UserDto.builder()
                .name(user.getName())
                .about(user.getAbout())
                .email(user.getEmail())
                .gender(user.getGender())
                .image(user.getImage())
                .build();

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
        Mockito.when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        UserDto savedUser = userService.getUserById(id);

        assertNotNull(savedUser);
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getAbout(), savedUser.getAbout());
        assertEquals(user.getGender(), savedUser.getGender());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getImage(), savedUser.getImage());
    }

}
