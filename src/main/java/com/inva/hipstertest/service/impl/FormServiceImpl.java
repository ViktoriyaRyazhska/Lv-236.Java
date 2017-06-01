package com.inva.hipstertest.service.impl;

import com.inva.hipstertest.service.FormService;
import com.inva.hipstertest.domain.Form;
import com.inva.hipstertest.repository.FormRepository;
import com.inva.hipstertest.service.dto.FormDTO;
import com.inva.hipstertest.service.mapper.FormMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Form.
 */
@Service
@Transactional
public class FormServiceImpl implements FormService{

    private final Logger log = LoggerFactory.getLogger(FormServiceImpl.class);

    private final FormRepository formRepository;

    private final FormMapper formMapper;

    public FormServiceImpl(FormRepository formRepository, FormMapper formMapper) {
        this.formRepository = formRepository;
        this.formMapper = formMapper;
    }

    /**
     * Save a form.
     *
     * @param formDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FormDTO save(FormDTO formDTO) {
        log.debug("Request to save Form : {}", formDTO);
        Form form = formMapper.formDTOToForm(formDTO);
        form = formRepository.save(form);
        FormDTO result = formMapper.formToFormDTO(form);
        return result;
    }

    /**
     *  Get all the forms.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FormDTO> findAll() {
        log.debug("Request to get all Forms");
        List<FormDTO> result = formRepository.findAll().stream()
            .map(formMapper::formToFormDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get all Forms by Teacher id.
     *
     *  @param teacherId the id of teacher entity
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FormDTO> findAllByTeacherId(Long teacherId) {
        log.debug("Request to get all Forms by Teacher : {}", teacherId);
        List<Form> forms = formRepository.findAllByTeacherId(teacherId);
        List<FormDTO> formsDTO = formMapper.formsToFormDTOs(forms);
        return formsDTO;
    }

    /**
     *  Get one form by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FormDTO findOne(Long id) {
        log.debug("Request to get Form : {}", id);
        Form form = formRepository.findOne(id);
        FormDTO formDTO = formMapper.formToFormDTO(form);
        return formDTO;
    }

    /**
     *  Delete the  form by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Form : {}", id);
        formRepository.delete(id);
    }
}
