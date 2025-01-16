package com.javaweb.api.web;

import com.javaweb.entity.CourseEntity;
import com.javaweb.service.impl.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
public class CourseAPI {

    @Autowired
    CourseService courseService;

    @GetMapping("/{id}")
    public ResponseEntity<CourseEntity> getCourseDetails(@PathVariable Long id) {
        CourseEntity course = courseService.getCourseDetails(id);
        return ResponseEntity.ok(course);
    }
}