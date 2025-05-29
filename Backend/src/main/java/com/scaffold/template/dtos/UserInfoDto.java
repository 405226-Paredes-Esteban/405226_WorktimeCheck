package com.scaffold.template.dtos;

import lombok.Data;

@Data
public class UserInfoDto {
    private String userName;
    private String email;
    private Long userState;
    private String userRole;
}
