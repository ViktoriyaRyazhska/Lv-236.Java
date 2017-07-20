package com.inva.hipstertest.service;

import com.inva.hipstertest.domain.Parent;
import com.inva.hipstertest.service.dto.ParentDTO;
import java.util.List;
import java.util.Set;

/**
 * Service Interface for managing Parent.
 */
public interface ParentService {

    /**
     * Save a parent.
     *
     * @param parentDTO the entity to save
     * @return the persisted entity
     */
    ParentDTO save(ParentDTO parentDTO);

    /**
     *  Get all the parents.
     *
     *  @return the list of entities
     */
    List<ParentDTO> findAll();

    /**
     *  Get the "id" parent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ParentDTO findOne(Long id);

    /**
     *  Delete the "id" parent.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
    /**
     *  Finds parents of the pupil with id.
     *
     *  @param id the id of the pupil
     */
    List<ParentDTO> findParentOfPupil(Long id);

    /**
     * Find parent by current user (via principal)
     */
    ParentDTO findParentByCurrentUser();
    ParentDTO saveParentWithUser(ParentDTO parentDTO, Long pupilId);
}
