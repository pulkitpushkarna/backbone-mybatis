package com.mybatis.demo.controller;

import com.mybatis.demo.document.Student;
import com.mybatis.demo.dto.AverageDTO;
import com.mybatis.demo.dto.FilterDTO;
import com.mybatis.demo.dto.ResponseDTO;
import com.mybatis.demo.dto.StudenttDTO;
import com.mybatis.demo.repository.StudentRepository;
import com.mybatis.demo.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    DocumentService documentService;

    @PostMapping("/")
    String addStudent(@RequestBody Student student){
        studentRepository.insert(student);
        return "saved successfully";

    }
    @GetMapping("/{standard}")
    List<Student> getStudentByClassName(@PathVariable String standard){
        return documentService.getStudentByClassName(standard);
    }

    @GetMapping("/aggregation/{standard}")
    List<StudenttDTO> aggregationTest(@PathVariable String standard){
        return documentService.aggregationExample(standard);
    }

    @PostMapping("/average")
    ResponseDTO<List<AverageDTO>> getAverageMarks(@RequestBody FilterDTO filterDTO){
        return documentService.getAverageMarks(filterDTO);

    }
}


