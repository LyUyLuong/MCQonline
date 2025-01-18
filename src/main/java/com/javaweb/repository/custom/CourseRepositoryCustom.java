package com.javaweb.repository.custom;

import com.javaweb.builder.CourseSearchBuilder;
import com.javaweb.entity.CourseEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseRepositoryCustom {

    List<CourseEntity> findAll(CourseSearchBuilder courseSearchBuilder, Pageable pageable);
    int countTotalItem(CourseSearchBuilder courseSearchBuilder);

}
