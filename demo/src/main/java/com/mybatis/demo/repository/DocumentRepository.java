package com.mybatis.demo.repository;

import com.mybatis.demo.document.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DocumentRepository extends MongoRepository<Student,String> {
    @Query(value = "{'standard.name':?0}",fields = "{'subjects':1,'firstName':1}")
    List<Student> findByStandard(String standard);
}
