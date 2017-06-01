package com.inva.hipstertest.service;

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

//    /**
//     * Save a classroom.
//     *
//     * @param classroomDTO the entity to save
//     * we take this entity in UI level(some form).
//     * @return String information about login and password.
//     */
//    ClassroomDTO saveClassroomWithUser(ClassroomDTO classroomDTO);
}
