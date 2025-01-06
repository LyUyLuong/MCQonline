package com.javaweb.service.impl;

import com.javaweb.converter.TestConverter;
import com.javaweb.entity.TestEntity;
import com.javaweb.model.dto.TestDTO;
import com.javaweb.repository.TestRepository;
import com.javaweb.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TestService implements ITestService {

    @Autowired
    TestRepository testRepository;

    @Autowired
    TestConverter testConverter;

    @Override
    public List<TestDTO> getTests() {

        List<TestEntity> testEntities = testRepository.findAll();
        List<TestDTO> testDTOS = new ArrayList<>();
        for (TestEntity testEntity : testEntities) {
            TestDTO testDTO = testConverter.toDTO(testEntity);

            testDTOS.add(testDTO);
        }
        return testDTOS;
    }

    @Override
    public TestDTO getTestById(Long id) {
        return testConverter.toDTO(testRepository.findById(id).get());
    }

    @Override
    public List<TestDTO> haveanAlytics(){



        return null;
    }
}
