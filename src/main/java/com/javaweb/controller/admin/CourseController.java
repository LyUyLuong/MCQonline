package com.javaweb.controller.admin;

import com.javaweb.constant.SystemConstant;
import com.javaweb.converter.CourseConverter;
import com.javaweb.entity.PartTestEntity;
import com.javaweb.model.dto.CourseDTO;
import com.javaweb.model.dto.TestDTO;
import com.javaweb.model.request.CourseRequest;
import com.javaweb.model.request.TestSearchRequest;
import com.javaweb.model.response.CourseReponse;
import com.javaweb.model.response.TestSearchResponse;
import com.javaweb.repository.CourseRepository;
import com.javaweb.security.utils.SecurityUtils;
import com.javaweb.service.impl.CourseService;
import com.javaweb.utils.DisplayTagUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController(value = "courseControllerOfAdmin")
public class CourseController {

    @Autowired
    CourseService courseService;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    CourseConverter courseConverter;

    @GetMapping("/admin/course-list")
    private ModelAndView courseList(@ModelAttribute(name = "modelSearch") CourseRequest params,
                                  @ModelAttribute(SystemConstant.MODEL) CourseReponse model,
                                  HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView("admin/course/list");

        // Sử dụng DisplayTagUtils để hỗ trợ phân trang
        DisplayTagUtils.of(request, model);


        // Lấy dữ liệu từ DB với tham số tìm kiếm và phân trang
        List<CourseReponse> rs = courseService.findAll(params, PageRequest.of(model.getPage() - 1, model.getMaxPageItems()));

        // Đưa dữ liệu vào model để hiển thị trên giao diện
        modelAndView.addObject("ListCourse", rs);

        // Gán kết quả vào model
        model.setListResult(rs);
        model.setTotalItems(courseService.countTotalItems(params));
        modelAndView.addObject(SystemConstant.MODEL, model);

        return modelAndView;
    }


    @GetMapping("/admin/course-create")
    private ModelAndView courseCreate(@ModelAttribute(name = "courseEdit") CourseDTO courseDTO) {
        ModelAndView modelAndView = new ModelAndView("/admin/course/create");


        return modelAndView;
    }

    @GetMapping("/admin/course-edit-{id}")
    private ModelAndView courseEdit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/admin/course/edit");

        CourseDTO courseDTO = courseConverter.toDTO(courseRepository.findById(id).get());

        modelAndView.addObject("courseEdit", courseDTO);
        return modelAndView;
    }

}
