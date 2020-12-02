package com.mybatis.demo.service;

import com.mybatis.demo.dto.ResponseDTO;
import com.mybatis.demo.dto.SearchDTO;
import com.mybatis.demo.dto.StudentRequestDTO;
import com.mybatis.demo.dto.StudentResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface StudentService {

    ResponseDTO<List<StudentResponseDTO>> getAllStudents();

    ResponseDTO createStudent(StudentRequestDTO studentRequestDto);

    ResponseDTO updateStudent(StudentRequestDTO studentRequestDto,String rollNo);

    ResponseDTO getStudentByRollNo(String rollNo);

    ResponseDTO deleteStudent(String rollNo);


    ResponseDTO getStudentCountByClass(String className);

    ResponseDTO<List<StudentResponseDTO>> getFilteredStudent(SearchDTO searchDTO);

    ResponseDTO<List<StudentResponseDTO>> getListOfStudent(List<String> rollNoList);
}
