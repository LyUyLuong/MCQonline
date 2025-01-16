package com.javaweb.controller.web;

import com.javaweb.converter.TestConverter;
import com.javaweb.converter.UserConverter;
import com.javaweb.entity.CourseEntity;
import com.javaweb.model.dto.TestDTO;
import com.javaweb.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller(value = "coursesControllerOfWeb")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    OrderService orderService;

    @GetMapping(value = "/courses")
    public ModelAndView getCourses() {
        ModelAndView mav = new ModelAndView("web/courses/list");

        List<CourseEntity> courses = courseService.getAllCourses();

        mav.addObject("courses", courses);
        return mav;
    }

    // Chi tiết đơn hàng
    @GetMapping(value = "/orders/{id}")
    public ModelAndView getOrderDetails(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("web/orders/detail");
        mav.addObject("order", orderService.getOrderById(id));
        return mav;
    }

    @GetMapping("/courses/{id}")
    public ModelAndView getCourseDetails(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("web/courses/details");
        CourseEntity course = courseService.getCourseDetails(id);
        mav.addObject("course", course);
        return mav;
    }

}