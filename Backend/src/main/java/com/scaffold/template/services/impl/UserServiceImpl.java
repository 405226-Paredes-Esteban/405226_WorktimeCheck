package com.scaffold.template.services.impl;

import com.scaffold.template.dtos.UserInfoDto;
import com.scaffold.template.entities.UserEntity;
import com.scaffold.template.repositories.UserRepository;
import com.scaffold.template.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserEntity createUser(UserEntity user) {
        user.setUserAud(1L);
        user.setUserState(1L);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRole("ADMIN");
        return userRepository.save(user);
    }

    @Override
    public Boolean isUserEnabled(String userName) {
        UserEntity user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getUserState() != 0L;
    }


    @Override
    public Boolean deleteUser(String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setUserState(0L);
        userRepository.save(user);
        return true;
    }

    @Override
    public Page<UserInfoDto> getUserInfoPaged(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page,size);

        Page<UserEntity> userPage;

        if (search == null || search.isBlank()){
            userPage = userRepository.findAll(pageable);
        } else {
            userPage = userRepository.searchByEmail(search.toLowerCase(), pageable);
        }

        return userPage.map(userEntity -> modelMapper.map(userEntity, UserInfoDto.class));
    }

}