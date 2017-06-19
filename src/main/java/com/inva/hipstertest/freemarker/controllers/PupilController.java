package com.inva.hipstertest.freemarker.controllers;

import com.inva.hipstertest.service.FormService;
import com.inva.hipstertest.service.PupilService;
import com.inva.hipstertest.service.ScheduleService;
import com.inva.hipstertest.service.dto.PupilDTO;
import com.inva.hipstertest.service.dto.ScheduleDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;
import java.util.List;

@Controller
@RequestMapping(value = "freemarker/")
public class PupilController {
    private final PupilService pupilService;
    private final ScheduleService scheduleService;
    private final FormService formService;

    public PupilController(PupilService pupilService, ScheduleService scheduleService, FormService formService) {
        this.pupilService = pupilService;
        this.scheduleService = scheduleService;
        this.formService = formService;
    }

    /**
     * @param model
     * @return
     */
    @RequestMapping(value = "pupil-home", method = RequestMethod.GET)
    public String index(@ModelAttribute("model") ModelMap model) {
        PupilDTO pupil = pupilService.findPupilByCurrentUser();
        List<ScheduleDTO> schedule = scheduleService.findAllByFormId(pupil.getFormId());
        model.addAttribute("currentPupil", pupil);
        model.addAttribute("mySchedule", schedule);
        return "pupil-home";
    }

    /**
     * @return
     */
    @RequestMapping("pupil-home/mySchedule/")
    public @ResponseBody
    List<ScheduleDTO> getListSchedulesByDate() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        List<ScheduleDTO> scheduleDTO = scheduleService.findByFormIdAndDate(zonedDateTime);
        return scheduleDTO;
    }

}
