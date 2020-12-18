package com.mybatis.demo.dto;

public class AverageDTO {

    private String subjectName;
    private Integer averageMarks;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getAverageMarks() {
        return averageMarks;
    }

    public void setAverageMarks(Integer averageMarks) {
        this.averageMarks = averageMarks;
    }
}
