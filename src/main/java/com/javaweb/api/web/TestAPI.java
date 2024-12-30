package com.javaweb.api.web;


import com.javaweb.entity.PartTestEntity;
import com.javaweb.entity.QuestionTestEntity;
import com.javaweb.model.dto.TestDTO;
import com.javaweb.model.dto.UserDTO;
import com.javaweb.service.ITestService;
import com.javaweb.service.impl.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api/test")
public class TestAPI {

    @Autowired
    ITestService testService;

//    @PostMapping(value="/{id}/finish");


}
