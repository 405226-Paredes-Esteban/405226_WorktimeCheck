package com.scaffold.template.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
    @NotNull
    private String userName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;
}
