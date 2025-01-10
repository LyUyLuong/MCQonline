package com.javaweb.service;

import com.javaweb.entity.TestEntity;
import com.javaweb.model.dto.TestDTO;
import com.javaweb.model.request.TestSearchRequest;
import com.javaweb.model.response.TestSearchResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITestService {

    List<TestDTO> getTests();

    TestDTO getTestById(Long id);

    List<TestDTO> haveanAlytics();

    int countTotalItems(TestSearchRequest params);
    List<TestSearchResponse> findAll(TestSearchRequest params, PageRequest pageable);
}
