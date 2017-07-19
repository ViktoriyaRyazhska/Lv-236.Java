package com.inva.hipstertest.service;

import com.inva.hipstertest.freemarker.searchcriteria.ClassroomSearchCriteria;
import com.inva.hipstertest.service.dto.ClassroomDTO;
import java.util.List;

/**
 * Service Interface for managing Classroom.
 */
public interface ClassroomService {

    /**
     * Save a classroom.
     *
     * @param classroomDTO the entity to save
     * @return the persisted entity
     */
    ClassroomDTO save(ClassroomDTO classroomDTO);

    /**
     *  Get all the classrooms.
     *
     *  @return the list of entities
     */
    List<ClassroomDTO> findAll();

    /**
     *  Get the "id" classroom.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClassroomDTO findOne(Long id);

    /**
     *  Delete the "id" classroom.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<ClassroomDTO> findAllByCurrentSchool();

    List<ClassroomDTO> findAvailableByCurrentSchoolAndSearchCriteria(ClassroomSearchCriteria classroomSearchCriteria);

    List<ClassroomDTO> findAvailablePlusOneById(ClassroomSearchCriteria classroomSearchCriteria);
}
