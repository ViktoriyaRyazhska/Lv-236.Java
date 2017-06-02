package com.inva.hipstertest.service.impl;

import com.inva.hipstertest.service.LessonService;
import com.inva.hipstertest.domain.Lesson;
import com.inva.hipstertest.repository.LessonRepository;
import com.inva.hipstertest.service.dto.LessonDTO;
import com.inva.hipstertest.service.mapper.LessonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Lesson.
 */
@Service
@Transactional
public class LessonServiceImpl implements LessonService{

    private final Logger log = LoggerFactory.getLogger(LessonServiceImpl.class);

    private final LessonRepository lessonRepository;

    private final LessonMapper lessonMapper;

    public LessonServiceImpl(LessonRepository lessonRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
    }

    /**
     * Save a lesson.
     *
     * @param lessonDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LessonDTO save(LessonDTO lessonDTO) {
        log.debug("Request to save Lesson : {}", lessonDTO);
        Lesson lesson = lessonMapper.lessonDTOToLesson(lessonDTO);
        lesson = lessonRepository.save(lesson);
        LessonDTO result = lessonMapper.lessonToLessonDTO(lesson);
        return result;
    }

    /**
     *  Get all the lessons.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LessonDTO> findAll() {
        log.debug("Request to get all Lessons");
        List<LessonDTO> result = lessonRepository.findAll().stream()
            .map(lessonMapper::lessonToLessonDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get all the lessons by teacher id.
     *
     *  @return the list of entities
     */
    @Override
    public List<LessonDTO> getAllLessonsByTeacherId(Long teacherId) {
        log.debug("Request to get all Lessons by teacher {}", teacherId);
        List<Lesson> lessons = lessonRepository.getAllLessonsByTeacherId(teacherId);
        List<LessonDTO> lessonDTOs = lessonMapper.lessonsToLessonDTOs(lessons);
        return lessonDTOs;
    }

    /**
     * GET all distinct lessons for selected form.
     *
     * @param formId to check form
     * @return list of lesson DTO's
     */
    @Override
    public List<LessonDTO> getDistinctLessonsForForm(Long formId) {
        log.debug("Request to get distinct Lessons by form {}", formId);
        List<Lesson> lessons = lessonRepository.getDistinctLessonsForForm(formId);
        List<LessonDTO> lessonDTOS = lessonMapper.lessonsToLessonDTOs(lessons);
        return lessonDTOS;
    }

    /**
     *  Get one lesson by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LessonDTO findOne(Long id) {
        log.debug("Request to get Lesson : {}", id);
        Lesson lesson = lessonRepository.findOne(id);
        LessonDTO lessonDTO = lessonMapper.lessonToLessonDTO(lesson);
        return lessonDTO;
    }

    /**
     *  Delete the  lesson by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lesson : {}", id);
        lessonRepository.delete(id);
    }
}
