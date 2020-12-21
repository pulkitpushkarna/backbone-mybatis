package com.mybatis.demo.service;

import com.mybatis.demo.dto.ResponseDTO;
import com.mybatis.demo.dto.StandardResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StandardService {
    ResponseDTO<List<StandardResponseDTO>> getStandardNameList();

}
