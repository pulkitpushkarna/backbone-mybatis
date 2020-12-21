package com.mybatis.demo.service.impl;

import com.mybatis.demo.document.Student;
import com.mybatis.demo.dto.AverageDTO;
import com.mybatis.demo.dto.FilterDTO;
import com.mybatis.demo.dto.ResponseDTO;
import com.mybatis.demo.repository.DocumentRepository;
import com.mybatis.demo.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

@Service
public class DocumentServiceImpl {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    DocumentRepository documentRepository;
    private static final Logger log = LoggerFactory.getLogger(DocumentServiceImpl.class);


    public List<Student> getStudentByClassName(String standard) {
//        Query query = new Query();
//                query.fields().include("firstName");
//
//                query.addCriteria(Criteria.where("standard.name").is(standard));
//        return mongoTemplate.find(query, Student.class);
        return documentRepository.findByStandard(standard);
    }

    public ResponseDTO<List<AverageDTO>> getAverageMarks(FilterDTO filterDTO) {
        ResponseDTO<List<AverageDTO>> responseDTO = new ResponseDTO<>(Boolean.FALSE, Constant.REQUEST_NOT_PROCESSED);
        log.info("FilterDTO " + filterDTO.toString());
        if (filterDTO.getStandardName() != null && filterDTO.getSubjectName() != null && !filterDTO.getSubjectName().trim().equals("") && !filterDTO.getStandardName().trim().equals("")) {
            Aggregation aggregation = newAggregation(
                    match(Criteria.where("standard.name").is(filterDTO.getStandardName())),
                    unwind("subjects"),
                    group("subjects.name").sum("subjects.score").as("total"),
                    match(Criteria.where("_id").is(filterDTO.getSubjectName())),
                    project()
                            .and("_id").as("subjectName")
                            .and("total").as("averageMarks")

            );
            List<AverageDTO> averageDTOList = mongoTemplate.aggregate(aggregation, "student", AverageDTO.class).getMappedResults();
            if (!averageDTOList.isEmpty()) {
                responseDTO.setStatus(Boolean.TRUE);
                responseDTO.setCode(Constant.OK);
                responseDTO.setMessage(Constant.REQUEST_SUCCESS);
                responseDTO.setData(averageDTOList);
            } else {
                responseDTO.setCode(Constant.BAD_REQUEST);
                responseDTO.setMessage("No Data found");
            }
        } else {
            responseDTO.setCode(Constant.BAD_REQUEST);
            responseDTO.setMessage("Subject name or standard name cannot be null ");
        }
        return responseDTO;

    }
}
