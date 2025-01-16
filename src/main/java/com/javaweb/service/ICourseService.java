package com.javaweb.service;

import com.javaweb.entity.CourseEntity;

import java.util.List;

public interface ICourseService {

    List<CourseEntity> getAllCourses();

    CourseEntity getCourseDetails(Long courseId);
}