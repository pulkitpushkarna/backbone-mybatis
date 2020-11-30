package com.mybatis.demo.repository;

import com.mybatis.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User findByUserName(String userName);
}
