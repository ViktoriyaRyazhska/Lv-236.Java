package com.inva.hipstertest.service;

import com.inva.hipstertest.service.dto.PupilDTO;

import java.util.List;

/**
 * Service Interface for managing Pupil.
 */
public interface PupilService {

    /**
     * Save a pupil.
     *
     * @param pupilDTO the entity to save
     * @return the persisted entity
     */
    PupilDTO save(PupilDTO pupilDTO);

    /**
     * Get all the pupils.
     *
     * @return the list of entities
     */
    List<PupilDTO> findAll();

    /**
     * Get the "id" pupil.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PupilDTO findOne(Long id);

    /**
     * Delete the "id" pupil.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Find all pupils by Form id.
     *
     * @param formId the id of the Form entity
     */
    List<PupilDTO> findAllByFormId(Long formId);

    /**
     * Find pupil bu current user (via principal)
     */
    PupilDTO findPupilByCurrentUser();
}
