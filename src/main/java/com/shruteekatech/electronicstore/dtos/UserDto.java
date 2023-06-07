package com.shruteekatech.electronicstore.dtos;

import com.shruteekatech.electronicstore.helper.AppConstants;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends SuperEntityDto {
    private String id;
    @NotEmpty
    @Size(min = 5, max = 20, message = AppConstants.NAME_VLD)
    private String name;

    @Pattern(regexp = AppConstants.PASS_PATTERN)
    @NotEmpty
    @Size(min = 5, max = 20, message = AppConstants.PASS_VLD)
    private String password;
    @Email(message = AppConstants.EMAIL_VLD)
    private String email;
    @Pattern(regexp = "^(male|female)$", message = AppConstants.GENDER_MSG)
    private String gender;
    private String about;
    private String image;
}
