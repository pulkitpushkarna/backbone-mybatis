package com.mybatis.demo.controller;

import com.mybatis.demo.document.Student;
import com.mybatis.demo.dto.AverageDTO;
import com.mybatis.demo.dto.FilterDTO;
import com.mybatis.demo.dto.ResponseDTO;
import com.mybatis.demo.repository.StudentRepository;
import com.mybatis.demo.service.impl.DocumentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    DocumentServiceImpl documentServiceImpl;

    @PostMapping("/")
    String addStudent(@RequestBody Student student){
        studentRepository.insert(student);
        return "saved successfully";

    }
    @GetMapping("/{standard}")
    List<Student> getStudentByClassName(@PathVariable String standard){
        return documentServiceImpl.getStudentByClassName(standard);
    }

    @PostMapping("/average")
    ResponseDTO<List<AverageDTO>> getAverageMarks(@RequestBody FilterDTO filterDTO){
        return documentServiceImpl.getAverageMarks(filterDTO);

    }
}


