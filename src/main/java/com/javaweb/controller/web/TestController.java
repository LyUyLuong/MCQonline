package com.javaweb.controller.web;


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

    @GetMapping(value="/test")
    public ModelAndView getAllTests() {
        ModelAndView mav = new ModelAndView("web/test");

        List<TestDTO> testDTOS = testService.getTests();

        mav.addObject("tests", testDTOS);
        return mav;
    }

    @GetMapping(value="/test/{id}")
    public ModelAndView getOneTest(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("web/tests/detail");

        TestDTO test = testService.getTestById(id);

        List<PartTestEntity> partTestEntities = test.getPartTestEntities();

        Map<PartTestEntity,List<QuestionTestEntity>> questionTestEntities = new HashMap<>();

        for (PartTestEntity partTestEntity : partTestEntities) {
            List<QuestionTestEntity> qte = partTestEntity.getQuestions();
            questionTestEntities.put(partTestEntity,qte);
        }

        mav.addObject("test", test);
        mav.addObject("partTestEntities", partTestEntities);
        mav.addObject("questionTestEntities", questionTestEntities);

        return mav;
    }

    @GetMapping(value="/test/{id}/start")
    public ModelAndView doOneTest(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("web/tests/sheet");

        TestDTO test = testService.getTestById(id);

        List<PartTestEntity> partTestEntities = test.getPartTestEntities();

        Map<PartTestEntity,List<QuestionTestEntity>> questionTestEntities = new HashMap<>();

        for (PartTestEntity partTestEntity : partTestEntities) {
            List<QuestionTestEntity> qte = partTestEntity.getQuestions();
            questionTestEntities.put(partTestEntity,qte);
        }


        mav.addObject("test", test);
        mav.addObject("partTestEntities", partTestEntities);
        mav.addObject("questionTestEntities", questionTestEntities);

        return mav;
    }

    @GetMapping(value="/test/{id}/practice")
    public ModelAndView doPratice(@PathVariable Long id, @ModelAttribute(name = "part") List<String> part, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("web/tests/sheet");

        TestDTO test = testService.getTestById(id);

        List<PartTestEntity> partTestEntities = test.getPartTestEntities();

        List<PartTestEntity> getPartTestEntities = new ArrayList<>();

        for(PartTestEntity pt : partTestEntities) {
            for(String p : part) {
                if(pt.getId().equals(Long.parseLong(p))) {
                    getPartTestEntities.add(pt);
                }
            }
        }

        Map<PartTestEntity,List<QuestionTestEntity>> questionTestEntities = new HashMap<>();

        for (PartTestEntity partTestEntity : getPartTestEntities) {
            List<QuestionTestEntity> qte = partTestEntity.getQuestions();
            questionTestEntities.put(partTestEntity,qte);
        }

        mav.addObject("test", test);
        mav.addObject("partTestEntities", getPartTestEntities);
        mav.addObject("questionTestEntities", questionTestEntities);

        return mav;
    }

//        TestDTO test = testService.getTestById(id);

//        List<PartTestEntity> partTestEntities = test.getPartTestEntities();

//        Map<PartTestEntity,List<QuestionTestEntity>> questionTestEntities = new HashMap<>();
//
//        for (PartTestEntity partTestEntity : partTestEntities) {
//            List<QuestionTestEntity> qte = partTestEntity.getQuestions();
//            questionTestEntities.put(partTestEntity,qte);
//        }


//        mav.addObject("test", test);
//        mav.addObject("partTestEntities", partTestEntities);
//        mav.addObject("questionTestEntities", questionTestEntities);


    @GetMapping(value="/test/{id}/result/{idresult}")
    public ModelAndView showOneTestResult(@PathVariable Long id, @PathVariable Long idresult) {
        ModelAndView mav = new ModelAndView("web/tests/result");

        TestDTO test = testService.getTestById(id);
        ResultEntity result = resultService.getOneResult(idresult);
        List<UserAnswerEntity> userAnswerEntities = userAnswerService.getUserAnswers(result);

        List<PartTestEntity> partTestEntities = test.getPartTestEntities();
        Map<PartTestEntity, List<QuestionTestEntity>> questionTestEntities = new HashMap<>();
        List<AnswerEntity> userAnswerList = new ArrayList<>();

        for (UserAnswerEntity userAnswer : userAnswerEntities) {
            userAnswerList.add(userAnswer.getAnswerEntity());
        }

        for (PartTestEntity partTestEntity : partTestEntities) {
            List<QuestionTestEntity> qte = partTestEntity.getQuestions();
            questionTestEntities.put(partTestEntity, qte);
        }

        mav.addObject("test", test);
        mav.addObject("partTestEntities", partTestEntities);
        mav.addObject("questionTestEntities", questionTestEntities);
        mav.addObject("userAnswerList", userAnswerList); // Precomputed user answers
        mav.addObject("result", result);

        return mav;
    }


    @PostMapping("/test/{testid}/finish")
    public ResponseEntity<String> submitSheet(@PathVariable Long testid,
                                              @RequestParam("part") List<String> parts,
                                              @RequestBody List<UserAnswerRaw> userAnswerList) {

        if (testid == null || testid <= 0 || userAnswerList == null || userAnswerList.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid data provided.");
        }

        UserDTO user = userService.findOneByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        TestDTO test = testService.getTestById(testid);
        

        if (user == null || test == null) {
            return ResponseEntity.notFound().build();
        }
        ResultEntity resultEntity = resultService.sumbitSheet(user, test, userAnswerList);

        return ResponseEntity.ok("Test submitted successfully.");
    }

}
