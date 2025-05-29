package com.scaffold.template.services;

import com.scaffold.template.dtos.UserDto;
import com.scaffold.template.dtos.UserInfoDto;
import com.scaffold.template.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public UserEntity createUser(UserEntity user);
    public Boolean isUserEnabled(String userName);
    public Boolean deleteUser(String userEmail);
    public Page<UserInfoDto> getUserInfoPaged(int page, int size, String search);
}
