package com.inva.hipstertest.service.impl;

import com.inva.hipstertest.domain.Pupil;
import com.inva.hipstertest.repository.PupilRepository;
import com.inva.hipstertest.service.PupilService;
import com.inva.hipstertest.service.dto.PupilDTO;
import com.inva.hipstertest.service.mapper.PupilMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Pupil.
 */
@Service
@Transactional
public class PupilServiceImpl implements PupilService {

    private final Logger log = LoggerFactory.getLogger(PupilServiceImpl.class);

    private final PupilRepository pupilRepository;

    private final PupilMapper pupilMapper;

    public PupilServiceImpl(PupilRepository pupilRepository, PupilMapper pupilMapper) {
        this.pupilRepository = pupilRepository;
        this.pupilMapper = pupilMapper;
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
}
