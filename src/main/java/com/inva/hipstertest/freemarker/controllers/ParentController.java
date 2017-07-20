package com.inva.hipstertest.freemarker.controllers;

import com.inva.hipstertest.freemarker.searchcriteria.PupilSearchCriteria;
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
import java.time.temporal.ChronoUnit;
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
    List<ScheduleDTO> pupilSchedule(@RequestBody PupilSearchCriteria pupilSearchCriteria){
        log.debug("Create ajax request for pupil schedule by pupil id and date: " + pupilSearchCriteria.getDate());
        ZonedDateTime startDay = pupilSearchCriteria.getDate().truncatedTo(ChronoUnit.DAYS).with(previousOrSame(DayOfWeek.SUNDAY));;
        ZonedDateTime endDate = startDay.plusDays(6);
        List<ScheduleDTO> scheduleDTOs = scheduleService.findAllByFormIdAndDateBetween(pupilSearchCriteria.getPupilFormId(), startDay, endDate);
        return scheduleDTOs;
    }

    @RequestMapping(value = "freemarker/parent-home/lessons", method = RequestMethod.POST)
    public @ResponseBody
    List<LessonDTO> pupilLessons(@RequestBody PupilSearchCriteria pupilSearchCriteria){
        log.debug("Create ajax request for pupil lessons");
        List<LessonDTO> lessonDTOs = lessonService.getDistinctLessonsForForm(pupilSearchCriteria.getPupilFormId());
        Collections.sort(lessonDTOs, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        return lessonDTOs;
    }

    @RequestMapping(value = "freemarker/parent-home/attendance", method = RequestMethod.POST)
    public @ResponseBody
    List<AttendancesDTO> pupilAttendance(@RequestBody PupilSearchCriteria pupilSearchCriteria){
        log.debug("Create ajax request for pupil attendance by lesson id");
        List<AttendancesDTO> attendancesDTO = attendancesService.findAllByPupilAndLessonId(pupilSearchCriteria.getPupilId(), pupilSearchCriteria.getLessonId());
        Collections.sort(attendancesDTO, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        return attendancesDTO;
    }

}
