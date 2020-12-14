package com.mybatis.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatis.demo.entity.User;

import java.io.Serializable;


public class ResponseDTO<T> implements Serializable {

    private Boolean status = true;

    private String code;

    private String message;

    @JsonIgnore
    private String loginedUser;

    @JsonIgnore
    private String userRole;

    private T data;

    public ResponseDTO(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }


    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getLoginedUser() {
        return loginedUser;
    }

    public void setLoginedUser(String loginedUser) {
        this.loginedUser = loginedUser;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
