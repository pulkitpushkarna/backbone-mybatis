package com.mybatis.demo.service.impl;

import com.mybatis.demo.dto.ResponseDTO;
import com.mybatis.demo.dto.UserRequestDTO;
import com.mybatis.demo.dto.UserResponseDTO;
import com.mybatis.demo.entity.User;
import com.mybatis.demo.repository.UserMapper;
import com.mybatis.demo.security.AppUser;
import com.mybatis.demo.service.UserService;
import com.mybatis.demo.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public ResponseDTO createUser(UserRequestDTO userRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO(false, Constant.REQUEST_NOT_PROCESSED);

        validateUserRequestDTO(responseDTO, userRequestDTO);
        if (!responseDTO.getStatus()) {
            return responseDTO;
        }
        User checkuser = userMapper.findByUserName(userRequestDTO.getUserName());
        if (checkuser != null) {
            responseDTO.setCode(Constant.BAD_REQUEST);
            responseDTO.setStatus(Boolean.FALSE);
            responseDTO.setMessage("User already present");
            log.info("user already present");

        } else {
            User user = new User();
            user.setUserName(userRequestDTO.getUserName());
            user.setPassword(userRequestDTO.getPassword());
            user.setConfirmPassword(userRequestDTO.getConfirmPassword());
            user.setRoles(userRequestDTO.getRoles());
            user.setActive(true);
            userMapper.save(user);
            log.info("User saved ");
            responseDTO.setStatus(Boolean.TRUE);
            responseDTO.setCode(Constant.OK);
            responseDTO.setMessage(Constant.REQUEST_SUCCESS);
        }
        return responseDTO;
    }

    private void validateUserRequestDTO(ResponseDTO responseDTO, UserRequestDTO userRequestDTO) {
        log.info("validating user : " + userRequestDTO.getUserName());

        if (userRequestDTO.getUserName() == null
                || userRequestDTO.getUserName().trim().equals("")) {
            responseDTO.setMessage("Username Cannot be Null");
            log.error("Username is null");
        } else if (userRequestDTO.getPassword() == null
                || userRequestDTO.getPassword().trim().equals("")) {
            responseDTO.setMessage("Password cannot be null");
            log.error("Password is null");

        } else if (userRequestDTO.getConfirmPassword() == null
                || userRequestDTO.getConfirmPassword().trim().equals("")) {
            responseDTO.setMessage("Confirm password cannot be null");
            log.error("Confirm password is null");

        } else if (!userRequestDTO.getPassword().equals(userRequestDTO.getConfirmPassword())) {
            responseDTO.setMessage("Confirm password didn't matched");
            log.error("Confirm password didn't matched");

        } else if (userRequestDTO.getRoles() == null
                || userRequestDTO.getRoles().trim().equals("")) {
            responseDTO.setMessage("Role Cannot be null");
            log.error("Role cannot be null ");

        } else {
            responseDTO.setStatus(Boolean.TRUE);
        }
    }

    @Override
    public ResponseDTO<UserResponseDTO> getUserByUserName(String userName) {
        ResponseDTO responseDTO = new ResponseDTO(false, Constant.REQUEST_NOT_PROCESSED);
        log.info("Getting user by user-name " + userName);
        User user = userMapper.findByUserName(userName);
        if (user != null) {
            responseDTO.setStatus(Boolean.TRUE);
            responseDTO.setCode(Constant.OK);
            responseDTO.setMessage(Constant.REQUEST_SUCCESS);
            responseDTO.setData(user);
        } else {
            log.info("User not found ");
            responseDTO.setStatus(Boolean.FALSE);
            responseDTO.setMessage(Constant.NO_DATA_FOUND);
        }
        return responseDTO;


    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser userDetail = (AppUser) authentication.getPrincipal();
        String username = userDetail.getUsername();
        return userMapper.findByUserName(username);
    }
}
