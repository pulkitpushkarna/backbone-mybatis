package com.mybatis.demo.repository;

import com.mybatis.demo.dto.SearchDTO;
import com.mybatis.demo.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {

    List<Student> getAllStudents();

    Student getStudentByRollNo(String rollNo);

    void saveStudent(Student student);

    void updateStudent(Student student);

    void deleteStudent(String rollNo);

    Integer getStudentCountByClass(String className);

    List<Student> getFilteredStudent(SearchDTO searchDTO);

    List<Student> getListOfStudent(List<String> rollNoList);
}
