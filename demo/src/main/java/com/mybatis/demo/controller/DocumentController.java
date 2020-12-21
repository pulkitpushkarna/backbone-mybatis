package com.mybatis.demo.controller;

import com.mybatis.demo.document.Student;
import com.mybatis.demo.dto.*;
import com.mybatis.demo.repository.DocumentRepository;
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
    @Autowired
    DocumentRepository documentRepository;

    @GetMapping("/")
    ResponseDTO<List<DocumentResponseDTO>> getDocument(){
        return documentServiceImpl.getDocument();
    }

    @PostMapping("/")
   ResponseDTO saveDocument(@RequestBody DocumentRequestDTO documentRequestDTO){
        return documentServiceImpl.saveDocument(documentRequestDTO);

    }
    @GetMapping("/{standard}")
    List<Student> getStudentByClassName(@PathVariable String standard){
        return documentServiceImpl.getStudentByClassName(standard);
    }

    @PostMapping("/average")
    ResponseDTO<List<AverageDTO>> getAverageMarks(@RequestBody FilterDTO filterDTO){
        return documentServiceImpl.getAverageMarks(filterDTO);

    }
    @GetMapping("/student/{rollNo}")
    Student getByrollNo(@PathVariable String rollNo){
        return documentRepository.findByRollNo(rollNo);
    }
}


