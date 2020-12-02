package com.mybatis.demo.controller;

import com.mybatis.demo.dto.FacultyResponseDTO;
import com.mybatis.demo.dto.ResponseDTO;
import com.mybatis.demo.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    @Autowired
    FacultyService facultyService;


    @GetMapping("/info/{facultyCode}")
    ResponseDTO<List<FacultyResponseDTO>> getFacultyInfo(@PathVariable String facultyCode){
        return facultyService.getFacultyInformation(facultyCode);

    }

}
