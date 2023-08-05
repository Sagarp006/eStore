package com.shruteekatech.electronicstore.controllers;

import com.shruteekatech.electronicstore.dtos.UserDto;
import com.shruteekatech.electronicstore.exceptions.ResourceNotFoundException;
import com.shruteekatech.electronicstore.helper.ApiResponse;
import com.shruteekatech.electronicstore.helper.AppConstants;
import com.shruteekatech.electronicstore.helper.ImageResponse;
import com.shruteekatech.electronicstore.util.Pagination;
import com.shruteekatech.electronicstore.service.FileService;
import com.shruteekatech.electronicstore.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
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
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final FileService fileService;

    @Value("${image-path-user}")
    private String imageUploadPath;

    /**
     * @param userDto The entity of the User.
     * @return created user info
     * @author sagar padwekar
     * @author sagar padwekar
     * @apiNote This Api Creates a new user with the provided username and password.
     */
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Starting request to create user");
        UserDto user = this.userService.createUser(userDto);
        log.info("user created successfully ");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
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
    public ResponseEntity<Pagination<UserDto>> getAllUser(
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(value = "sortDi", required = false, defaultValue = "asc") String sortDi,  //sorting direction
            @RequestParam(value = "sortBy", required = false, defaultValue = "name") String sortBy   //sort using name,email,id,etc.
    ) {
        log.info("Starting request to get all users");
        Pagination<UserDto> users = this.userService.getAllUsers(pageNo, pageSize, sortDi, sortBy);
        log.info("Completed request to get all users list");
        return new ResponseEntity<>(users, HttpStatus.FOUND);
    }

    /**
     * @return the List of Users whose name is @param name
     * @author sagar padwekar
     * @apiNote This Api Gets an all users available whose name is provided.
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable("keyword") String keyword) {
        log.info("Initiated request for search user details with keyword:{}", keyword);
        List<UserDto> user = this.userService.searchUser(keyword);
        log.info("Completed request for search user details with keyword:{}", keyword);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    /**
     * @param userId , to find the user
     * @author sagar padwekar
     * @apiNote This Api is to delete user with the provided userId .
     */
    @DeleteMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        log.info("Starting request to delete user with userId:{}", userId);
        this.userService.deleteUser(userId);
        log.info("Completed request to delete user with userId:{}", userId);
        ApiResponse apiResponse = ApiResponse.builder().message(AppConstants.USER_DEL)
                .status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * @param userId    , to find the user
     * @param userImage ,contains image file with multiple info
     * @author sagar padwekar
     * @apiNote This Api is to upload image to user profile with the provided userId .
     */
    @PostMapping("/uploadImage/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(
            @RequestParam(value = "userImage") MultipartFile userImage, @PathVariable String userId) {
        log.info("image Upload process is being Started");
        try {
            String imageName = this.fileService.uploadFile(userImage, imageUploadPath);
            UserDto user = this.userService.getUserById(userId);
            user.setImage(imageName);
            this.userService.updateUser(user, userId);
            ImageResponse imageResponse = ImageResponse.of(imageName, userId);
            log.info("Image is Uploaded");
            return new ResponseEntity<>(imageResponse, HttpStatus.OK);
        } catch (IOException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        log.info("Initiated request for serve image details with  userId:{}", userId);
        UserDto user = this.userService.getUserById(userId);
        log.info("user image name:{}", user.getImage());
        InputStream resource = this.fileService.getResource(imageUploadPath, user.getImage());
        resource.close();
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        log.info("Completed request for serve image details with  userId:{}", userId);
    }
}

