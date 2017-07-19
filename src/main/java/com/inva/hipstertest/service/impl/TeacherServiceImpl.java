package com.inva.hipstertest.service.impl;

import com.inva.hipstertest.domain.*;
import com.inva.hipstertest.repository.FormRepository;
import com.inva.hipstertest.repository.UserRepository;
import com.inva.hipstertest.service.*;
import com.inva.hipstertest.repository.TeacherRepository;
import com.inva.hipstertest.service.dto.FormDTO;
import com.inva.hipstertest.service.dto.TeacherDTO;
import com.inva.hipstertest.service.mapper.SchoolMapper;
import com.inva.hipstertest.service.mapper.TeacherMapper;
import com.inva.hipstertest.support.methods.ROLE_ENUM;
import com.inva.hipstertest.support.methods.SupportCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Teacher.
 */
@Service
@Transactional
public class TeacherServiceImpl extends SupportCreate implements TeacherService {

    private final Logger log = LoggerFactory.getLogger(TeacherServiceImpl.class);

    private final TeacherRepository teacherRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final FormService formService;

    private final TeacherMapper teacherMapper;

    private final SchoolMapper schoolMapper;


    @Autowired
    private MailService mailService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private UserService service;

    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              TeacherMapper teacherMapper,
                              SchoolMapper schoolMapper,
                              UserService userService,
                              UserRepository userRepository,
                              FormService formService) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
        this.userService = userService;
        this.userRepository = userRepository;
        this.formService = formService;
        this.schoolMapper = schoolMapper;
    }

    /**
     * Save a teacher.
     *
     * @param teacherDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TeacherDTO save(TeacherDTO teacherDTO) {
        log.debug("Request to save Teacher : {}", teacherDTO);
        Teacher teacher = teacherMapper.teacherDTOToTeacher(teacherDTO);
        teacher = teacherRepository.save(teacher);
        TeacherDTO result = teacherMapper.teacherToTeacherDTO(teacher);
        return result;
    }

    /**
     * Save a teacher(and user details).
     *
     * @param teacherDTO the entity to save
     * @return the persisted entity
     */
    public TeacherDTO saveTeacherAndUser(TeacherDTO teacherDTO) {
        log.debug("Request to save Teacher and User : {}", teacherDTO);
        Teacher teacher = teacherMapper.teacherDTOToTeacher(teacherDTO);
        TeacherDTO headTeacher = findTeacherByCurrentUser();
        User teacherUser = userService.getUserWithAuthorities(teacherDTO.getUserId());
        // modify only if are from same school
        if (headTeacher.getSchoolId().equals(teacherDTO.getSchoolId())) {
            teacherUser.setEmail(teacherDTO.getEmail());
            teacherUser.setFirstName(teacherDTO.getFirstName());
            teacherUser.setLastName(teacherDTO.getLastName());
            userRepository.save(teacherUser);
            if (teacherDTO.getFormId() != null) {
                FormDTO formDTO = formService.findOne(teacherDTO.getFormId());
                formDTO.setTeacherId(teacher.getId());
                formService.save(formDTO);
            } else {
                FormDTO formDTO = formService.findOneByTeacherId(teacherDTO.getId());
                if (formDTO != null) {
                    formDTO.setTeacherId(formDTO.getId());
                    formService.save(formDTO);
                }
            }
            //formService.save(formDTO);
            teacher = teacherRepository.save(teacher);
        }
        TeacherDTO result = teacherMapper.teacherToTeacherDTO(teacher);
        return result;
    }

    /**
     * Get all the teachers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TeacherDTO> findAll() {
        log.debug("Request to get all Teachers");
        List<TeacherDTO> result = teacherRepository.findAllWithEagerRelationships().stream()
            .map(teacherMapper::teacherToTeacherDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     * Get one teacher by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TeacherDTO findOne(Long id) {
        log.debug("Request to get Teacher : {}", id);
        Teacher teacher = teacherRepository.findOneWithEagerRelationships(id);
        TeacherDTO teacherDTO = teacherMapper.teacherToTeacherDTO(teacher);
        return teacherDTO;
    }

    /**
     * Find teacher by current user.
     *
     * @return the entity
     */
    @Override
    public TeacherDTO findTeacherByCurrentUser() {
        log.debug("Request to get Teacher by current user");
        return teacherMapper.teacherToTeacherDTO(teacherRepository.findTeacherByCurrentUser());
    }


    /**
     * Delete the  teacher by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Teacher : {}", id);
        FormDTO formDTO = formService.findOneByTeacherId(id);
        if (formDTO != null) {
            formDTO.setTeacherId(null);
            formService.save(formDTO);
        }
        teacherRepository.delete(id);
    }


    /**
     * Save a teacher.
     */
    @Override
    public TeacherDTO saveTeacherWithUser(TeacherDTO teacherDTO) {
        log.debug("Request to save Teacher : {}", teacherDTO);
        Teacher hteacher = teacherRepository.findOneWithSchool();
        teacherDTO.setSchoolId(hteacher.getSchool().getId());
        Map<String, Object> information = super.saveUserWithRole(teacherDTO, ROLE_ENUM.TEACHER);

        if (information.get("error") != null) {
            teacherDTO.setEnabled(false);
            return teacherDTO;
        }

        User user = (User) information.get("userObject");
        String content = (String) information.get("content");
        teacherDTO.setEnabled(true);
        Teacher teacher = teacherMapper.teacherDTOToTeacher(teacherDTO);
        /* NEED CREATE NEW EMAIL */
        mailService.sendSimpleEmailTry(user, content); // sendSimpleEmail(teacherDTO.getEmail(), content);
        teacher.setSchool(hteacher.getSchool());
        teacher.setUser(user);
        return teacherMapper.teacherToTeacherDTO(teacherRepository.save(teacher));
    }

    /**
     * Save a headTeacher.
     */
    @Override
    public TeacherDTO saveHeadTeacherWithUser(TeacherDTO teacherDTO, Long schoolId) {
        log.debug("Request to save headTeacher : {}", teacherDTO);

        teacherDTO.setSchoolId(schoolId);
        Map<String, Object> information = super.saveUserWithRole(teacherDTO, ROLE_ENUM.HEAD_TEACHER);

        if (information.get("error") != null) {
            teacherDTO.setEnabled(false);
            return teacherDTO;
        }

        User user = (User) information.get("userObject");
        String content = (String) information.get("content");
        teacherDTO.setEnabled(true);
        Teacher teacher = teacherMapper.teacherDTOToTeacher(teacherDTO);
        /* NEED CREATE NEW EMAIL */
        mailService.sendSimpleEmailTry(user, content); // sendSimpleEmail(teacherDTO.getEmail(), content);
        School school = schoolMapper.schoolDTOToSchool(schoolService.findOne(schoolId));
        teacher.setSchool(school);
        teacher.setUser(user);
        return teacherMapper.teacherToTeacherDTO(teacherRepository.save(teacher));
    }

    /**
     * Get all the teachers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TeacherDTO> findAllByCurrentSchool() {
        log.debug("Request to get all Teachers for current school");
        long idSchool = teacherRepository.findOneWithSchool().getSchool().getId();
        List<TeacherDTO> result = teacherRepository.findAllTeachersByCurrentSchool(idSchool).stream()
            .map(teacherMapper::teacherToTeacherDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     * Get all Teachers by school id.
     *
     * @param id the school id
     * @return the list of entities
     */
    @Override
    public List<TeacherDTO> getAllBySchoolId(Long id) {
        log.debug("Request to get all Teachers by school id {}", id);
        List<TeacherDTO> dtoList = teacherMapper.teachersToTeacherDTOs(teacherRepository.getAllBySchoolId(id));
        return dtoList;
    }


    /**
     * Make teacher a headTeacher.
     *
     * @param id the id of the entity
     */
    @Override
    public TeacherDTO makeHeadTeacher(Long id) {
        log.debug("Request to make headteacher : {}", id);
        Teacher teacher = teacherRepository.getOne(id);
        User user = teacher.getUser();
        Set<Authority> auto = user.getAuthorities();
        SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_HEAD_TEACHER");
        if (auto.contains(role)) {
            return teacherMapper.teacherToTeacherDTO(teacher);
        } else {
            Authority authority = new Authority();
            authority.setName(role.getAuthority());
            auto.add(authority);
            user.setAuthorities(auto);
            userRepository.save(user);
            teacher.setUser(user);
            save(teacherMapper.teacherToTeacherDTO(teacher));
            return teacherMapper.teacherToTeacherDTO(teacher);
        }
    }

    /**
     * Unmake teacher a headTeacher.
     *
     * @param id the id of the entity
     */
    @Override
    public TeacherDTO unMakeHeadTeacher(Long id) {
        log.debug("Request to unmake headteacher : {}", id);
        Teacher teacher = teacherRepository.getOne(id);
        User user = teacher.getUser();
        Set<Authority> auto = user.getAuthorities();
        for (Iterator<Authority> it = auto.iterator(); it.hasNext(); ) {
            Authority f = it.next();
            if (f.getName().equals("ROLE_HEAD_TEACHER"))
            it.remove();
        }

        user.setAuthorities(auto);
        userRepository.save(user);
        teacher.setUser(user);
        save(teacherMapper.teacherToTeacherDTO(teacher));
        return teacherMapper.teacherToTeacherDTO(teacher);

    }
}
