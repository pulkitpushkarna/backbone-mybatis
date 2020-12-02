package com.mybatis.demo.controller;

import com.mybatis.demo.dto.ResponseDTO;
import com.mybatis.demo.dto.UserRequestDTO;
import com.mybatis.demo.dto.UserResponseDTO;
import com.mybatis.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class AdminController {

    @Autowired
    UserService userService;

    @PostMapping("/")
    ResponseDTO createUser(@RequestBody UserRequestDTO userRequestDTO){
        return userService.createUser(userRequestDTO);

    }

    @GetMapping("/{userName}")
    ResponseDTO<UserResponseDTO> getUser(@PathVariable String userName){
        return userService.getUserByUserName(userName);
    }


}
