package com.inva.hipstertest.service.impl;

import com.inva.hipstertest.domain.*;
import com.inva.hipstertest.repository.SchoolRepository;
import com.inva.hipstertest.service.MailService;
import com.inva.hipstertest.service.SchoolService;
import com.inva.hipstertest.service.TeacherService;
import com.inva.hipstertest.repository.TeacherRepository;
import com.inva.hipstertest.service.UserService;
import com.inva.hipstertest.service.dto.TeacherDTO;
import com.inva.hipstertest.service.dto.UserDTO;
import com.inva.hipstertest.service.mapper.TeacherMapper;
import com.inva.hipstertest.service.util.RandomUtil;
import com.inva.hipstertest.support.methods.ROLE_ENUM;
import com.inva.hipstertest.support.methods.SupportCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Teacher.
 */
@Service
@Transactional
public class TeacherServiceImpl extends SupportCreate implements TeacherService{

    private final Logger log = LoggerFactory.getLogger(TeacherServiceImpl.class);

    private final TeacherRepository teacherRepository;

    private final TeacherMapper teacherMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private UserService service;

    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
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
     *  Get all the teachers.
     *
     *  @return the list of entities
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
     *  Get one teacher by id.
     *
     *  @param id the id of the entity
     *  @return the entity
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
     *  Find teacher by current user.
     *
     *  @return the entity
     */
    @Override
    public TeacherDTO findTeacherByCurrentUser() {
        log.debug("Request to get Teacher by current user");
        return teacherMapper.teacherToTeacherDTO(teacherRepository.findTeacherByCurrentUser());
    }

    /**
     *  Delete the  teacher by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Teacher : {}", id);
        teacherRepository.delete(id);
    }


    /**
     * Save a teacher.
     *
     * @param teacherDTO the entity to save         //NEED CORRECTION
     */
    @Override
    public TeacherDTO saveTeacherWithUser(TeacherDTO teacherDTO) {
        log.debug("Request to save Teacher : {}", teacherDTO);
        Teacher hteacher = teacherRepository.findOneWithSchool();
        teacherDTO.setSchoolId(hteacher.getSchool().getId());
        Map<String, Object> information = super.saveUserWithRole(teacherDTO, ROLE_ENUM.TEACHER);

        if (information.get("error") != null){
            teacherDTO.setEnabled(false);
            return teacherDTO;
        }

        User user = (User) information.get("userObject");
        String content = (String) information.get("content");
        teacherDTO.setEnabled(true);
        Teacher teacher = teacherMapper.teacherDTOToTeacher(teacherDTO);
        /* NEED CREATE NEW EMAIL */
        mailService.sendSimpleEmailTry(user, content); //sendSimpleEmail(teacherDTO.getEmail(), content);
        teacher.setSchool(hteacher.getSchool());
        teacher.setUser(user);
        return teacherMapper.teacherToTeacherDTO(teacherRepository.save(teacher));
    }

}
