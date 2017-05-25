package com.inva.hipstertest.service.impl;

import com.inva.hipstertest.domain.*;
import com.inva.hipstertest.repository.SchoolRepository;
import com.inva.hipstertest.service.TeacherService;
import com.inva.hipstertest.repository.TeacherRepository;
import com.inva.hipstertest.service.UserService;
import com.inva.hipstertest.service.dto.TeacherDTO;
import com.inva.hipstertest.service.dto.UserDTO;
import com.inva.hipstertest.service.mapper.TeacherMapper;
import com.inva.hipstertest.service.util.RandomUtil;
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

    //@Autowired
    //private SchoolRepository schoolRepository;

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
    public String saveTeacherWithUser(TeacherDTO teacherDTO, User userStart, Principal principal) {
        log.debug("Request to save Teacher : {}", teacherDTO, userStart);
        Map<String, Object> information = super.saveUserWithRole(userStart, "headTeacher");
        User user1 = (User) information.get("id");
        String content = (String) information.get("content");
        teacherDTO.setEnabled(true);
        Teacher teacher = teacherMapper.teacherDTOToTeacher(teacherDTO);

        //System.out.println(principal.getName()); // LOGIN

        School school = new School();
        school.setEnabled(true);
        school.setId(1L);
        school.setName("?? ?91");
        teacher.setSchool(school);

        teacher.setUser(user1);
        teacherRepository.save(teacher);
        return content;
    }

    @Override
    public Teacher findOneWithSchool(Long id){
        return teacherRepository.findOneWithSchool(id);
    }


}
