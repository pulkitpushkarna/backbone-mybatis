package com.exampledemo.awsmysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    Repository repository;

    @GetMapping("/users")
    List<Entity> getAllUsers(){
        List<Entity> all = (List<Entity>) repository.findAll();
        return all;

    }
}
