package com.mybatis.demo.service.impl;

import com.mybatis.demo.document.Subject;
import com.mybatis.demo.dto.ResponseDTO;
import com.mybatis.demo.dto.SubjectRequestDTO;
import com.mybatis.demo.dto.SubjectResponseDTO;
import com.mybatis.demo.repository.SubjectRepository;
import com.mybatis.demo.service.SubjectService;
import com.mybatis.demo.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    private static final Logger log = LoggerFactory.getLogger(SubjectServiceImpl.class);

    @Override
    public ResponseDTO addSubject(SubjectRequestDTO subjectRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO(false, Constant.REQUEST_NOT_PROCESSED);
        validateSubjectRequestDTO(responseDTO, subjectRequestDTO);
        if (!responseDTO.getStatus()) {
            return responseDTO;
        }
        Subject byCode = subjectRepository.findByCode(subjectRequestDTO.getCode());
        if (byCode != null) {
            log.error("Subject with code alreaady exist");
            responseDTO.setStatus(Boolean.FALSE);
            responseDTO.setCode(Constant.OK);
            responseDTO.setMessage("Subject with code " + subjectRequestDTO.getCode() + " already exist");
            responseDTO.setData(byCode);
        } else {
            Subject subject = new Subject();
            subject.setName(subjectRequestDTO.getName());
            subject.setCode(subjectRequestDTO.getCode());
            subject.setScore(subjectRequestDTO.getScore());
            subjectRepository.insert(subject);
            responseDTO.setCode(Constant.OK);
            responseDTO.setStatus(Boolean.TRUE);
            responseDTO.setMessage(Constant.REQUEST_SUCCESS);
            responseDTO.setData(subject);
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO<List<SubjectResponseDTO>> getAllSubjects() {
        ResponseDTO<List<SubjectResponseDTO>> responseDTO = new ResponseDTO<>(false, Constant.REQUEST_NOT_PROCESSED);
        List<Subject> subjects = subjectRepository.findAll();
        List<SubjectResponseDTO> subjectResponseDTOList = new ArrayList<>();
        if (!subjects.isEmpty()) {
            for (Subject subject : subjects) {
                SubjectResponseDTO subjectResponseDTO = new SubjectResponseDTO();
                // subjectResponseDTO.setId(subject.getId());
                subjectResponseDTO.setCode(subject.getCode());
                subjectResponseDTO.setName(subject.getName());
                subjectResponseDTO.setScore(subject.getScore());
                subjectResponseDTOList.add(subjectResponseDTO);
            }
            responseDTO.setCode(Constant.OK);
            responseDTO.setStatus(Boolean.TRUE);
            responseDTO.setMessage(Constant.REQUEST_SUCCESS);
            responseDTO.setData(subjectResponseDTOList);
        } else {
            responseDTO.setCode(Constant.BAD_REQUEST);
            responseDTO.setMessage(Constant.NO_DATA_FOUND);
        }
        return responseDTO;
    }

    private void validateSubjectRequestDTO(ResponseDTO responseDTO, SubjectRequestDTO subjectRequestDTO) {
        log.info("validating subject " + subjectRequestDTO.getName());
        if (subjectRequestDTO.getName() == null || subjectRequestDTO.getName().trim().equals("")) {
            responseDTO.setMessage("Subject Name Cannot be null ");
            log.info("Subject name is null");
        } else if (subjectRequestDTO.getScore() == null) {
            responseDTO.setMessage("Subject score cannot be null");
            log.info("Suject score is null");
        } else if (subjectRequestDTO.getCode() == null || subjectRequestDTO.getCode().trim().equals("")) {
            responseDTO.setMessage("Subject code cannot be null");
            log.info("Subject code cannot be null");
        } else {
            responseDTO.setStatus(Boolean.TRUE);
        }

    }

    @Override
    public ResponseDTO<List<SubjectResponseDTO>> getNameList() {
        ResponseDTO<List<SubjectResponseDTO>> responseDTO = new ResponseDTO<>(false, Constant.REQUEST_NOT_PROCESSED);
        List<SubjectResponseDTO> subjectResponseDTOList = new ArrayList<>();
        Query query = new Query();
        query
                .fields()
                .include("name")
                .include("code")
                .exclude("_id");
        List<Subject> subjects = mongoTemplate.find(query, Subject.class);
        if (!subjects.isEmpty() || subjects != null){
            for (Subject subject:subjects) {
                SubjectResponseDTO subjectResponseDTO = new SubjectResponseDTO();
                subjectResponseDTO.setName(subject.getName());
                subjectResponseDTO.setCode(subject.getCode());
                subjectResponseDTOList.add(subjectResponseDTO);
            }
            responseDTO.setCode(Constant.OK);
            responseDTO.setStatus(Boolean.TRUE);
            responseDTO.setMessage(Constant.REQUEST_SUCCESS);
            responseDTO.setData(subjectResponseDTOList);
        }else {
            responseDTO.setCode(Constant.BAD_REQUEST);
            responseDTO.setMessage(Constant.NO_DATA_FOUND);
        }

        return responseDTO;


    }
}
