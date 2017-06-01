package com.inva.hipstertest.service;

import com.inva.hipstertest.domain.School;
import com.inva.hipstertest.service.dto.SchoolDTO;
import java.util.List;

/**
 * Service Interface for managing School.
 */
public interface SchoolService {

    /**
     * Save a school.
     *
     * @param schoolDTO the entity to save
     * @return the persisted entity
     */
    SchoolDTO save(SchoolDTO schoolDTO);

    /**
     *  Get all the schools.
     *
     *  @return the list of entities
     */
    List<SchoolDTO> findAll();

    /**
     *  Get the "id" school.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SchoolDTO findOne(Long id);

    /**
     *  Delete the "id" school.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

}
