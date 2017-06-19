package com.inva.hipstertest.freemarker.controllers;

import com.inva.hipstertest.service.*;
import com.inva.hipstertest.service.dto.AttendancesDTO;
import com.inva.hipstertest.service.dto.LessonDTO;
import com.inva.hipstertest.service.dto.PupilDTO;
import com.inva.hipstertest.service.dto.ScheduleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
public class PupilController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final PupilService pupilService;
    private final ScheduleService scheduleService;
    private final FormService formService;
    private final LessonService lessonService;
    private final AttendancesService attendancesService;

    public PupilController(PupilService pupilService, ScheduleService scheduleService,
                           FormService formService, LessonService lessonService,
                           AttendancesService attendancesService) {
        this.pupilService = pupilService;
        this.scheduleService = scheduleService;
        this.formService = formService;
        this.lessonService = lessonService;
        this.attendancesService = attendancesService;
    }

    /**
     * Saves the static list of users in model and renders it
     * via freemarker template.
     *
     * @param model
     * @return The index view (FTL)
     */
    @RequestMapping(value = "freemarker/pupil-home", method = RequestMethod.GET)
    public String index(@ModelAttribute("model") ModelMap model) {
        PupilDTO pupil = pupilService.findPupilByCurrentUser();
        List<ScheduleDTO> schedule = scheduleService.findAllByFormId(pupil.getFormId());
        model.addAttribute("currentPupil", pupil);
        model.addAttribute("mySchedule", schedule);
        return "pupil-home";
    }

    /**
     * Get list of lessons for select.
     *
     * @param model
     * @return The attendances view (FTL)
     */
    @RequestMapping(value = "freemarker/pupil/attendances", method = RequestMethod.GET)
    public String getCurrentPupilAttendances(Model model){
        log.debug("Request to get Attendances for current pupil");
        PupilDTO pupilDTO = pupilService.findPupilByCurrentUser();
        model.addAttribute("lessons",lessonService.getDistinctLessonsForForm(pupilDTO.getFormId()));
        model.addAttribute("pupilFirstName",pupilDTO.getFirstName());
        model.addAttribute("pupilLastName",pupilDTO.getLastName());
        return "attendances";
    }

    /**
     * Get list of lessons for select.
     *
     * @param lessonDTO with set id.
     * @return The List<AttendancesDTO> with attendance by choose id.lessons.
     */
    @RequestMapping(value = "freemarker/pupil/att", method = RequestMethod.POST)
    public @ResponseBody List<AttendancesDTO> requestSome(@RequestBody LessonDTO lessonDTO){
        log.debug("Create Ajax request for attendance by id lesson");
        PupilDTO pupilDTO = pupilService.findPupilByCurrentUser();
        List<AttendancesDTO> attendancesDTO =
            attendancesService.findAllByPupilAndLessonId(pupilDTO.getId(),lessonDTO.getId());
        Collections.sort(attendancesDTO, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        return attendancesDTO;
    }


}
