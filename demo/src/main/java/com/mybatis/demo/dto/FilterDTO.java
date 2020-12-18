package com.mybatis.demo.dto;

public class FilterDTO {
    private String standardName;
    private String subjectName;

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "FilterDTO{" +
                "standardName='" + standardName + '\'' +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }
}
