package com.mybatis.demo.service;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mybatis.demo.document.Student;
import com.mybatis.demo.dto.AverageDTO;
import com.mybatis.demo.dto.FilterDTO;
import com.mybatis.demo.dto.ResponseDTO;
import com.mybatis.demo.dto.StudenttDTO;
import com.mybatis.demo.repository.DocumentRepository;
import com.mybatis.demo.service.impl.StudentServiceImpl;
import com.mybatis.demo.util.Constant;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

@Service
public class DocumentService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    DocumentRepository documentRepository;
    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);


    public List<Student> getStudentByClassName(String standard) {
//        Query query = new Query();
//                query.fields().include("firstName");
//
//                query.addCriteria(Criteria.where("standard.name").is(standard));
//        return mongoTemplate.find(query, Student.class);
        return documentRepository.findByStandard(standard);
    }

    public List<StudenttDTO> aggregationExample(String standard) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("standard.name").is(standard)),
                Aggregation.unwind("subjects"),
                Aggregation.group("subjects.name").sum("subjects.score").as("total"),
                Aggregation.match(Criteria.where("_id").is("Maths")),
                Aggregation.project()
                        .and("_id").as("name")
                        .and("total").as("total")

        );

        List<StudenttDTO> students = mongoTemplate.aggregate(aggregation, "student", StudenttDTO.class).getMappedResults();

        return students.stream().map(e -> {
            StudenttDTO studenttDTO = new StudenttDTO();
            studenttDTO.setName(e.getName());
            studenttDTO.setTotal(e.getTotal());
            return studenttDTO;
        }).collect(Collectors.toList());
    }

    public ResponseDTO<List<AverageDTO>> getAverageMarks(FilterDTO filterDTO) {
        ResponseDTO<List<AverageDTO>> responseDTO = new ResponseDTO<>(Boolean.FALSE, Constant.REQUEST_NOT_PROCESSED);
        log.info("FilterDTO "+filterDTO.toString());
        if (filterDTO.getStandardName() != null && filterDTO.getSubjectName() != null) {
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("standard.name").is(filterDTO.getStandardName())),
                    Aggregation.unwind("subjects"),
                    Aggregation.group("subjects.name").sum("subjects.score").as("total"),
                    Aggregation.match(Criteria.where("_id").is(filterDTO.getSubjectName())),
                    Aggregation.project()
                            .and("_id").as("subjectName")
                            .and("total").as("averageMarks")

            );
            List<AverageDTO> averageDTOList = mongoTemplate.aggregate(aggregation, "student", AverageDTO.class).getMappedResults();
            if (!averageDTOList.isEmpty()){
                responseDTO.setStatus(Boolean.TRUE);
                responseDTO.setCode(Constant.OK);
                responseDTO.setMessage(Constant.REQUEST_SUCCESS);
                responseDTO.setData(averageDTOList);
            }else {
                responseDTO.setCode(Constant.BAD_REQUEST);
                responseDTO.setMessage("No Data found");
            }
        }else {
            responseDTO.setCode(Constant.BAD_REQUEST);
            responseDTO.setMessage("Subject name or standard name cannot be null ");
        }
        return responseDTO;

    }
}
