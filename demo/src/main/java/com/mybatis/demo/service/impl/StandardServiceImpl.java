package com.mybatis.demo.service.impl;

import com.mybatis.demo.document.Standard;
import com.mybatis.demo.document.Subject;
import com.mybatis.demo.dto.ResponseDTO;
import com.mybatis.demo.dto.StandardResponseDTO;
import com.mybatis.demo.dto.SubjectResponseDTO;
import com.mybatis.demo.service.StandardService;
import com.mybatis.demo.util.Constant;
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
}
