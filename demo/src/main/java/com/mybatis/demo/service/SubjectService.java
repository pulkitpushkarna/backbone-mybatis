package com.mybatis.demo.service;

import com.mybatis.demo.dto.ResponseDTO;
import com.mybatis.demo.dto.SubjectRequestDTO;
import com.mybatis.demo.dto.SubjectResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SubjectService {


    ResponseDTO addSubject(SubjectRequestDTO subjectRequestDTO);

    ResponseDTO<List<SubjectResponseDTO>> getAllSubjects();

    ResponseDTO<List<SubjectResponseDTO>> getNameList();
}
