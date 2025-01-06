package com.javaweb.controller.web;


import com.javaweb.converter.UserConverter;
import com.javaweb.entity.*;
import com.javaweb.model.dto.TestDTO;
import com.javaweb.model.dto.UserDTO;
import com.javaweb.model.raw.UserAnswerRaw;
import com.javaweb.model.request.PartTestRequest;
import com.javaweb.repository.UserAnswerRepository;
import com.javaweb.service.IUserAnswerService;
import com.javaweb.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller(value = "testControllerOfWeb")
public class TestController {

    @Autowired
    TestService testService;

    @Autowired
    ResultService resultService;

    @Autowired
    UserAnswerService userAnswerService;

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @Autowired
    UserService userService;

    @Autowired
    UserConverter userConverter;

    @GetMapping(value = "/test")
    public ModelAndView getAllTests() {
        ModelAndView mav = new ModelAndView("web/test");

        List<TestDTO> testDTOS = testService.getTests();

        mav.addObject("tests", testDTOS);
        return mav;
    }

    @GetMapping(value = "/test/{id}")
    public ModelAndView getOneTest(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("web/tests/detail");

        TestDTO test = testService.getTestById(id);

        List<PartTestEntity> partTestEntities = test.getPartTestEntities();

        Map<PartTestEntity, List<QuestionTestEntity>> questionTestEntities = new HashMap<>();

        for (PartTestEntity partTestEntity : partTestEntities) {
            List<QuestionTestEntity> qte = partTestEntity.getQuestions();
            questionTestEntities.put(partTestEntity, qte);
        }

        mav.addObject("test", test);
        mav.addObject("partTestEntities", partTestEntities);
        mav.addObject("questionTestEntities", questionTestEntities);

        return mav;
    }

    @GetMapping(value = "/test/{id}/start")
    public ModelAndView doOneTest(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("web/tests/sheet");

        TestDTO test = testService.getTestById(id);

        List<PartTestEntity> partTestEntities = test.getPartTestEntities();

        Map<PartTestEntity, List<QuestionTestEntity>> questionTestEntities = new HashMap<>();

        for (PartTestEntity partTestEntity : partTestEntities) {
            List<QuestionTestEntity> qte = partTestEntity.getQuestions();
            questionTestEntities.put(partTestEntity, qte);
        }

        String typeOfTest = "FULL";
        Integer timeLimit = 120;


        mav.addObject("test", test);
        mav.addObject("typeOfTest", typeOfTest);
        mav.addObject("partTestEntities", partTestEntities);
        mav.addObject("questionTestEntities", questionTestEntities);
        mav.addObject("timeLimit", timeLimit);

        return mav;
    }

    @GetMapping(value = "/test/{id}/practice")
    public ModelAndView doPratice(@PathVariable Long id,
                                  @RequestParam(name = "part", required = false) List<String> part,
                                  @RequestParam(name = "time", required = false) Long timeLimit  ) {
        ModelAndView mav = new ModelAndView("web/tests/sheet");

        // Lấy thông tin bài test
        TestDTO test = testService.getTestById(id);

        // Lấy danh sách các phần (parts)
        List<PartTestEntity> partTestEntities = test.getPartTestEntities();

        // Danh sách các phần được chọn
        List<PartTestEntity> getPartTestEntities = new ArrayList<>();

        // Nếu không có phần nào được chọn, dùng tất cả các phần
        if (part == null || part.isEmpty()) {
            getPartTestEntities = partTestEntities;
        } else {
            for (PartTestEntity pt : partTestEntities) {
                if (part.contains(pt.getId().toString())) {
                    getPartTestEntities.add(pt);
                }
            }
        }

        // Tạo danh sách câu hỏi dựa trên các phần đã chọn
        Map<PartTestEntity, List<QuestionTestEntity>> questionTestEntities = new HashMap<>();
        for (PartTestEntity partTestEntity : getPartTestEntities) {
            List<QuestionTestEntity> qte = partTestEntity.getQuestions();
            questionTestEntities.put(partTestEntity, qte);
        }
        if(timeLimit == null){
            timeLimit = 1L*120;
        }

        // Thiết lập kiểu bài test
        String typeOfTest = "PARTS";

        // Gắn dữ liệu vào ModelAndView
        mav.addObject("test", test);
        mav.addObject("typeOfTest", typeOfTest);
        mav.addObject("partTestEntities", getPartTestEntities);
        mav.addObject("questionTestEntities", questionTestEntities);
        mav.addObject("timeLimit", timeLimit);

        return mav;
    }



    @GetMapping(value = "/test/{id}/result/{idresult}")
    public ModelAndView showOneTestResult(@PathVariable Long id, @PathVariable Long idresult) {

        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();


        ModelAndView mav = new ModelAndView("web/tests/result");

        TestDTO test = testService.getTestById(id);
        ResultEntity result = resultService.getOneResult(idresult);
        if (result == null) {
            return new ModelAndView("/web/errors/NotFound");
        }

        String testBelongTo = result.getUserEntity().getUserName();
        if (!currentUser.equals(testBelongTo)) {
            return new ModelAndView("/web/errors/NotFound");
        }



        List<UserAnswerEntity> userAnswerEntities = userAnswerService.getUserAnswers(result);


        Map<PartTestEntity, List<QuestionTestEntity>> questionTestEntities = new HashMap<>();
        List<AnswerEntity> userAnswerList = new ArrayList<>();

        List<PartTestEntity> havePartTestEntities = new ArrayList<>();

        for (UserAnswerEntity userAnswer : userAnswerEntities) {
            PartTestEntity partTestEntity = userAnswer.getQuestionTestEntity().getPartTest();
            if(!havePartTestEntities.contains(partTestEntity)) {
                havePartTestEntities.add(partTestEntity);
            }

            userAnswerList.add(userAnswer.getAnswerEntity());
        }

        for (PartTestEntity partTestEntity : havePartTestEntities) {
            List<QuestionTestEntity> qte = partTestEntity.getQuestions();
            questionTestEntities.put(partTestEntity, qte);
        }


        mav.addObject("test", test);
        mav.addObject("partTestEntities", havePartTestEntities);
        mav.addObject("questionTestEntities", questionTestEntities);
        mav.addObject("userAnswerList", userAnswerList); // Precomputed user answers
        mav.addObject("result", result);

        return mav;
    }

    @GetMapping(value = "/test/analytics")
    public ModelAndView haveAnalytics() {
        ModelAndView mav = new ModelAndView("web/tests/analytics");

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO user = userService.findOneByUserName(userName);
        List<ResultEntity> resultEntities = resultService.getAllResultsByUserEntity(userConverter.convertToEntity(user));

        Map<ResultEntity, List<PartTestEntity>> userAnswerEntitiesMap = new HashMap<>();
        for (ResultEntity resultEntity : resultEntities) {

            List<UserAnswerEntity> userAnswerEntities = userAnswerService.getUserAnswers(resultEntity);
            List<PartTestEntity> havePartTestEntities = new ArrayList<>();
            for (UserAnswerEntity userAnswerEntity : userAnswerEntities) {
                PartTestEntity partTestEntity = userAnswerEntity.getQuestionTestEntity().getPartTest();
                havePartTestEntities.add(partTestEntity);
            }
            userAnswerEntitiesMap.put(resultEntity, havePartTestEntities);
        }

        mav.addObject("resultEntities", resultEntities);
        mav.addObject("userAnswerEntitiesMap", userAnswerEntitiesMap);
        return mav;
    }


}
