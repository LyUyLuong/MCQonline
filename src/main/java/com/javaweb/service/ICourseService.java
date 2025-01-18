package com.javaweb.service;

import com.javaweb.entity.CourseEntity;
import com.javaweb.model.dto.CourseDTO;
import com.javaweb.model.request.CourseRequest;
import com.javaweb.model.response.CourseReponse;
import com.javaweb.model.response.TestSearchResponse;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ICourseService {

    List<CourseEntity> getAllCourses();

    CourseEntity getCourseDetails(Long courseId);

    List<CourseReponse> findAll(CourseRequest params, PageRequest pageable);

    int countTotalItems(CourseRequest params);

    void createOrUpdateCourse(CourseDTO courseDTO);
}