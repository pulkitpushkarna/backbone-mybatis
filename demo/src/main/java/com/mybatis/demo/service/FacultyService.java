package com.mybatis.demo.service;

import com.mybatis.demo.dto.FacultyResponseDTO;
import com.mybatis.demo.dto.ResponseDTO;

import java.util.List;

public interface FacultyService {

    ResponseDTO<List<FacultyResponseDTO>> getFacultyInformation(String facultyCode);
}
