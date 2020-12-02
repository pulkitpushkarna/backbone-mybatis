package com.mybatis.demo.controller;

import com.mybatis.demo.dto.ResponseDTO;
import com.mybatis.demo.dto.SearchDTO;
import com.mybatis.demo.dto.StudentRequestDTO;
import com.mybatis.demo.dto.StudentResponseDTO;
import com.mybatis.demo.entity.User;
import com.mybatis.demo.repository.UserMapper;
import com.mybatis.demo.service.StudentService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentService studentService;
    @Autowired
    UserMapper userMapper;


    @GetMapping("/")
    ResponseDTO<List<StudentResponseDTO>> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/")
    ResponseDTO createStudent(@RequestBody  StudentRequestDTO studentRequestDto) {
        return studentService.createStudent(studentRequestDto);
    }

    @GetMapping("/{rollNo}")
    ResponseDTO getStudentByRollNo(@PathVariable String rollNo) {
        return studentService.getStudentByRollNo(rollNo);

    }

    @PutMapping("/{rollNo}")
    ResponseDTO updateStudent( @PathVariable String rollNo, @RequestBody  StudentRequestDTO studentRequestDto) {
        return studentService.updateStudent(studentRequestDto,rollNo);
    }

    @DeleteMapping("/{rollNo}")
    ResponseDTO deleteStudent(@PathVariable String rollNo) {
        return studentService.deleteStudent(rollNo);
    }

    @GetMapping("/count/{className}")
    ResponseDTO getStudentCountByClass(@PathVariable String className){
        return studentService.getStudentCountByClass(className);
    }

    @GetMapping("/filter")
    ResponseDTO<List<StudentResponseDTO>> getFilteredStudent(@RequestBody SearchDTO searchDTO){
        return studentService.getFilteredStudent(searchDTO);
    }

    @GetMapping("/list")
    ResponseDTO<List<StudentResponseDTO>> getListOfStudent(@RequestBody List<String> rollNoList){
       // List<String> rollNoList = new ArrayList<>(Arrays.asList("1001","1002","1003"));
        return studentService.getListOfStudent(rollNoList);
    }

    @GetMapping("/user/{userName}")
    public  User getUser(@PathVariable String userName){
        System.out.println(userName);
        return userMapper.findByUserName(userName);

    }


}
