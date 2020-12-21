package com.mybatis.demo.controller;

import com.mybatis.demo.dto.ResponseDTO;
import com.mybatis.demo.dto.SubjectRequestDTO;
import com.mybatis.demo.dto.SubjectResponseDTO;
import com.mybatis.demo.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @PostMapping("/")
    ResponseDTO addSubject(@RequestBody SubjectRequestDTO subjectRequestDTO){
        return subjectService.addSubject(subjectRequestDTO);
    }

    @GetMapping("/")
    ResponseDTO<List<SubjectResponseDTO>> getAllSubjects(){
        return subjectService.getAllSubjects();
    }

    @GetMapping("/nameList")
    ResponseDTO<List<SubjectResponseDTO>> getNameList(){
        return subjectService.getNameList();
    }
}
