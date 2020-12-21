package com.mybatis.demo.repository;

import com.mybatis.demo.document.Standard;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardRepository extends MongoRepository<Standard,String> {
    Standard findByName(String rollNo);
}
