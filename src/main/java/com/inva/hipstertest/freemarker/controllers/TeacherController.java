package com.inva.hipstertest.freemarker.controllers;

import com.inva.hipstertest.freemarker.searchcriteria.ClassroomSearchCriteria;
import com.inva.hipstertest.freemarker.searchcriteria.FormSearchCriteria;
import com.inva.hipstertest.freemarker.searchcriteria.ScheduleSearchCriteria;
import com.inva.hipstertest.service.ClassroomService;
import com.inva.hipstertest.service.FormService;
import com.inva.hipstertest.service.ScheduleService;
import com.inva.hipstertest.service.TeacherService;
import com.inva.hipstertest.service.dto.ClassroomDTO;
import com.inva.hipstertest.service.dto.FormDTO;
import com.inva.hipstertest.service.dto.ScheduleDTO;
import com.inva.hipstertest.service.dto.TeacherDTO;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TeacherController {
    private final Logger log = LoggerFactory.getLogger(TestController.class);

    private final TeacherService teacherService;
    private final ScheduleService scheduleService;
    private final ClassroomService classroomService;
    private final FormService formService;

    public TeacherController(TeacherService teacherService, ScheduleService scheduleService,
                             ClassroomService classroomService, FormService formService) {

        this.teacherService = teacherService;
        this.scheduleService = scheduleService;
        this.classroomService = classroomService;
        this.formService = formService;
    }

    @RequestMapping(value = "/freemarker/teacher/schedule", method = RequestMethod.GET)
    public String scheduling(@ModelAttribute("model") ModelMap model) {
        return "teacher/schedule";
    }

    @RequestMapping(value = "freemarker/teacher/schedule/forms", method = RequestMethod.GET)
    public @ResponseBody
    List<FormDTO> getAllFormsFromCurrentSchool(){
        log.debug("Create Ajax request for all forms");
        return formService.findAllFormsByCurrentSchool();
    }

    @RequestMapping(value = "freemarker/teacher/schedule/teachers", method = RequestMethod.GET)
    public @ResponseBody List<TeacherDTO> getAllTeachersFromCurrentSchool(){
        log.debug("Create Ajax request for all teachers");
        return teacherService.findAllByCurrentSchool();
    }

    @RequestMapping(value = "freemarker/teacher/schedule/classrooms", method = RequestMethod.GET)
    public @ResponseBody List<ClassroomDTO> getAllClassroomsFromCurrentSchool(){
        log.debug("Create Ajax request for all classrooms");
        return classroomService.findAllByCurrentSchool();
    }

    @RequestMapping(value = "/freemarker/teacher/schedule/classrooms-wp", method = RequestMethod.POST)
    public @ResponseBody List<ClassroomDTO> getAvailableClassroomBySearchCriteria(@RequestBody ClassroomSearchCriteria classroomSearchCriteria){
        log.debug("Create Ajax request to search available forms by search criteria");
        Validate.notNull(classroomSearchCriteria.getLessonPosition(), "Field 'lessonPosition' on classroomSearchCriteria can not be null.");
        Validate.notNull(classroomSearchCriteria.getDate(), "Field 'Date' on  classroomSearchCriteria can not be null.");
        if (classroomSearchCriteria.getClassroomId() != null) {
            return classroomService.findAvailablePlusOneById(classroomSearchCriteria);
        }
        return classroomService.findAvailableByCurrentSchoolAndSearchCriteria(classroomSearchCriteria);
    }

    @RequestMapping(value = "freemarker/teacher/schedule/schedule", method = RequestMethod.POST)
    public @ResponseBody List<ScheduleDTO> getScheduleBySearchCriteria(@RequestBody ScheduleSearchCriteria scheduleSearchCriteria){
        log.debug("Create Ajax request to search schedule by search criteria");
        Validate.notNull(scheduleSearchCriteria.getId(), "Field 'id' on scheduleSearchCriteria can not be null.");
        Validate.notNull(scheduleSearchCriteria.getScheduleFilterType(), "Field 'Schedule type' on scheduleSearchCriteria can not be null.");
        Validate.notNull(scheduleSearchCriteria.getDate(), "Field 'Date' on scheduleSearchCriteria can not be null.");
        return scheduleService.getScheduleBySearchCriteria(scheduleSearchCriteria);
    }

    @RequestMapping(value = "/freemarker/teacher/schedule/forms-wp", method = RequestMethod.POST)
    public @ResponseBody List<FormDTO> getAvailableFormsBySearchCriteria(@RequestBody FormSearchCriteria formSearchCriteria){
        log.debug("Create Ajax request to search available forms by search criteria");
        Validate.notNull(formSearchCriteria.getLessonPosition(), "Field 'lessonPosition' on formSearchCriteria can not be null.");
        Validate.notNull(formSearchCriteria.getDate(), "Field 'Date' on  formSearchCriteria can not be null.");
        if (formSearchCriteria.getFormId() != null) {
            return formService.findAllAvailablePlusOneById(formSearchCriteria);
        }
        return formService.findAvailableFormsByCurrentSchoolAndSearchCriteria(formSearchCriteria);
    }

    @RequestMapping(value = "/freemarker/teacher/schedule/schedule/{scheduleId}", method = RequestMethod.GET)
    public @ResponseBody ScheduleDTO getSchedulesById(@PathVariable("scheduleId") Long scheduleId) {
        log.debug("Request to get schedule by id : {}", scheduleId);
        return scheduleService.findOne(scheduleId);
    }
}
