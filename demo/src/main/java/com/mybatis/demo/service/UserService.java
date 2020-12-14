package com.mybatis.demo.service;

import com.mybatis.demo.dto.ResponseDTO;
import com.mybatis.demo.dto.UserRequestDTO;
import com.mybatis.demo.dto.UserResponseDTO;
import org.springframework.stereotype.Service;

public interface UserService {
    ResponseDTO createUser(UserRequestDTO userRequestDTO);

    ResponseDTO<UserResponseDTO> getUserByUserName(String userName);
}
