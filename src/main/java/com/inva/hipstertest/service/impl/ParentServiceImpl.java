package com.inva.hipstertest.service.impl;

import com.inva.hipstertest.service.ParentService;
import com.inva.hipstertest.domain.Parent;
import com.inva.hipstertest.repository.ParentRepository;
import com.inva.hipstertest.service.dto.ParentDTO;
import com.inva.hipstertest.service.mapper.ParentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Parent.
 */
@Service
@Transactional
public class ParentServiceImpl implements ParentService{

    private final Logger log = LoggerFactory.getLogger(ParentServiceImpl.class);

    private final ParentRepository parentRepository;

    private final ParentMapper parentMapper;

    public ParentServiceImpl(ParentRepository parentRepository, ParentMapper parentMapper) {
        this.parentRepository = parentRepository;
        this.parentMapper = parentMapper;
    }

    /**
     * Save a parent.
     *
     * @param parentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParentDTO save(ParentDTO parentDTO) {
        log.debug("Request to save Parent : {}", parentDTO);
        Parent parent = parentMapper.parentDTOToParent(parentDTO);
        parent = parentRepository.save(parent);
        ParentDTO result = parentMapper.parentToParentDTO(parent);
        return result;
    }

    /**
     *  Get all the parents.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ParentDTO> findAll() {
        log.debug("Request to get all Parents");
        List<ParentDTO> result = parentRepository.findAllWithEagerRelationships().stream()
            .map(parentMapper::parentToParentDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one parent by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ParentDTO findOne(Long id) {
        log.debug("Request to get Parent : {}", id);
        Parent parent = parentRepository.findOneWithEagerRelationships(id);
        ParentDTO parentDTO = parentMapper.parentToParentDTO(parent);
        return parentDTO;
    }

    /**
     *  Delete the  parent by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Parent : {}", id);
        parentRepository.delete(id);
    }
    /**
     *  Finds the  parents of pupil by id.
     *
     *  @param id the id of the pupil
     */
    @Override
    public List<ParentDTO> findParentOfPupil(Long id) {
        log.debug("Request to get Parents of pupil : {}", id);
        List<Parent> parents = parentRepository.findParentOfPupil(id);
        List<ParentDTO> parentsDTO=parentMapper.parentsToParentDTOs(parents);


        return parentsDTO;
    }
}
