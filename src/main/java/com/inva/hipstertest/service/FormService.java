package com.inva.hipstertest.service;

import com.inva.hipstertest.service.dto.FormDTO;
import java.util.List;

/**
 * Service Interface for managing Form.
 */
public interface FormService {

    /**
     * Save a form.
     *
     * @param formDTO the entity to save
     * @return the persisted entity
     */
    FormDTO save(FormDTO formDTO);

    /**
     *  Get all the forms.
     *
     *  @return the list of entities
     */
    List<FormDTO> findAll();

    /**
     *  Get all the forms by teacher id.
     *
     *  @return the list of entities
     */
    List<FormDTO> findAllByTeacherId(Long teacherId);

    /**
     *  Get the "id" form.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FormDTO findOne(Long id);

    /**
     *  Delete the "id" form.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
