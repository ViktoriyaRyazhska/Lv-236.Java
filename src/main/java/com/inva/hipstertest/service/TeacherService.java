package com.inva.hipstertest.service;

import com.inva.hipstertest.domain.Teacher;
import com.inva.hipstertest.domain.User;
import com.inva.hipstertest.service.dto.TeacherDTO;
import com.inva.hipstertest.service.dto.UserDTO;

import java.security.Principal;
import java.util.List;

/**
 * Service Interface for managing Teacher.
 */
public interface TeacherService {

    /**
     * Save a teacher.
     *
     * @param teacherDTO the entity to save
     * @return the persisted entity
     */
    TeacherDTO save(TeacherDTO teacherDTO);

    /**
     *  Get all the teachers.
     *
     *  @return the list of entities
     */
    List<TeacherDTO> findAll();

    /**
     *  Get the "id" teacher.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TeacherDTO findOne(Long id);

    /**
     *  Find teacher by current user
     *
     *  @return the entity
     */
    TeacherDTO findTeacherByCurrentUser();

    /**
     *  Delete the "id" teacher.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Save a teacher.
     *
     * @param teacherDTO the entity to save
     * we take this entity in UI level(some form).
     * @return String information about login and password.
     */
    TeacherDTO saveTeacherWithUser(TeacherDTO teacherDTO);

}
