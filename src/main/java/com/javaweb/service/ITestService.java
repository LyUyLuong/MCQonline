package com.javaweb.service;

import com.javaweb.entity.TestEntity;
import com.javaweb.model.dto.TestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITestService {

    List<TestDTO> getTests();

    TestDTO getTestById(Long id);

    List<TestDTO> haveanAlytics();

}
