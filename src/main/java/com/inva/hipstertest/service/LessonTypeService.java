package com.inva.hipstertest.service;

import com.inva.hipstertest.service.dto.LessonDTO;
import com.inva.hipstertest.service.dto.LessonTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing LessonType.
 */
public interface LessonTypeService {

    /**
     * Save a lessonType.
     *
     * @param lessonTypeDTO the entity to save
     * @return the persisted entity
     */
    LessonTypeDTO save(LessonTypeDTO lessonTypeDTO);

    /**
     *  Get all the lessonTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LessonTypeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" lessonType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LessonTypeDTO findOne(Long id);

    /**
     *  Delete the "id" lessonType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     *  Get all the lessonTypes.
     *
     *  @return the list of entities
     */
    List<LessonTypeDTO> findAll();
}
