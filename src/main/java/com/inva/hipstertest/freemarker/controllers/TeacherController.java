package com.inva.hipstertest.freemarker.controllers;

import com.inva.hipstertest.service.TeacherService;
import com.inva.hipstertest.service.dto.PupilDTO;
import com.inva.hipstertest.service.dto.ScheduleDTO;
import com.inva.hipstertest.service.dto.SchoolDTO;
import com.inva.hipstertest.service.dto.TeacherDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static com.inva.hipstertest.security.SecurityUtils.isCurrentUserInRole;

/**
 * Created by Kolja on 19.06.2017.
 */
@Controller
public class TeacherController {

    TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @RequestMapping(value = "freemarker/teacher-schedule", method = RequestMethod.GET)
    public String some(@ModelAttribute("model") ModelMap model) {
        List<TeacherDTO> teacherList = teacherService.findAll();
        TeacherDTO selectedTeacher = null;
        model.addAttribute("teacherList", teacherList);
        model.addAttribute("selectedTeacher", selectedTeacher);
        model.addAttribute("selectedTeacher", selectedTeacher);

        if (isCurrentUserInRole("ROLE_TEACHER")) {
            return "teacher-schedule";
        } else {
            return "redirect:error";
        }
    }
}
