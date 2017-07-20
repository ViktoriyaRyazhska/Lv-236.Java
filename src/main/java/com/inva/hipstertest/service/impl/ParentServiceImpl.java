package com.inva.hipstertest.service.impl;

import com.inva.hipstertest.domain.Form;
import com.inva.hipstertest.domain.Pupil;
import com.inva.hipstertest.domain.User;
import com.inva.hipstertest.service.MailService;
import com.inva.hipstertest.service.ParentService;
import com.inva.hipstertest.domain.Parent;
import com.inva.hipstertest.repository.ParentRepository;
import com.inva.hipstertest.service.PupilService;
import com.inva.hipstertest.service.dto.ParentDTO;
import com.inva.hipstertest.service.dto.PupilDTO;
import com.inva.hipstertest.service.mapper.ParentMapper;
import com.inva.hipstertest.service.mapper.PupilMapper;
import com.inva.hipstertest.support.methods.ROLE_ENUM;
import com.inva.hipstertest.support.methods.SupportCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Parent.
 */
@Service
@Transactional
public class ParentServiceImpl extends SupportCreate implements ParentService {

    private final Logger log = LoggerFactory.getLogger(ParentServiceImpl.class);

    private final ParentRepository parentRepository;

    private final ParentMapper parentMapper;
    private final PupilMapper pupilMapper;
    private final PupilService pupilService;

    @Autowired
    private MailService mailService;

    public ParentServiceImpl(ParentRepository parentRepository, ParentMapper parentMapper, PupilMapper pupilMapper, PupilService pupilService) {
        this.parentRepository = parentRepository;
        this.parentMapper = parentMapper;
        this.pupilMapper = pupilMapper;
        this.pupilService = pupilService;
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
     *  Finds the parents of pupil by id.
     *
     *  @param id the id of the pupil
     */
    @Override
    public List<ParentDTO> findParentOfPupil(Long id) {
        log.debug("Request to get Parents of pupil : {}", id);
        List<Parent> parents = parentRepository.findParentOfPupil(id);
        List<ParentDTO> parentsDTO = parentMapper.parentsToParentDTOs(parents);

        return parentsDTO;
    }

    @Override
    public ParentDTO findParentByCurrentUser() {
        log.debug("Request to find parent by current user");
        return parentMapper.parentToParentDTO(parentRepository.findParentByCurrentUser());
    }

    @Override
    public ParentDTO saveParentWithUser(ParentDTO parentDTO, Long pupilId) {
        log.debug("Request to save parent : {}", parentDTO);
        System.out.println("0000000000000000000");
        Set<PupilDTO> pupilsSet=new HashSet<>();
        pupilsSet.add(pupilService.findOne(pupilId));
        parentDTO.setPupils(pupilsSet);

        System.out.println("1111111111111111111111");
        Map<String, Object> information = super.saveParentWithRole(parentDTO, ROLE_ENUM.PARENT);
        System.out.println("22222222222222222222222222");
        if (information.get("error") != null) {
            parentDTO.setEnabled(false);
            return parentDTO;
        }

        User user = (User) information.get("userObject");
        String content = (String) information.get("content");
        parentDTO.setEnabled(true);
        Parent parent = parentMapper.parentDTOToParent(parentDTO);
        /* NEED CREATE NEW EMAIL */
        mailService.sendSimpleEmailTry(user, content);
        Pupil pupil = pupilMapper.pupilDTOToPupil(pupilService.findOne(pupilId));
        Set<Pupil> pupils=new HashSet<>();
        pupils.add(pupil);
        parent.setPupils(pupils);
        parent.setUser(user);
        return parentMapper.parentToParentDTO(parentRepository.save(parent));
    }
}
