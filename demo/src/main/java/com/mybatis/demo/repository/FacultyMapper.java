package com.mybatis.demo.repository;

import com.mybatis.demo.dto.FacultyResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface FacultyMapper {

    List<FacultyResponseDTO> getFacultyInformation(String facultyCode);

    String getFacultyByFacultyCode(String facultyCode);
}
