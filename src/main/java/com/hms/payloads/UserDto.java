package com.hms.payloads;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

@Data
public class UserDto {

    private Long id;
    private String name;

    @NotEmpty
    private String username;

    @Email
    private String email;

    private String password;

    private String role;


}
// iam using this