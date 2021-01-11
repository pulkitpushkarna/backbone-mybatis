package com.mybatis.demo;

import com.mybatis.demo.service.impl.DocumentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);


    @Override
    public void run(ApplicationArguments args) throws Exception {

        jdbcTemplate.update("DROP TABLE IF EXISTS user ");
        jdbcTemplate.update("DROP TABLE IF EXISTS faculty ");
        jdbcTemplate.update("DROP TABLE IF EXISTS persistent_logins ");
        jdbcTemplate.update("DROP TABLE IF EXISTS student_class ");
        jdbcTemplate.update("DROP TABLE IF EXISTS student ");


        jdbcTemplate.update("create table user(" +
                "    id               int auto_increment" +
                "        primary key," +
                "    user_name        varchar(255) not null," +
                "    password         varchar(255) not null," +
                "    confirm_password varchar(255) not null," +
                "    roles            varchar(255) not null," +
                "    active           tinyint(1)   not null" +
                ")");

        jdbcTemplate.update("create table faculty(" +
                "    faculty_id   int auto_increment primary key," +
                "    name   varchar(255) not null," +
                "    class_name   varchar(255) null," +
                "    faculty_code varchar(255) null" +
                ")");

        jdbcTemplate.update("create table persistent_logins(" +
                "    username  varchar(64) not null," +
                "    series    varchar(64) not null primary key," +
                "    token     varchar(64) not null," +
                "    last_used timestamp   not null" +
                ")");
        jdbcTemplate.update("create table student(" +
                "    student_id int auto_increment primary key," +
                "    roll_no    varchar(225) not null," +
                "    first_name varchar(255) not null," +
                "    last_name  varchar(255) not null," +
                "    class_name varchar(255) not null," +
                "    address    varchar(255) not null," +
                "    city       varchar(255) not null," +
                "    deleted    tinyint(1)   not null" +
                ")");
        jdbcTemplate.update("create table student_class(" +
                "    id           int auto_increment primary key," +
                "    class_code   varchar(255) not null," +
                "    class_name   varchar(255) not null," +
                "    faculty_name varchar(255) not null" +
                ")");

        log.info("Database Table Created");
        jdbcTemplate.update("insert into user values (1,'admin','pass','pass','ROLE_ADMIN',1)");
        log.info("One sample user added");

    }

}

