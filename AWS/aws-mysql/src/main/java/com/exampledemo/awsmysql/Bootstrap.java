package com.exampledemo.awsmysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Bootstrap implements ApplicationRunner {
    @Autowired
    Repository repository;



    @Override
    public void run(ApplicationArguments args) throws Exception {
        Entity entity = new Entity();
        entity.setId("5");
        entity.setName("Kapil");
        entity.setAddress("Noida");
        repository.save(entity);


        System.out.println("hello world");


    }

}

