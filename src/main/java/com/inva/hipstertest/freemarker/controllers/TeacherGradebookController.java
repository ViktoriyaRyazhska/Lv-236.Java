package com.inva.hipstertest.freemarker.controllers;

import com.codahale.metrics.annotation.Timed;
import com.inva.hipstertest.domain.Form;
import com.inva.hipstertest.repository.PupilRepository;
import com.inva.hipstertest.service.*;
import com.inva.hipstertest.service.dto.*;
import com.inva.hipstertest.service.mapper.FormMapper;
import com.inva.hipstertest.service.mapper.PupilMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.*;

@Controller
public class TeacherGradebookController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final TeacherService teacherService;
    private final ScheduleService scheduleService;
    private final PupilService pupilService;
    private final AttendancesService attendancesService;
    private final SchoolService schoolService;
    private final FormService formService;
    private final FormMapper formMapper;
    private final PupilRepository pupilRepository;
    private final PupilMapper pupilMapper;

    public TeacherGradebookController(TeacherService teacherService, ScheduleService scheduleService, PupilService pupilService,
                                      AttendancesService attendancesService, SchoolService schoolService, FormService formService,
                                      FormMapper formMapper, PupilRepository pupilRepository, PupilMapper pupilMapper) {
        this.teacherService = teacherService;
        this.scheduleService = scheduleService;
        this.pupilService = pupilService;
        this.attendancesService = attendancesService;
        this.schoolService = schoolService;
        this.formService = formService;
        this.formMapper = formMapper;
        this.pupilRepository = pupilRepository;
        this.pupilMapper = pupilMapper;
    }

    @RequestMapping(value = "/freemarker/teacher-gradebook", method = RequestMethod.GET)
    public ModelAndView home(@ModelAttribute("model") ModelMap model) {
        TeacherDTO teacher = teacherService.findTeacherByCurrentUser();
        log.debug("request to get school status by current user");
        Boolean schoolEnabled = schoolService.getSchoolStatus(teacher.getSchoolId());
        if (schoolEnabled == false) {
            model.addAttribute("currentUser", teacher);
            return new ModelAndView("schoolDisabledPage");
        }
        List<ScheduleDTO> formsAndLessons = scheduleService.findFormsAndLessonsByTeacherId(teacher.getId());
        if (formsAndLessons.isEmpty()) {
            return new ModelAndView("redirect:/freemarker/error");
        }
        Collections.sort(formsAndLessons, (o1, o2) -> o1.getFormName().compareTo(o2.getFormName()));
        ScheduleDTO formAndLesson = formsAndLessons.get(0);
        return new ModelAndView("redirect:/freemarker/teacher-gradebook/" + formAndLesson.getFormId() + "/" + formAndLesson.getLessonId());
    }

    @RequestMapping(value = "/freemarker/teacher-gradebook/{formId}/{lessonId}", method = RequestMethod.GET)
    public String gradebook(@ModelAttribute("model") ModelMap model, @PageableDefault(value = 10) Pageable pageable, @PathVariable Long formId, @PathVariable Long lessonId) {
        TeacherDTO teacher = teacherService.findTeacherByCurrentUser();
        log.debug("request to get school status by current user");
        Boolean schoolEnabled = schoolService.getSchoolStatus(teacher.getSchoolId());
        if (schoolEnabled == false) {
            model.addAttribute("currentUser", teacher);
            return "schoolDisabledPage";
        }
        List<ScheduleDTO> formsAndLessons = scheduleService.findFormsAndLessonsByTeacherId(teacher.getId());
        if (formsAndLessons.isEmpty()) {
            return "redirect:/freemarker/error";
        }
        ScheduleDTO formAndLesson = null;

        for (ScheduleDTO item : formsAndLessons) {
            if (item.getFormId() == formId && item.getLessonId() == lessonId) {
                formAndLesson = item;
            }
        }

        Page<ScheduleDTO> page = scheduleService.findSchedulesByTeacherIdFormIdSubjectIdMaxDate(pageable, teacher.getId(), formId, lessonId, ZonedDateTime.now());
        List<PupilDTO> pupilDTOs = pupilService.findAllByFormId(formId);
        Comparator<PupilDTO> comparatorLastNameFirstName = Comparator.comparing(PupilDTO::getLastName).thenComparing(PupilDTO::getFirstName);
        List<AttendancesDTO> attendancesDTOs = attendancesService.findAllWherePupilIdInAndScheduleIdIn(teacher.getId(), formId, lessonId);

        Collections.sort(formsAndLessons, (o1, o2) -> o1.getFormName().compareTo(o2.getFormName()));
        Collections.sort(pupilDTOs, comparatorLastNameFirstName);

        model.addAttribute("teacher", teacher.getFirstName() + " " + teacher.getLastName());
        model.addAttribute("formsAndLessons", formsAndLessons);
        model.addAttribute("formAndLesson", formAndLesson);
        model.addAttribute("pupils", pupilDTOs);
        model.addAttribute("attendances", attendancesDTOs);
        model.addAttribute("schedules", page.getContent());
        model.addAttribute("sizes", pageable.getPageSize());
        model.addAttribute("current", pageable.getPageNumber());
        model.addAttribute("longs", pages(pageable.getPageSize(), teacher.getId(), formId, lessonId, ZonedDateTime.now()));
//        model.addAttribute("formId", formId);
//        model.addAttribute("lessonId", lessonId);

        return "teacher-gradebook";
    }

    public long pages(int size, Long teacherId, Long formId, Long lessonId, ZonedDateTime today) {
        long all = scheduleService.countSchedulesForGradeBook(teacherId, formId, lessonId, today);
        long realPage = all / size;
        if (all % size == 0) {
            return realPage;
        }
        return realPage + 1;
    }

    @RequestMapping(value = "/freemarker/teacher-gradebook/update", method = RequestMethod.POST)
    public @ResponseBody
    AttendancesDTO updateSchedule(@RequestBody AttendancesDTO attendancesDTO) {
        log.debug("REST request to create/update Schedule : {}", attendancesDTO);
        attendancesService.save(attendancesDTO);
        return attendancesDTO;
    }


    @RequestMapping(value = "freemarker/teacher-my-class", method = RequestMethod.GET)
    public String myClass(@ModelAttribute("model") ModelMap model) {
        TeacherDTO teacher = teacherService.findTeacherByCurrentUser();
        log.debug("request to get school status by current user");
        Boolean schoolEnabled = schoolService.getSchoolStatus(teacher.getSchoolId());
        if (schoolEnabled == false) {
            model.addAttribute("currentUser", teacher);
            return "schoolDisabledPage";
        }
        Form form = formMapper.formDTOToForm(formService.findFormByTeacherId(teacher.getId()));
        if (form == null) {
            model.addAttribute("currentUser", teacher);
            return "teacherHaveNoClassPage";
        }

        String formName = form.getName();

        List<PupilDTO> pupils = pupilMapper.pupilsToPupilDTOs(pupilRepository.findAllByFormId(form.getId()));
        Comparator<PupilDTO> comparatorLastNameFirstName = Comparator.comparing(PupilDTO::getLastName).thenComparing(PupilDTO::getFirstName);
        Collections.sort(pupils, comparatorLastNameFirstName);
        model.addAttribute("currentUser", teacher);
        model.addAttribute("formName", formName);
        model.addAttribute("pupils", pupils);
        return "teacher-mgmt/teacher-my-class";
    }

    @RequestMapping(value = "freemarker/teacher-my-class/newPupil/{formId}", method = RequestMethod.GET)
    public ModelAndView teacherNewPupil( @PathVariable Long formId) {

        return new ModelAndView("teacher-mgmt/teacher-my-class-createNewPupil");
    }


    /**
     * Creates new pupil in form.
     */
    @PostMapping(value = "freemarker/teacher-my-class/newPupil/{formId}")
    @Timed
    public ModelAndView teacherCreatePupil(@ModelAttribute("model") ModelMap model, PupilDTO pupilDTO, @PathVariable Long formId, BindingResult bindingResult, String emailFail) throws URISyntaxException {
        log.debug("Freemarker request to save pupil : {}", pupilDTO);
        log.debug(pupilDTO.getFirstName() + " " + pupilDTO.getLastName() + " " + pupilDTO.getEmail());
        PupilDTO result = pupilService.savePupilWithUser(pupilDTO, formId);
        emailFail = "Invalid e-mail";

        if (!result.getEnabled()) {
            // handle email already in use
            return new ModelAndView("teacher-mgmt/teacher-my-class-createNewPupil", "emailFail", emailFail);
        }
        // handle creation success
        return new ModelAndView("redirect:/freemarker/teacher-my-class");
    }


    /**
     * Request to receive complete pupilDTO for further editing
     *
     * @param id - ID of pupil to Edit
     * @return pupilDTO
     */
    @RequestMapping(value = "freemarker/teacher-my-class-edit", method = RequestMethod.POST)
    public @ResponseBody
    PupilDTO editRequest(@RequestBody Long id) {
        log.debug("Create Ajax edit request");
        return pupilService.findOne(id);
    }


    @RequestMapping(value = "freemarker/teacher-my-class-savePupil", method = RequestMethod.POST)
    public @ResponseBody
    String saveRequest(@RequestBody @Valid PupilDTO pupilDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Error";
        }
        pupilService.saveEditedPupil(pupilDTO);
        return "Success";
    }

    /**
     * Request to get  forms in school
     *
     * @return available forms
     */
    @RequestMapping(value = "freemarker/teacher-my-class-get-av-forms", method = RequestMethod.GET)
    public @ResponseBody
    List<FormDTO> getAvailableForms() {
        log.debug("Create Ajax request for forms");
        return formService.findAllFormsByCurrentSchool();
    }
}
