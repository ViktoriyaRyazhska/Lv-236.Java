package com.inva.hipstertest.freemarker.controllers;

import com.inva.hipstertest.domain.Pupil;
import com.inva.hipstertest.service.*;
import com.inva.hipstertest.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(value = "freemarker/")
public class PupilController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final PupilService pupilService;
    private final ScheduleService scheduleService;
    private final LessonService lessonService;
    private final AttendancesService attendancesService;
    private final TeacherService teacherService;
    private final SchoolService schoolService;

    public PupilController(PupilService pupilService, ScheduleService scheduleService,
                           LessonService lessonService, AttendancesService attendancesService,
                           TeacherService teacherService, SchoolService schoolService) {
        this.pupilService = pupilService;
        this.scheduleService = scheduleService;
        this.lessonService = lessonService;
        this.attendancesService = attendancesService;
        this.teacherService = teacherService;
        this.schoolService = schoolService;
    }

    /**
     * Get name of Current pupil.
     *
     * @param model
     * @return hme page view (FTL).
     */
    @RequestMapping(value = "pupil-home", method = RequestMethod.GET)
    public String index(@ModelAttribute("model") ModelMap model) {
        log.debug("Request to get current pupil");
        PupilDTO currentUser = pupilService.findPupilByCurrentUser();

        Boolean schoolEnabled = schoolService.getSchoolStatus(schoolService.getSchoolIdByForm(currentUser.getFormId()));
        if (schoolEnabled == false) {
            model.addAttribute("currentUser", currentUser);
            return "schoolDisabledPage";
        }
        model.addAttribute("pupilFirstName", currentUser.getFirstName());
        model.addAttribute("pupilLastName", currentUser.getLastName());
        return "pupil-home";
    }

    /**
     * Get schedules by date.
     *
     * @param date requested date
     * @return the list of schedules.
     */
    @RequestMapping("pupil-home/mySchedule/{date}")
    public @ResponseBody
    List<ScheduleDTO> getListSchedulesByDate(@PathVariable String date) {
        log.debug("Request to get schedule for current pupil by date : {}", date);
        PupilDTO currentUser = pupilService.findPupilByCurrentUser();
        List<ScheduleDTO> scheduleDTO = scheduleService.findAllByFormIdAndDate(currentUser.getFormId(), date);
        return scheduleDTO;
    }

    /**
     * Get attendances by date.
     *
     * @param date requested date
     * @return te list of attendances.
     */
    @RequestMapping("pupil-home/myAttendances/{date}")
    public @ResponseBody
    List<AttendancesDTO> getListAttendancesByDate(@PathVariable String date) {
        log.debug("Request to get attendance for current pupil by date : {}", date);
        PupilDTO currentUser = pupilService.findPupilByCurrentUser();
        List<AttendancesDTO> attendancesDTOs = attendancesService.findAllByPupilIdAndDate(currentUser.getId(), date);
        return attendancesDTOs;
    }

    /**
     * Get teacher by id.
     *
     * @param id - ID of teacher
     * @return teacherDTO
     */
    @RequestMapping(value = "pupil-home/teacher", method = RequestMethod.POST)
    public @ResponseBody
    TeacherDTO editRequest(@RequestBody Long id) {
        log.debug("Request to get teacher {}", id);
        TeacherDTO teacherDTO = teacherService.findOne(id);
        return teacherDTO;
    }

    /**
     * Get list of lessons for select.
     *
     * @param model
     * @return The attendances view (FTL)
     */
    @RequestMapping(value = "pupil/attendances", method = RequestMethod.GET)
    public String getCurrentPupilAttendances(@ModelAttribute("model") ModelMap model) {
        log.debug("Request to get Attendances for current pupil");

        PupilDTO currentUser = pupilService.findPupilByCurrentUser();
        Boolean schoolEnabled = schoolService.getSchoolStatus(schoolService.getSchoolIdByForm(currentUser.getFormId()));
        if (schoolEnabled == false) {
            model.addAttribute("currentUser", currentUser);
            return "schoolDisabledPage";
        }
        model.addAttribute("pupilFirstName", currentUser.getFirstName());
        model.addAttribute("pupilLastName", currentUser.getLastName());
        model.addAttribute("lessons", lessonService.findAllByFormId(currentUser.getFormId()));

        return "attendances";
    }

    /**
     * Get list of lessons for select.
     *
     * @param lessonDTO with set id.
     * @return The List<AttendancesDTO> with attendance by choose id.lessons.
     */
    @RequestMapping(value = "pupil/att", method = RequestMethod.POST)
    public @ResponseBody
    List<AttendancesDTO> requestSome(@RequestBody LessonDTO lessonDTO) {
        log.debug("Create Ajax request for attendance by id lesson");
        PupilDTO pupilDTO = pupilService.findPupilByCurrentUser();
        List<AttendancesDTO> attendancesDTO =
            attendancesService.findAllByPupilIdAndLessonId(pupilDTO.getId(), lessonDTO.getId());
        Collections.sort(attendancesDTO, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        return attendancesDTO;
    }
}
