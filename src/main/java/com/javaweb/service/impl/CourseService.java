package com.javaweb.service.impl;

import com.javaweb.entity.CourseEntity;
import com.javaweb.repository.CourseRepository;
import com.javaweb.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CourseService implements ICourseService {
    @Autowired
    CourseRepository courseRepository;

    public List<CourseEntity> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public CourseEntity getCourseDetails(Long courseId) {
        return courseRepository.findById(courseId).get();
    }
}