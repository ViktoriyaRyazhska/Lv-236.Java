package com.inva.hipstertest.freemarker.controllers;

import com.codahale.metrics.annotation.Timed;
import com.inva.hipstertest.domain.School;
import com.inva.hipstertest.service.SchoolService;
import com.inva.hipstertest.service.TeacherService;
import com.inva.hipstertest.service.UserAddonService;
import com.inva.hipstertest.service.dto.FormDTO;
import com.inva.hipstertest.service.dto.SchoolDTO;
import com.inva.hipstertest.service.dto.TeacherDTO;
import com.inva.hipstertest.service.dto.UserAddonDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    private final Logger log = LoggerFactory.getLogger(HeadTeacherController.class);
    private final SchoolService schoolService;
    private final TeacherService teacherService;


    private final UserAddonService userAddonService;


    public AdminController(SchoolService schoolService, TeacherService teacherService, UserAddonService userAddonService) {
        this.schoolService = schoolService;
        this.teacherService = teacherService;
        this.userAddonService = userAddonService;
    }

    @RequestMapping(value = "freemarker/admin-home", method = RequestMethod.GET)
    public String index(@ModelAttribute("model") ModelMap model) {
        UserAddonDTO user = userAddonService.findByCurrentUser();
        List<SchoolDTO> schoolList = new ArrayList<SchoolDTO>();
        schoolList = schoolService.findAll();
        //List<TeacherDTO> teachers = teacherService.getAllBySchoolId(schoolId);
       // List<TeacherDTO> headTeachers = schoolService.findHeadTeachersOfSchool(schoolId);
        model.addAttribute("schoolList", schoolList);
        model.addAttribute("currentUser", user);
       // model.addAttribute("teachersList", teachers);
       // model.addAttribute("headTeachers", headTeachers);
        return "admin/admin-home";
    }

    /**
     * Add a new School
     *
     * @param schoolDTO
     * @return Redirect back to same /freemarkertest page to display school list, if successful
     */
    @RequestMapping(value = "freemarker/admin-home/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("schoolDTO") SchoolDTO schoolDTO) {
        if (schoolDTO.getName() != null && !schoolDTO.getName().isEmpty() &&
            schoolDTO.getEnabled() != null) {
            schoolService.save(schoolDTO);
            return "redirect:";
        } else {
            return "redirect:error"; //TODO: create error page
        }
    }

    /**
     * Get list of teachers.
     *
     * @param model
     * @param schoolId
     * @return The index view (FTL)
     */
    @RequestMapping(value = "freemarker/admin-home/details/{schoolId}", method = RequestMethod.GET)
    public String index(@ModelAttribute("model") ModelMap model, @PathVariable Long schoolId) {
        List<TeacherDTO> teachers = teacherService.getAllBySchoolId(schoolId);
        String schoolName = schoolService.findOne(schoolId).getName();
        List<TeacherDTO> headTeachers = schoolService.findHeadTeachersOfSchool(schoolId);
        model.addAttribute("teachersList", teachers);
        model.addAttribute("schoolId", schoolId);
        model.addAttribute("schoolName", schoolName);
        model.addAttribute("headTeachers", headTeachers);
        return "admin/admin-home-details";
    }


    @RequestMapping(value = "/freemarker/admin-home/createSchool", method = RequestMethod.GET)
    public ModelAndView adminNewSchool() {
        SchoolDTO schoolDTO = new SchoolDTO();
        return new ModelAndView("admin/admin-home-create-school", "schoolDTO", schoolDTO);
    }

    /**
     * Creates new teacher in school.
     */
    @PostMapping(value = "/freemarker/admin-home/createSchool")
    @Timed
    public String addSchool(@ModelAttribute("schoolDTO") SchoolDTO schoolDTO) {
        if (schoolDTO.getName() != null && !schoolDTO.getName().isEmpty() &&
            schoolDTO.getEnabled() != null) {
            schoolService.save(schoolDTO);
            return "redirect:";
        } else {
            return "redirect:error"; //TODO: create error page
        }
    }

    @RequestMapping(value = "/freemarker/admin-home/createHeadTeacher/{schoolId}", method = RequestMethod.GET)
    public ModelAndView adminNewHeadTeacher(@PathVariable Long schoolId) {

        return new ModelAndView("admin/admin-home-create-headTeacher");
    }


    /**
     * Creates new head teacher in school.
     *
     */
    @PostMapping(value = "/freemarker/admin-home/createHeadTeacher/{schoolId}")
    @Timed
    public ModelAndView adminCreateNewHeadTeacher(TeacherDTO teacherDTO,@PathVariable Long schoolId, BindingResult bindingResult, String emailFail) throws URISyntaxException {
        log.debug("Freemarker request to save headTeacher : {}", teacherDTO);
        log.debug(teacherDTO.getFirstName() + " " + teacherDTO.getLastName() + " " + teacherDTO.getEmail());
        TeacherDTO result = teacherService.saveHeadTeacherWithUser(teacherDTO,schoolId );
        emailFail = "Invalid e-mail";
        if(!result.getEnabled()){
            // handle email already in use
            return new ModelAndView("admin/admin-home-create-headTeacher", "emailFail", emailFail);
        }
        // handle creation success
        return new ModelAndView("redirect:/freemarker/admin-home");
    }


    /**
     * Toggles schools's "enabled" field.
     * @param id school to toggle
     */
    @RequestMapping(value = "/freemarker/admin-home/school-toggle/{id}", method = RequestMethod.GET)
    public ModelAndView schoolDisable(@ModelAttribute("model") ModelMap model, @PathVariable Long id){
        SchoolDTO schoolToToggle = schoolService.findOne(id);
            if(schoolToToggle.getEnabled()){
                schoolToToggle.setEnabled(false);
            } else {
                schoolToToggle.setEnabled(true);
            }
            schoolService.save(schoolToToggle);
            return new ModelAndView("redirect:/freemarker/admin-home");

    }



    /**
     * Request to get all head teachers of school
     * @return available forms
     */
    @RequestMapping(value = "freemarker/admin-home/headTeachersOfSchool/{id}", method = RequestMethod.GET)
    public @ResponseBody List<TeacherDTO> getHeadTeachers(@PathVariable Long id){
        log.debug("Create Ajax request for headTeachers");
        List<TeacherDTO> headTeachers = schoolService.findHeadTeachersOfSchool(id);
        return headTeachers;
    }
}

