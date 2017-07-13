package com.inva.hipstertest.freemarker.controllers;

import com.inva.hipstertest.freemarker.pojo.ParentPagePOJO;
import com.inva.hipstertest.service.*;
import com.inva.hipstertest.service.dto.AttendancesDTO;
import com.inva.hipstertest.service.dto.LessonDTO;
import com.inva.hipstertest.service.dto.PupilDTO;
import com.inva.hipstertest.service.dto.ScheduleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.previousOrSame;

@Controller
public class ParentController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ParentService parentService;
    private final PupilService pupilService;
    private final ScheduleService scheduleService;
    private final LessonService lessonService;
    private final AttendancesService attendancesService;

    public ParentController(ParentService parentService, PupilService pupilService,
                            ScheduleService scheduleService, LessonService lessonService,
                            AttendancesService attendancesService) {
        this.parentService = parentService;
        this.pupilService = pupilService;
        this.scheduleService = scheduleService;
        this.lessonService = lessonService;
        this.attendancesService = attendancesService;
    }

    @RequestMapping(value = "freemarker/parent-home", method = RequestMethod.GET)
    public String pupilList(@ModelAttribute("model") ModelMap model) {
        Long parentId = parentService.findParentByCurrentUser().getId();
        List<PupilDTO> pupilList = pupilService.findAllByParentId(parentId);
        model.addAttribute("pupilList", pupilList);
        return "parent-home";
    }

    @RequestMapping(value = "freemarker/parent-home/schedule", method = RequestMethod.POST)
    public @ResponseBody
    List<ScheduleDTO> pupilSchedule(@RequestBody ParentPagePOJO parentPagePOJO){
        log.debug("Create ajax request for pupil schedule by pupil id and date: " + parentPagePOJO.getDate());
        ZonedDateTime prevSunday = parentPagePOJO.getDate().with(previousOrSame(DayOfWeek.SUNDAY));;
        ZonedDateTime nextSunday = prevSunday.plusDays(7);
        List<ScheduleDTO> scheduleDTOs = scheduleService.findAllByFormIdAndDateBetween(parentPagePOJO.getPupilFormId(), prevSunday, nextSunday);
        return scheduleDTOs;
    }

    @RequestMapping(value = "freemarker/parent-home/lessons", method = RequestMethod.POST)
    public @ResponseBody
    List<LessonDTO> pupilLessons(@RequestBody ParentPagePOJO parentPagePOJO){
        log.debug("Create ajax request for pupil lessons");
        List<LessonDTO> lessonDTOs = lessonService.findAllByFormId(parentPagePOJO.getPupilFormId());
        Collections.sort(lessonDTOs, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        return lessonDTOs;
    }

    @RequestMapping(value = "freemarker/parent-home/attendance", method = RequestMethod.POST)
    public @ResponseBody
    List<AttendancesDTO> pupilAttendance(@RequestBody ParentPagePOJO parentPagePOJO){
        log.debug("Create ajax request for pupil attendance by lesson id");
        List<AttendancesDTO> attendancesDTO = attendancesService.findAllByPupilIdAndLessonId(parentPagePOJO.getPupilId(), parentPagePOJO.getLessonId());
        Collections.sort(attendancesDTO, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        return attendancesDTO;
    }

}
