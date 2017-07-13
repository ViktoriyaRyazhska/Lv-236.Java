package com.inva.hipstertest.service.impl;

import com.inva.hipstertest.domain.*;
import com.inva.hipstertest.repository.PupilRepository;
import com.inva.hipstertest.service.FormService;
import com.inva.hipstertest.service.MailService;
import com.inva.hipstertest.service.PupilService;
import com.inva.hipstertest.service.dto.PupilDTO;
import com.inva.hipstertest.service.mapper.FormMapper;
import com.inva.hipstertest.service.mapper.PupilMapper;
import com.inva.hipstertest.support.methods.ROLE_ENUM;
import com.inva.hipstertest.support.methods.SupportCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Pupil.
 */
@Service
@Transactional
public class PupilServiceImpl extends SupportCreate implements PupilService {

    private final Logger log = LoggerFactory.getLogger(PupilServiceImpl.class);

    private final PupilRepository pupilRepository;

    private final PupilMapper pupilMapper;
    private final FormMapper formMapper;
    private final FormService formService;

    @Autowired
    private MailService mailService;

    public PupilServiceImpl(PupilRepository pupilRepository, PupilMapper pupilMapper, FormMapper formMapper, FormService formService) {
        this.pupilRepository = pupilRepository;
        this.pupilMapper = pupilMapper;
        this.formMapper = formMapper;
        this.formService = formService;
    }

    /**
     * Save a pupil.
     *
     * @param pupilDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PupilDTO save(PupilDTO pupilDTO) {
        log.debug("Request to save Pupil : {}", pupilDTO);
        Pupil pupil = pupilMapper.pupilDTOToPupil(pupilDTO);
        pupil = pupilRepository.save(pupil);
        PupilDTO result = pupilMapper.pupilToPupilDTO(pupil);
        return result;
    }

    /**
     * Get all the pupils.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PupilDTO> findAll() {
        log.debug("Request to get all Pupils");
        List<PupilDTO> result = pupilRepository.findAll().stream()
            .map(pupilMapper::pupilToPupilDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     * Get one pupil by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PupilDTO findOne(Long id) {
        log.debug("Request to get Pupil : {}", id);
        Pupil pupil = pupilRepository.findOne(id);
        PupilDTO pupilDTO = pupilMapper.pupilToPupilDTO(pupil);
        return pupilDTO;
    }

    /**
     * Delete the  pupil by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pupil : {}", id);
        pupilRepository.delete(id);
    }


    @Override
    public List<PupilDTO> findAllByFormId(Long formId) {
        log.debug("Request to find all pupils by formId : {}", formId);
        List<Pupil> pupils = pupilRepository.findAllByFormId(formId);
        List<PupilDTO> pupilDTOs = pupilMapper.pupilsToPupilDTOs(pupils);
        return pupilDTOs;
    }

    @Override
    public PupilDTO findPupilByCurrentUser() {
        log.debug("Request to find pupil by current user");
        return pupilMapper.pupilToPupilDTO(pupilRepository.findPupilByCurrentUser());
    }

    @Override
    public List<PupilDTO> findAllByParentId(Long parentId) {
        log.debug("Request to find all pupils by parentId : {}", parentId);
        List<Pupil> pupils = pupilRepository.findAllByParentId(parentId);
        List<PupilDTO> pupilDTOs = pupilMapper.pupilsToPupilDTOs(pupils);
        return pupilDTOs;
    }

    /**
     * Save a pupil.
     */
    @Override
    public PupilDTO savePupilWithUser(PupilDTO pupilDTO, Long formId) {
        log.debug("Request to save pupil : {}", pupilDTO);

        pupilDTO.setFormId(formId);
        Map<String, Object> information = super.savePupilWithRole(pupilDTO, ROLE_ENUM.PUPIL);

        if (information.get("error") != null) {
            pupilDTO.setEnabled(false);
            return pupilDTO;
        }

        User user = (User) information.get("userObject");
        String content = (String) information.get("content");
        pupilDTO.setEnabled(true);
        Pupil pupil = pupilMapper.pupilDTOToPupil(pupilDTO);
        /* NEED CREATE NEW EMAIL */
        mailService.sendSimpleEmailTry(user, content); // sendSimpleEmail(pupilDTO.getEmail(), content);
        Form form = formMapper.formDTOToForm(formService.findOne(formId));
        pupil.setForm(form);
        pupil.setUser(user);
        return pupilMapper.pupilToPupilDTO(pupilRepository.save(pupil));
    }
}
