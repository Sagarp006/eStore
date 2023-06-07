package com.shruteekatech.electronicstore.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.*;

@Entity
@Table(name = "users")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends SuperEntity {
    @Id
    private String id;
    @NotEmpty
    @Column(name = "user_name")
    private String name;
    @NotEmpty
    @Column(name = "user_password")
    private String password;
    @Email
    @Column(name = "user_email",unique = true)
    private String email;
    @NotEmpty
    @Column(name = "user_gender")
    private String gender;
    @Column(name = "user_about")
    private String about;
    @Column(name = "user_image")
    private String image;

}
