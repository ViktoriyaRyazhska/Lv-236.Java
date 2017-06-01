package com.inva.hipstertest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.inva.hipstertest.domain.Authority;
import com.inva.hipstertest.domain.User;
import com.inva.hipstertest.repository.TeacherRepository;
import com.inva.hipstertest.repository.UserRepository;
import com.inva.hipstertest.service.MailService;
import com.inva.hipstertest.service.TeacherService;
import com.inva.hipstertest.service.dto.UserDTO;
import com.inva.hipstertest.web.rest.util.HeaderUtil;
import com.inva.hipstertest.service.dto.TeacherDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * REST controller for managing Teacher.
 */
@RestController
@RequestMapping("/api")
public class TeacherResource {

    private final Logger log = LoggerFactory.getLogger(TeacherResource.class);

    private static final String ENTITY_NAME = "teacher";

    private final TeacherService teacherService;

    @Autowired
    private MailService mailService;
    @Autowired
    private UserRepository userRepository;

    public TeacherResource(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    /**
     * POST  /teachers : Create a new teacher.
     *
     * @param teacherDTO the teacherDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new teacherDTO, or with status 400 (Bad Request) if the teacher has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/teachers")
    @Timed
    public ResponseEntity<TeacherDTO> createTeacher(@Valid @RequestBody TeacherDTO teacherDTO) throws URISyntaxException {
        log.debug("REST request to save Teacher : {}", teacherDTO);
        if (teacherDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new teacher cannot already have an ID")).body(null);
        }
        TeacherDTO result = teacherService.save(teacherDTO);
        return ResponseEntity.created(new URI("/api/teachers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /teachers : Updates an existing teacher.
     *
     * @param teacherDTO the teacherDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated teacherDTO,
     * or with status 400 (Bad Request) if the teacherDTO is not valid,
     * or with status 500 (Internal Server Error) if the teacherDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/teachers")
    @Timed
    public ResponseEntity<TeacherDTO> updateTeacher(@Valid @RequestBody TeacherDTO teacherDTO) throws URISyntaxException {
        log.debug("REST request to update Teacher : {}", teacherDTO);
        if (teacherDTO.getId() == null) {
            return createTeacher(teacherDTO);
        }
        TeacherDTO result = teacherService.save(teacherDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, teacherDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /teachers : get all the teachers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of teachers in body
     */
    @GetMapping("/teachers")
    @Timed
    public List<TeacherDTO> getAllTeachers() {
        log.debug("REST request to get all Teachers");
        return teacherService.findAll();
    }

    /**
     * GET  /teachers/:id : get the "id" teacher.
     *
     * @param id the id of the teacherDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the teacherDTO, or with status 404 (Not Found)
     */
    @GetMapping("/teachers/{id}")
    @Timed
    public ResponseEntity<TeacherDTO> getTeacher(@PathVariable Long id, Principal principal) {
        log.debug("REST request to get Teacher : {}", id);
        TeacherDTO teacherDTO = teacherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(teacherDTO));
    }

    /**
     * GET  /teachers/current : get current teacher.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the current teacherDTO, or with status 404 (Not Found)
     */
    @GetMapping("/teachers/current")
    @Timed
    public ResponseEntity<TeacherDTO> getCurrentTeacher() {
        log.debug("REST request to get current Teacher");
        TeacherDTO teacherDTO = teacherService.findTeacherByCurrentUser();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(teacherDTO));
    }

    /**
     * DELETE  /teachers/:id : delete the "id" teacher.
     *
     * @param id the id of the teacherDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/teachers/{id}")
    @Timed
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        log.debug("REST request to delete Teacher : {}", id);
        teacherService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    /**
     * GET  /teachers : get all the teachers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of teachers in body
     */
    @GetMapping("/headteacher-management")
    @Timed
    public List<TeacherDTO> getAllTeachersForMe() {
        log.debug("REST request to get all Teachers");
        return teacherService.findAll();

        /*
        Optional<User> s = userRepository.findOneWithAuthoritiesByLogin(principal.getName());
        Set<Authority> set = s.get().getAuthorities();
        Long ssss = userRepository.findByLoginUserId(principal.getName());
        System.out.println(ssss + " //////////////");
        System.out.println(teacherService.findOneWithSchool(ssss).getSchool());
        //System.out.println(teacherService.findOne(ssss) + "   ////////////////////");
        //System.out.println(teacherService.findOne(ssss).getSchoolId() + "    ///////////////");
*/
/*

        TeacherDTO teacherDTOs = new TeacherDTO();
        User user = new User();
        //Set userDTO
        user.setEmail("marianpulup@gmail.com");
        user.setFirstName("Марян");
        user.setLastName("Пилип");
        String context = teacherService.saveTeacherWithUser(teacherDTOs, user, principal);
        mailService.sendSimpleEmail(user.getEmail(), context);

*/

    }

    @PostMapping("/headteacher-management")
    @Timed
    public ResponseEntity<TeacherDTO> createTeacherWithUser(@Valid @RequestBody TeacherDTO teacherDTO) throws URISyntaxException {
        log.debug("REST request to save Teacher : {}", teacherDTO);
        TeacherDTO result = teacherService.saveTeacherWithUser(teacherDTO);
        return ResponseEntity.created(new URI("/api/headteacher-management/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


}
