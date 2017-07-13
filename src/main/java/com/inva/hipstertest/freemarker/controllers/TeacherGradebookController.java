package com.inva.hipstertest.freemarker.controllers;

import com.inva.hipstertest.service.*;
import com.inva.hipstertest.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class TeacherGradebookController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final TeacherService teacherService;
    private final ScheduleService scheduleService;
    private final PupilService pupilService;
    private final AttendancesService attendancesService;
    private final SchoolService schoolService;
    private final FormService formService;
    private final LessonService lessonService;

    public TeacherGradebookController(TeacherService teacherService, ScheduleService scheduleService,
                                      PupilService pupilService, AttendancesService attendancesService,
                                      SchoolService schoolService, FormService formService,
                                      LessonService lessonService) {
        this.teacherService = teacherService;
        this.scheduleService = scheduleService;
        this.pupilService = pupilService;
        this.attendancesService = attendancesService;
        this.schoolService = schoolService;
        this.formService = formService;
        this.lessonService = lessonService;
    }

    @RequestMapping(value = {"/freemarker/teacher-gradebook", "/freemarker/teacher-gradebook/{formId}/{lessonId}"}, method = RequestMethod.GET)
    public String gradebook(@ModelAttribute("model") ModelMap model, @PageableDefault(value = 10) Pageable pageable, @PathVariable Optional<Long> formId, @PathVariable Optional<Long> lessonId) {
        TeacherDTO teacher = teacherService.findTeacherByCurrentUser();
        model.addAttribute("teacher", teacher);

        log.debug("request to get school status by current user");
        Boolean schoolEnabled = schoolService.getSchoolStatus(teacher.getSchoolId());
        if (schoolEnabled == false){
            model.addAttribute("currentUser", teacher);
            return "schoolDisabledPage";
        }

        List<ScheduleDTO> formsAndLessons = scheduleService.findFormsAndLessonsByTeacherId(teacher.getId());

        if(formsAndLessons.isEmpty()) {
            model.addAttribute("error", 1);
            return "teacher-gradebook";
        }

        if(!formId.isPresent() || !lessonId.isPresent()) {
            return "redirect:/freemarker/teacher-gradebook/" + formsAndLessons.get(0).getFormId() + "/" + formsAndLessons.get(0).getLessonId();
        }

        model.addAttribute("formsAndLessons", formsAndLessons);
        model.addAttribute("form", formService.findOne(formId.get()));
        model.addAttribute("lesson", lessonService.findOne(lessonId.get()));

        Page<ScheduleDTO> page = scheduleService.findAllByFormIdLessonIdMaxDate(pageable, formId.get(), lessonId.get(), ZonedDateTime.now());

        if(!page.hasContent()) {
            model.addAttribute("error", 2);
            return "teacher-gradebook";
        }

        List<PupilDTO> pupils = pupilService.findAllByFormId(formId.get());
        List<AttendancesDTO> attendances = attendancesService.findAllByFormIdAndLessonId(formId.get(), lessonId.get());
        Comparator<PupilDTO> comparatorLastNameFirstName = Comparator.comparing(PupilDTO::getLastName).thenComparing(PupilDTO::getFirstName);

        Collections.sort(formsAndLessons, (o1, o2) -> o1.getLessonName().compareTo(o2.getLessonName()));
        Collections.sort(pupils, comparatorLastNameFirstName);

        model.addAttribute("pupils", pupils);
        model.addAttribute("attendances", attendances);
        model.addAttribute("schedules", page.getContent());
        model.addAttribute("sizes", pageable.getPageSize());
        model.addAttribute("current", pageable.getPageNumber());
        model.addAttribute("longs", pages(pageable.getPageSize(), formId.get(), lessonId.get(), ZonedDateTime.now()));

        return "teacher-gradebook";
    }

    public long pages(int size, Long formId, Long lessonId, ZonedDateTime maxDate) {
        long all = scheduleService.countAllByFormIdLessonIdMaxDate(formId, lessonId, maxDate);
        long realPage = all/size;
        if(all % size == 0){
            return realPage;
        }
        return realPage + 1;
    }

    @RequestMapping(value = "/freemarker/teacher-gradebook/update", method = RequestMethod.POST)
    public @ResponseBody
    AttendancesDTO updateSchedule(@RequestBody AttendancesDTO attendances) {
        log.debug("REST request to create/update Schedule : {}", attendances);

//        TeacherDTO teacher = teacherService.findTeacherByCurrentUser();
//        if(teacher.getId() != attendancesDTO.getTeacherId()) {
//            return null;
//        }
//        if ((attendances.getGrade() >= 0 && attendances.getGrade() <= 12) || attendances.getGrade() == null) {
//            return null;
//        }

        AttendancesDTO newAttendances = attendancesService.save(attendances);
        return newAttendances;
    }

}
