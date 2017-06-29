package com.inva.hipstertest.freemarker.controllers;

import com.codahale.metrics.annotation.Timed;
import com.inva.hipstertest.service.LessonService;
import com.inva.hipstertest.service.dto.LessonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class LessonController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @RequestMapping(value = "/freemarker/teacher-mgmt/teacher-mgmt-lessons", method = RequestMethod.GET)
    public String data(@ModelAttribute("model") ModelMap model) {
        List<LessonDTO> lessonList;
        lessonList = lessonService.findAll();
        model.addAttribute("lessonList", lessonList);
        return "teacher-mgmt/teacher-mgmt-lessons";
    }

    @RequestMapping(value = "/freemarker/teacher-mgmt/teacher-mgmt-add-lesson", method = RequestMethod.GET)
    public String index() {
        return "teacher-mgmt/teacher-mgmt-add-lesson";
    }

    @RequestMapping(value = "/freemarker/teacher-mgmt/addLesson", method = RequestMethod.GET)
    public ModelAndView adminNewSchool() {
        LessonDTO lessonDTO = new LessonDTO();
        return new ModelAndView("admin/admin-home-add-lesson", "lessonDTO", lessonDTO);
    }

//    @PostMapping(value = "/freemarker/teacher-mgmt/addLesson")
//    @Timed
//    public ModelAndView add(LessonDTO lessonDTO) {
//        System.out.println("1");
//        LessonDTO result = lessonService.save(lessonDTO); //TODO: This save doesn't work. Write a new one!
//        System.out.println("5");
//        return new ModelAndView("redirect:/freemarker/teacher-mgmt/teacher-mgmt-lessons/");
//    }

    @PostMapping(value = "/freemarker/teacher-mgmt/addLesson")
    @Timed
    public String addLesson(@ModelAttribute("lessonDTO") LessonDTO lessonDTO) {
        if (lessonDTO.getName() != null && !lessonDTO.getName().isEmpty() &&
            lessonDTO.getEnabled() != null) {
            lessonService.save(lessonDTO);
            return "redirect:/freemarker/teacher-mgmt/teacher-mgmt-lessons/";
        } else {
            return "redirect:error"; //TODO: create error page
        }
    }
}
