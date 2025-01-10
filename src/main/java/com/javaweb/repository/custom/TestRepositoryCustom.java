package com.javaweb.repository.custom;

import com.javaweb.builder.TestSearchBuilder;
import com.javaweb.entity.TestEntity;
import com.javaweb.model.request.TestSearchRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestRepositoryCustom {

    List<TestEntity> findAll(TestSearchBuilder testSearchBuilder, Pageable pageable);
    int countTotalItem(TestSearchBuilder testSearchBuilder);
}
