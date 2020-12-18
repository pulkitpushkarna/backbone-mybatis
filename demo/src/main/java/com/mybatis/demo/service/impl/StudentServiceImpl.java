package com.mybatis.demo.service.impl;

import com.mybatis.demo.dto.ResponseDTO;
import com.mybatis.demo.dto.SearchDTO;
import com.mybatis.demo.dto.StudentRequestDTO;
import com.mybatis.demo.dto.StudentResponseDTO;
import com.mybatis.demo.entity.Student;
import com.mybatis.demo.entity.User;
import com.mybatis.demo.repository.StudentMapper;
import com.mybatis.demo.service.StudentService;
import com.mybatis.demo.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;
    @Autowired
    UserServiceImpl userService;

    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public ResponseDTO<List<StudentResponseDTO>> getAllStudents() {
        ResponseDTO<List<StudentResponseDTO>> responseDTO =
                new ResponseDTO(false, Constant.REQUEST_NOT_PROCESSED);
        List<StudentResponseDTO> studentResponseDTOList = new ArrayList<>();
        List<Student> studentList = studentMapper.getAllStudents();
        if (!studentList.isEmpty()) {
            log.info("Student list{} " + studentList);
            for (Student student : studentList) {
                mapStudentToStudentResponseDto(student, studentResponseDTOList);
            }
        }
        if (!studentResponseDTOList.isEmpty()) {
            responseDTO.setCode(Constant.OK);
            responseDTO.setStatus(Boolean.TRUE);
            responseDTO.setMessage(Constant.REQUEST_SUCCESS);
            responseDTO.setData(studentResponseDTOList);
            responseDTO.setLoginedUser(userService.getCurrentUser().getUserName());
            responseDTO.setUserRole(userService.getCurrentUser().getRoles());
        } else {
            responseDTO.setCode(Constant.BAD_REQUEST);
            responseDTO.setStatus(Boolean.FALSE);
            responseDTO.setMessage("No data found");
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO createStudent(StudentRequestDTO studentRequestDto) {
        ResponseDTO responseDTO = new ResponseDTO(false, Constant.REQUEST_NOT_PROCESSED);
        validateStudentRequestDto(studentRequestDto, responseDTO);
        if (!responseDTO.getStatus()) {
            return responseDTO;
        }
        Student studentByRollNo = studentMapper.getStudentByRollNo(studentRequestDto.getRollNo());
        if (studentByRollNo != null) {
            log.error("Student alreaady exist");
            responseDTO.setStatus(Boolean.FALSE);
            responseDTO.setCode(Constant.OK);
            responseDTO.setMessage("Student already exist");
            responseDTO.setData(studentByRollNo);
        } else {
            Student student = new Student();
            student.setRollNo(studentRequestDto.getRollNo());
            student.setFirstName(studentRequestDto.getFirstName());
            student.setLastName(studentRequestDto.getLastName());
            student.setClassName(studentRequestDto.getClassName());
            student.setAddress(studentRequestDto.getAddress());
            student.setClassName(studentRequestDto.getClassName());
            student.setCity(studentRequestDto.getCity());
            student.setDeleted(false);
            studentMapper.saveStudent(student);
            responseDTO.setStatus(Boolean.TRUE);
            responseDTO.setCode(Constant.OK);
            responseDTO.setData(student);
            responseDTO.setMessage(Constant.REQUEST_SUCCESS);

        }
        return responseDTO;

    }

    public void validateStudentRequestDto(StudentRequestDTO studentRequestDto, ResponseDTO responseDTO) {
        log.info("validating student : " + studentRequestDto.getFirstName());
        if (studentRequestDto.getRollNo() == null
                || studentRequestDto.getRollNo().trim().equals("")) {
            responseDTO.setMessage("Roll no cannot be null");
            log.error("Roll no is null");
        } else if (studentRequestDto.getFirstName() == null
                || studentRequestDto.getFirstName().trim().equals("")) {
            responseDTO.setMessage("Student first name can not be null ");
            log.error("student first name is null ");

        } else if (studentRequestDto.getLastName() == null
                || studentRequestDto.getLastName().trim().equals("")) {
            responseDTO.setMessage("Student last name can not be null ");
            log.error("student last name is null ");

        } else if (studentRequestDto.getAddress() == null
                || studentRequestDto.getAddress().trim().equals("")) {
            responseDTO.setMessage("Address cannot be null");
            log.error("address cannot be null ");
        } else if (studentRequestDto.getClassName() == null
                || studentRequestDto.getClassName().trim().equals("")) {
            responseDTO.setMessage("Standard Name cannot be null");
            log.error("class name cannot be null");
        } else if (studentRequestDto.getCity() == null
                || studentRequestDto.getCity().trim().equals("")) {
            responseDTO.setMessage("City cannot be null");
            log.error("city is null");
        } else {
            responseDTO.setStatus(Boolean.TRUE);
        }
    }

    @Override
    public ResponseDTO updateStudent(StudentRequestDTO studentRequestDto, String rollNo) {
        ResponseDTO responseDTO = new ResponseDTO(false, Constant.REQUEST_NOT_PROCESSED);
        validateStudentRequestDto(studentRequestDto, responseDTO);
        log.info("updating student with roll no " + rollNo);
        if (!responseDTO.getStatus()) {
            return responseDTO;
        }
        Student studentByRollNo = studentMapper.getStudentByRollNo(rollNo);
        if (studentByRollNo == null) {
            responseDTO.setStatus(Boolean.FALSE);
            responseDTO.setMessage(Constant.NO_DATA_FOUND);
        } else {
            studentByRollNo.setFirstName(studentRequestDto.getFirstName());
            studentByRollNo.setLastName(studentRequestDto.getLastName());
            studentByRollNo.setAddress(studentRequestDto.getAddress());
            studentByRollNo.setCity(studentRequestDto.getCity());
            studentByRollNo.setClassName(studentRequestDto.getClassName());
            studentByRollNo.setClassName(studentRequestDto.getClassName());
            studentMapper.updateStudent(studentByRollNo);
            log.info("student updated ");
            responseDTO.setStatus(Boolean.TRUE);
            responseDTO.setCode(Constant.OK);
            responseDTO.setMessage(Constant.REQUEST_SUCCESS);
            responseDTO.setData(studentMapper.getStudentByRollNo(studentRequestDto.getRollNo()));
        }
        return responseDTO;

    }

    @Override
    public ResponseDTO getStudentByRollNo(String rollNo) {
        ResponseDTO responseDTO = new ResponseDTO(false, Constant.REQUEST_NOT_PROCESSED);
        log.info("Get Student by roll no " + rollNo);
        Student studentByRollNo = studentMapper.getStudentByRollNo(rollNo);
        if (studentByRollNo != null) {
            responseDTO.setStatus(Boolean.TRUE);
            responseDTO.setCode(Constant.OK);
            responseDTO.setMessage(Constant.REQUEST_SUCCESS);
            responseDTO.setData(studentByRollNo);
        } else {
            responseDTO.setStatus(Boolean.FALSE);
            responseDTO.setMessage(Constant.NO_DATA_FOUND);
        }
        return responseDTO;

    }

    @Override
    public ResponseDTO deleteStudent(String rollNo) {
        ResponseDTO responseDTO = new ResponseDTO(false, Constant.REQUEST_NOT_PROCESSED);
        Student studentByRollNo = studentMapper.getStudentByRollNo(rollNo);
        if (studentByRollNo == null) {
            responseDTO.setStatus(Boolean.FALSE);
            responseDTO.setMessage(Constant.NO_DATA_FOUND);
        } else {
            log.info("Deleting student with roll no " + rollNo);
            studentMapper.deleteStudent(rollNo);
            responseDTO.setStatus(Boolean.TRUE);
            responseDTO.setCode(Constant.OK);
            responseDTO.setMessage(Constant.REQUEST_SUCCESS);
        }
        return responseDTO;

    }

    @Override
    public ResponseDTO getStudentCountByClass(String className) {
        ResponseDTO responseDTO = new ResponseDTO(false, Constant.REQUEST_NOT_PROCESSED);
        Integer studentCountByClass = studentMapper.getStudentCountByClass(className);
        if (studentCountByClass != 0) {
            log.info("Student count of " + className + " is " + studentCountByClass);
            responseDTO.setStatus(Boolean.TRUE);
            responseDTO.setCode(Constant.OK);
            responseDTO.setMessage(Constant.REQUEST_SUCCESS);
            responseDTO.setData(studentCountByClass);
        } else {
            responseDTO.setMessage(Constant.NO_DATA_FOUND);
            responseDTO.setCode(Constant.BAD_REQUEST);
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO<List<StudentResponseDTO>> getFilteredStudent(SearchDTO searchDTO) {
        ResponseDTO<List<StudentResponseDTO>> responseDTO = new ResponseDTO(false, Constant.REQUEST_NOT_PROCESSED);
        log.info("SearchDTO " + searchDTO);
        List<Student> filteredStudent = studentMapper.getFilteredStudent(searchDTO);
        List<StudentResponseDTO> studentResponseDTOList = new ArrayList<>();
        if (!filteredStudent.isEmpty()) {
            for (Student student : filteredStudent) {
                mapStudentToStudentResponseDto(student, studentResponseDTOList);
            }
        }
        if (!studentResponseDTOList.isEmpty()) {
            responseDTO.setCode(Constant.OK);
            responseDTO.setStatus(true);
            responseDTO.setMessage(Constant.REQUEST_SUCCESS);
            responseDTO.setData(studentResponseDTOList);
        } else {
            responseDTO.setCode(Constant.BAD_REQUEST);
            responseDTO.setStatus(Boolean.FALSE);
            responseDTO.setMessage("No data found");
        }
        return responseDTO;


    }

    @Override
    public ResponseDTO<List<StudentResponseDTO>> getListOfStudent(List<String> rollNoList) {
        ResponseDTO<List<StudentResponseDTO>> responseDTO = new ResponseDTO(false, Constant.REQUEST_NOT_PROCESSED);
        if (!rollNoList.isEmpty()) {
            log.info("roll no list {} " + rollNoList);
            List<Student> studentList = studentMapper.getListOfStudent(rollNoList);
            List<StudentResponseDTO> studentResponseDTOList = new ArrayList<>();
            if (!studentList.isEmpty()) {
                log.info("Student List {} " + studentList);
                for (Student student : studentList) {
                    mapStudentToStudentResponseDto(student, studentResponseDTOList);
                }
            }
            if (!studentResponseDTOList.isEmpty()) {
                responseDTO.setCode(Constant.OK);
                responseDTO.setStatus(true);
                responseDTO.setMessage(Constant.REQUEST_SUCCESS);
                responseDTO.setData(studentResponseDTOList);
            } else {
                responseDTO.setCode(Constant.BAD_REQUEST);
                responseDTO.setStatus(Boolean.FALSE);
                responseDTO.setMessage("No data found");
            }

        }
        return responseDTO;
    }

    private void mapStudentToStudentResponseDto(Student student, List<StudentResponseDTO> studentResponseDTOList) {
        StudentResponseDTO studentResponseDto = new StudentResponseDTO();
        studentResponseDto.setId(student.getId());
        studentResponseDto.setRollNo(student.getRollNo());
        studentResponseDto.setFirstName(student.getFirstName());
        studentResponseDto.setLastName(student.getLastName());
        studentResponseDto.setClassName(student.getClassName());
        studentResponseDto.setAddress(student.getAddress());
        studentResponseDto.setCity(student.getCity());
        studentResponseDTOList.add(studentResponseDto);
    }

}
