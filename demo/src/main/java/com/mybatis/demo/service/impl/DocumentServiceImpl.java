package com.mybatis.demo.service.impl;

import com.mybatis.demo.document.Standard;
import com.mybatis.demo.document.Student;
import com.mybatis.demo.document.Subject;
import com.mybatis.demo.dto.*;
import com.mybatis.demo.repository.DocumentRepository;
import com.mybatis.demo.repository.StandardRepository;
import com.mybatis.demo.repository.SubjectRepository;
import com.mybatis.demo.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

@Service
public class DocumentServiceImpl {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    StandardRepository standardRepository;
    private static final Logger log = LoggerFactory.getLogger(DocumentServiceImpl.class);


    public List<Student> getStudentByClassName(String standard) {
//        Query query = new Query();
//                query.fields().include("firstName");
//
//                query.addCriteria(Criteria.where("standard.name").is(standard));
//        return mongoTemplate.find(query, Student.class);
        return documentRepository.findByStandard(standard);
    }

    public List<Student> getStudentByRollNo(String rollNo){
        Query query = new Query();
        query
                .fields()
                .include("firstName")
                .include("rollNo");
        query
                .addCriteria(Criteria.where("rollNo").is(rollNo));
        return mongoTemplate.find(query,Student.class);
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

    public ResponseDTO<List<DocumentResponseDTO>> getDocument() {
        ResponseDTO<List<DocumentResponseDTO>> responseDTO = new ResponseDTO<>(Boolean.FALSE, Constant.REQUEST_NOT_PROCESSED);
        List<Student> studentList = documentRepository.findAll();
        List<DocumentResponseDTO> documentResponseDTOList = new ArrayList<>();
        if (!studentList.isEmpty()) {
            for (Student student : studentList) {
                DocumentResponseDTO documentResponseDTO = new DocumentResponseDTO();
                documentResponseDTO.setFirstName(student.getFirstName());
                documentResponseDTO.setLastName(student.getLastName());
                documentResponseDTO.setRollNo(student.getRollNo());
                documentResponseDTO.setStandard(student.getStandard());
                documentResponseDTO.setSubjects(student.getSubjects());
                documentResponseDTOList.add(documentResponseDTO);
            }
            responseDTO.setCode(Constant.OK);
            responseDTO.setStatus(Boolean.TRUE);
            responseDTO.setMessage(Constant.REQUEST_SUCCESS);
            responseDTO.setData(documentResponseDTOList);
        } else {
            responseDTO.setCode(Constant.BAD_REQUEST);
            responseDTO.setMessage(Constant.NO_DATA_FOUND);
        }
        return responseDTO;
    }

    public ResponseDTO saveDocument(DocumentRequestDTO documentRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO<>(Boolean.FALSE, Constant.REQUEST_NOT_PROCESSED);
        validateDocumentRequestDTO(documentRequestDTO, responseDTO);
        if (!responseDTO.getStatus()) {
            return responseDTO;
        }
        Student studentCheck  = documentRepository.findByRollNo(documentRequestDTO.getRollNo());
        if (studentCheck!=null) {
            log.info("Student already present with roll no "+documentRequestDTO.getRollNo());
            responseDTO.setStatus(Boolean.FALSE);
            responseDTO.setCode(Constant.BAD_REQUEST);
            responseDTO.setMessage("Student with roll no " + documentRequestDTO.getRollNo() + " already present");
        } else {
            Student student = new Student();
            student.setFirstName(documentRequestDTO.getFirstName());
            student.setLastName(documentRequestDTO.getLastName());
            student.setRollNo(documentRequestDTO.getRollNo());
            Standard standard = standardRepository.findByName(documentRequestDTO.getStandard());
            if (standard != null) {
                student.setStandard(standard);
            }
            List<Subject> subjectList = new ArrayList<>();

            Subject subject1 = subjectRepository.findByCode(documentRequestDTO.getSubject1());
            if (subject1 != null) {
                subjectList.add(subject1);
            }
            Subject subject2 = subjectRepository.findByCode(documentRequestDTO.getSubject2());
            if (subject2 != null) {
                subjectList.add(subject2);
            }
            Subject subject3 = subjectRepository.findByCode(documentRequestDTO.getSubject3());
            if (subject3 != null) {
                subjectList.add(subject3);
            }

            if (!subjectList.isEmpty()) {
                student.setSubjects(subjectList);
            }
            documentRepository.insert(student);
            log.info("Student saved");
            responseDTO.setCode(Constant.OK);
            responseDTO.setStatus(Boolean.TRUE);
            responseDTO.setMessage(Constant.REQUEST_SUCCESS);
        }

        return responseDTO;

    }

    private void validateDocumentRequestDTO(DocumentRequestDTO documentRequestDTO, ResponseDTO responseDTO) {
        log.info("validating student " + documentRequestDTO.getFirstName());
        if (documentRequestDTO.getFirstName() == null || documentRequestDTO.getFirstName().trim().equals("")) {
            responseDTO.setMessage("first name cannot be null ");
            log.info("firstname cannot be null");
        } else if (documentRequestDTO.getLastName() == null || documentRequestDTO.getLastName().trim().equals("")) {
            responseDTO.setMessage("last name cannot be null");
            log.info("last name cannot be null");
        } else if (documentRequestDTO.getRollNo() == null || documentRequestDTO.getRollNo().trim().equals("")) {
            responseDTO.setMessage("roll no cannot be null");
            log.info("roll no cannot be null");
        } else if (documentRequestDTO.getSubject2().equals(documentRequestDTO.getSubject1())) {
            responseDTO.setMessage("subject name cannot be same");
            log.info("subject name cannot be same");
        } else if (documentRequestDTO.getSubject3().equals(documentRequestDTO.getSubject2()) || documentRequestDTO.getSubject3().equals(documentRequestDTO.getSubject1())) {
            responseDTO.setMessage("subject name cannot be same");
            log.info("subject name cannot be same");
        } else {
            responseDTO.setStatus(Boolean.TRUE);
        }

    }
}
