package com.mybatis.demo.service;

import com.mybatis.demo.dto.FacultyResponseDTO;
import com.mybatis.demo.dto.ResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface FacultyService {

    ResponseDTO<List<FacultyResponseDTO>> getFacultyInformation(String facultyCode);
}
