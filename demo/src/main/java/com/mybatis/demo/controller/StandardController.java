package com.mybatis.demo.controller;

import com.mybatis.demo.document.Standard;
import com.mybatis.demo.document.Student;
import com.mybatis.demo.dto.ResponseDTO;
import com.mybatis.demo.dto.StandardResponseDTO;
import com.mybatis.demo.repository.StandardRepository;
import com.mybatis.demo.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/standard")
public class StandardController {

    @Autowired
    StandardService standardService;
    @Autowired
    StandardRepository standardRepository;

    @PostMapping("/")
    ResponseDTO addStandard(@RequestBody StandardResponseDTO standardResponseDTO){
        return standardService.addStandard(standardResponseDTO);

    }


    @GetMapping("/nameList")
    ResponseDTO<List<StandardResponseDTO>> getStandardNameList(){
        return standardService.getStandardNameList();

    }
}
