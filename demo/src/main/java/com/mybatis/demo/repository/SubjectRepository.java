package com.mybatis.demo.repository;

import com.mybatis.demo.document.Subject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends MongoRepository<Subject,String> {

    Subject findBySubjectCode(String code);
}
