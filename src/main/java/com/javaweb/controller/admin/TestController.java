package com.javaweb.controller.admin;


import com.javaweb.constant.SystemConstant;
import com.javaweb.entity.PartTestEntity;
import com.javaweb.model.dto.TestDTO;
import com.javaweb.model.request.TestSearchRequest;
import com.javaweb.model.response.TestSearchResponse;
import com.javaweb.security.utils.SecurityUtils;
import com.javaweb.service.impl.PartTestService;
import com.javaweb.service.impl.TestService;
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

@RestController(value = "testControllerOfAdmin")
public class TestController {

    @Autowired
    TestService testService;

    @Autowired
    PartTestService partTestService;

    @GetMapping("/admin/test-list")
    private ModelAndView testList(@ModelAttribute(name = "modelSearch") TestSearchRequest params,
                                  @ModelAttribute(SystemConstant.MODEL) TestSearchResponse model,
                                  HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView("admin/tests/list");

        // Sử dụng DisplayTagUtils để hỗ trợ phân trang
        DisplayTagUtils.of(request, model);

        // Nếu có logic phân quyền, bạn có thể thêm tại đây
        // Ví dụ: Nếu user là ROLE_STAFF, chỉ hiển thị dữ liệu liên quan
        if (SecurityUtils.getAuthorities().contains("ROLE_STAFF")) {
            params.setName(SecurityUtils.getPrincipal().getUsername());
        }

        // Lấy dữ liệu từ DB với tham số tìm kiếm và phân trang
        List<TestSearchResponse> rs = testService.findAll(params, PageRequest.of(model.getPage() - 1, model.getMaxPageItems()));

        // Đưa dữ liệu vào model để hiển thị trên giao diện
        modelAndView.addObject("ListTest", rs);

        // Gán kết quả vào model
        model.setListResult(rs);
        model.setTotalItems(testService.countTotalItems(params));
        modelAndView.addObject(SystemConstant.MODEL, model);

        return modelAndView;
    }

    @GetMapping("/admin/test-create")
    private ModelAndView testCreate(@ModelAttribute(name = "testEdit") TestDTO testDTO) {
        ModelAndView modelAndView = new ModelAndView("/admin/tests/create");

        List<PartTestEntity> partTests = partTestService.findAllByTestEntityIsNull();
        modelAndView.addObject("partTests", partTests);
        return modelAndView;
    }

    @GetMapping("/admin/test-edit-{id}")
    private ModelAndView testCreate(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/admin/tests/edit");

        List<PartTestEntity> partTests = new ArrayList<>();
        modelAndView.addObject("partTests", partTests);
        return modelAndView;
    }



}
