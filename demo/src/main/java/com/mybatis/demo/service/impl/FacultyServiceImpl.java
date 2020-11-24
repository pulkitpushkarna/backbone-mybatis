package com.mybatis.demo.service.impl;

import com.mybatis.demo.dto.FacultyResponseDTO;
import com.mybatis.demo.dto.ResponseDTO;
import com.mybatis.demo.repository.FacultyMapper;
import com.mybatis.demo.service.FacultyService;
import com.mybatis.demo.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    FacultyMapper facultyMapper;
    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public ResponseDTO<List<FacultyResponseDTO>> getFacultyInformation(String facultyCode) {
        ResponseDTO<List<FacultyResponseDTO>> responseDTO = new ResponseDTO(Boolean.FALSE, Constant.REQUEST_NO_PROCESSED);
        List<FacultyResponseDTO> facultyInformation = facultyMapper.getFacultyInformation(facultyCode);
        List<FacultyResponseDTO> facultyResponseDTOList = new ArrayList<>();
        log.info("faculty code " + facultyCode);
        if (!facultyInformation.isEmpty()) {
            log.debug("faculty list {} " + facultyInformation);
            for (FacultyResponseDTO o : facultyInformation) {
                FacultyResponseDTO facultyResponseDTO = new FacultyResponseDTO();
                facultyResponseDTO.setStudentName(o.getStudentName());
                facultyResponseDTO.setStudentRollNo(o.getStudentRollNo());
                facultyResponseDTO.setClassName(o.getClassName());
                facultyResponseDTO.setClassCode(o.getClassCode());
                facultyResponseDTO.setFacultyName(o.getFacultyName());
                facultyResponseDTO.setFacultyCode(o.getFacultyCode());
                facultyResponseDTOList.add(facultyResponseDTO);
            }
            responseDTO.setCode(Constant.OK);
            responseDTO.setMessage(Constant.REQUEST_SUCCESS);
            responseDTO.setData(facultyResponseDTOList);
        } else {
            responseDTO.setCode(Constant.BAD_REQUEST);
            responseDTO.setMessage("No Data found");
        }

        return responseDTO;

    }
}
