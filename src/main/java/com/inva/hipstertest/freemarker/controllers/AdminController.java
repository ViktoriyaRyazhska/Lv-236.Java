package com.inva.hipstertest.freemarker.controllers;

import com.codahale.metrics.annotation.Timed;
import com.inva.hipstertest.domain.User;
import com.inva.hipstertest.service.SchoolService;
import com.inva.hipstertest.service.TeacherService;
import com.inva.hipstertest.service.UserService;
import com.inva.hipstertest.service.dto.SchoolDTO;
import com.inva.hipstertest.service.dto.TeacherDTO;
import com.inva.hipstertest.service.dto.UserDTO;
import com.inva.hipstertest.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URISyntaxException;
import java.util.List;

@Controller
public class AdminController {
    private final Logger log = LoggerFactory.getLogger(HeadTeacherController.class);
    private final SchoolService schoolService;
    private final TeacherService teacherService;
    private final UserMapper userMapper;
    private final UserService userService;


    public AdminController(SchoolService schoolService, TeacherService teacherService, UserMapper userMapper,
                           UserService userService) {
        this.schoolService = schoolService;
        this.teacherService = teacherService;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @RequestMapping(value = "freemarker/admin-home", method = RequestMethod.GET)
    public String index(Model model, Pageable pageable) {
        User user = userService.findByLoginUserId();
        UserDTO userDTO = userMapper.userToUserDTO(user);
        Page<SchoolDTO> page = schoolService.findAllEnabled(pageable);
        model.addAttribute("schools", page.getContent());
        model.addAttribute("sizes", pageable.getPageSize());
        model.addAttribute("current", pageable.getPageNumber());
        model.addAttribute("longs", enabledPages(pageable.getPageSize()));
        model.addAttribute("currentUser", userDTO);
        return "admin/admin-home";
    }

    public long enabledPages(int size) {
        long all = schoolService.countAllEnabledSchools();
        long realPage = all / size;
        if (all % size == 0) {
            return realPage;
        }
        return realPage + 1;
    }

    @RequestMapping(value = "freemarker/admin-home/deletedSchool", method = RequestMethod.GET)
    public String dataForDelete(Model model, Pageable pageable) {
        Page<SchoolDTO> page = schoolService.findAllDisabled(pageable);
        User user = userService.findByLoginUserId();
        UserDTO userDTO = userMapper.userToUserDTO(user);
        model.addAttribute("disabledSchools", page.getContent());
        model.addAttribute("sizes", pageable.getPageSize());
        model.addAttribute("current", pageable.getPageNumber());
        model.addAttribute("longs", disabledPages(pageable.getPageSize()));
        model.addAttribute("currentUser", userDTO);
        return "admin/admin-home-deleted-school";
    }


    public long disabledPages(int size) {
        long all = schoolService.countAllDisabledSchools();
        long realPage = all / size;
        if (all % size == 0) {
            return realPage;
        }
        return realPage + 1;
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
     * Creates new school.
     */
    @PostMapping(value = "/freemarker/admin-home/createSchool")
    @Timed
    public String addSchool(@ModelAttribute("schoolDTO") SchoolDTO schoolDTO) {
        if (schoolDTO.getName() != null && !schoolDTO.getName().isEmpty() &&
            schoolDTO.getEnabled() != null) {
            schoolService.save(schoolDTO);
            return "redirect:";
        } else {
            return "redirect:error";
        }
    }


    @RequestMapping(value = "/freemarker/admin-home/createHeadTeacher/{schoolId}", method = RequestMethod.GET)
    public ModelAndView adminNewHeadTeacher(@PathVariable Long schoolId) {
        return new ModelAndView("admin/admin-home-create-headTeacher");
    }


    /**
     * Creates new head teacher in school.
     */
    @PostMapping(value = "/freemarker/admin-home/createHeadTeacher/{schoolId}")
    @Timed
    public ModelAndView adminCreateNewHeadTeacher(TeacherDTO teacherDTO, @PathVariable Long schoolId, BindingResult bindingResult, String emailFail) throws URISyntaxException {
        log.debug("Freemarker request to save headTeacher : {}", teacherDTO);
        log.debug(teacherDTO.getFirstName() + " " + teacherDTO.getLastName() + " " + teacherDTO.getEmail());
        TeacherDTO result = teacherService.saveHeadTeacherWithUser(teacherDTO, schoolId);
        emailFail = "Invalid e-mail";
        if (!result.getEnabled()) {
            // handle email already in use
            return new ModelAndView("admin/admin-home-create-headTeacher", "emailFail", emailFail);
        }
        // handle creation success
        return new ModelAndView("redirect:/freemarker/admin-home");
    }


    /**
     * Toggles schools's "enabled" field.
     *
     * @param id school to toggle
     */
    @RequestMapping(value = "/freemarker/admin-home/school-toggle/{id}", method = RequestMethod.GET)
    public ModelAndView schoolDisable(@ModelAttribute("model") ModelMap model, @PathVariable Long id) {
        log.debug("Request to toggle school" + id);
        SchoolDTO schoolToToggle = schoolService.findOne(id);
        if (schoolToToggle.getEnabled()) {
            schoolToToggle.setEnabled(false);
            schoolService.save(schoolToToggle);
            return new ModelAndView("redirect:/freemarker/admin-home");

        } else {
            schoolToToggle.setEnabled(true);
            schoolService.save(schoolToToggle);
            return new ModelAndView("redirect:/freemarker/admin-home/deletedSchool");
        }


    }


    /**
     * Request to get all head teachers of school
     *
     * @return available forms
     */
    @RequestMapping(value = "freemarker/admin-home/headTeachersOfSchool/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<TeacherDTO> getHeadTeachers(@PathVariable Long id) {
        log.debug("Create Ajax request for headTeachers");
        List<TeacherDTO> headTeachers = schoolService.findHeadTeachersOfSchool(id);
        return headTeachers;
    }


    /**
     * Make teacher with "id" head teacher.
     *
     * @param id teacher
     */
    @RequestMapping(value = "/freemarker/admin-home/makeHeadTeacher/{id}", method = RequestMethod.GET)
    public ModelAndView makeHeadTeacher(@ModelAttribute("model") ModelMap model, @PathVariable Long id) {
        log.debug("Request make teacher head teacher" + id);

        teacherService.makeHeadTeacher(id);
        return new ModelAndView("redirect:/freemarker/admin-home");

    }

    /**
     * Unmake teacher with "id" head teacher.
     *
     * @param id teacher
     */
    @RequestMapping(value = "/freemarker/admin-home/unMakeHeadTeacher/{id}", method = RequestMethod.GET)
    public ModelAndView unMakeHeadTeacher(@ModelAttribute("model") ModelMap model, @PathVariable Long id) {
        log.debug("Request to unmake teacher head teacher" + id);

        teacherService.unMakeHeadTeacher(id);
        return new ModelAndView("redirect:/freemarker/admin-home");

    }

}

