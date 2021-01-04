package com.mybatis.demo.service.impl;

import com.mybatis.demo.document.Standard;
import com.mybatis.demo.document.Subject;
import com.mybatis.demo.dto.ResponseDTO;
import com.mybatis.demo.dto.StandardResponseDTO;
import com.mybatis.demo.dto.SubjectResponseDTO;
import com.mybatis.demo.repository.StandardRepository;
import com.mybatis.demo.service.StandardService;
import com.mybatis.demo.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StandardServiceImpl implements StandardService {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    StandardRepository standardRepository;
    private static final Logger log = LoggerFactory.getLogger(StandardServiceImpl.class);


    @Override
    public ResponseDTO<List<StandardResponseDTO>> getStandardNameList() {
        ResponseDTO<List<StandardResponseDTO>> responseDTO = new ResponseDTO<>(false, Constant.REQUEST_NOT_PROCESSED);
        List<StandardResponseDTO> standardResponseDTOList = new ArrayList<>();
        Query query = new Query();
        query
                .fields()
                .include("name")
                .include("roomNo")
                .exclude("_id");
        List<Standard> standards = mongoTemplate.find(query, Standard.class);
        if (!standards.isEmpty() || standards != null){
            for (Standard subject:standards) {
                StandardResponseDTO standardResponseDTO = new StandardResponseDTO();
                standardResponseDTO.setName(subject.getName());
                standardResponseDTO.setRoomNo(subject.getRoomNo());
                standardResponseDTOList.add(standardResponseDTO);
            }
            responseDTO.setCode(Constant.OK);
            responseDTO.setStatus(Boolean.TRUE);
            responseDTO.setMessage(Constant.REQUEST_SUCCESS);
            responseDTO.setData(standardResponseDTOList);
        }else {
            responseDTO.setCode(Constant.BAD_REQUEST);
            responseDTO.setMessage(Constant.NO_DATA_FOUND);
        }

        return responseDTO;

    }

    @Override
    public ResponseDTO addStandard(StandardResponseDTO standardResponseDTO) {
        ResponseDTO responseDTO = new ResponseDTO(Boolean.FALSE,Constant.REQUEST_NOT_PROCESSED);
        validationStandardResponseDTO(responseDTO,standardResponseDTO);
        if (!responseDTO.getStatus()){
            return responseDTO;
        }
        Standard checkStandard = standardRepository.findByRoomNo(standardResponseDTO.getRoomNo());
        if (checkStandard!=null){
            log.info("standard already present");
            responseDTO.setStatus(Boolean.FALSE);
            responseDTO.setCode(Constant.BAD_REQUEST);
            responseDTO.setMessage("Room no "+standardResponseDTO.getRoomNo()+" is already occupied");
        }else {
            Standard standard = new Standard();
            standard.setName(standardResponseDTO.getName());
            standard.setRoomNo(standardResponseDTO.getRoomNo());
            standardRepository.insert(standard);
            log.info("Standard created");
            responseDTO.setCode(Constant.OK);
            responseDTO.setStatus(Boolean.TRUE);
            responseDTO.setMessage(Constant.REQUEST_SUCCESS);
        }
        return responseDTO;

    }

    private void validationStandardResponseDTO(ResponseDTO responseDTO, StandardResponseDTO standardResponseDTO) {
        log.info("validation standard "+standardResponseDTO.getName());
        if(standardResponseDTO.getName()==null || standardResponseDTO.getName().trim().equals("")){
            responseDTO.setMessage("Standard Name cannot be null");
            log.info("standard  name cannot be null");
        }else if (standardResponseDTO.getRoomNo()==null || standardResponseDTO.getRoomNo().trim().equals("")){
            responseDTO.setMessage("Room no cannot be null ");
            log.info("room no cannot be null");
        }else {
            responseDTO.setStatus(Boolean.TRUE);
        }
    }
}
