package com.inva.hipstertest.service;

import com.inva.hipstertest.service.dto.LessonDTO;

import java.util.List;

/**
 * Service Interface for managing Lesson.
 */
public interface LessonService {

    /**
     * Save a lesson.
     *
     * @param lessonDTO the entity to save
     * @return the persisted entity
     */
    LessonDTO save(LessonDTO lessonDTO);

    /**
     * Get all the lessons.
     *
     * @return the list of entities
     */
    List<LessonDTO> findAll();

    /**
     * Get all the lessons by teacher id.
     *
     * @return the list of entities
     */
    List<LessonDTO> getAllByTeacherId(Long teacherId);


    /**
     * get list of distinct lessons for some form.
     *
     * @param formId to check form
     * @return list of distinct lessons
     */
    List<LessonDTO> findAllByFormId(Long formId);

    /**
     * Get the "id" lesson.
     *
     * @param id the id of the entity
     * @return the entity
     */
    LessonDTO findOne(Long id);

    /**
     * Delete the "id" lesson.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
