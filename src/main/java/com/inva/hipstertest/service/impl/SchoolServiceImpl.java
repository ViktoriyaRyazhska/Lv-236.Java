package com.inva.hipstertest.service.impl;

import com.inva.hipstertest.service.SchoolService;
import com.inva.hipstertest.domain.School;
import com.inva.hipstertest.repository.SchoolRepository;
import com.inva.hipstertest.service.dto.SchoolDTO;
import com.inva.hipstertest.service.mapper.SchoolMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing School.
 */
@Service
@Transactional
public class SchoolServiceImpl implements SchoolService{

    private final Logger log = LoggerFactory.getLogger(SchoolServiceImpl.class);

    private final SchoolRepository schoolRepository;

    private final SchoolMapper schoolMapper;

    public SchoolServiceImpl(SchoolRepository schoolRepository, SchoolMapper schoolMapper) {
        this.schoolRepository = schoolRepository;
        this.schoolMapper = schoolMapper;
    }

    /**
     * Save a school.
     *
     * @param schoolDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SchoolDTO save(SchoolDTO schoolDTO) {
        log.debug("Request to save School : {}", schoolDTO);
        School school = schoolMapper.schoolDTOToSchool(schoolDTO);
        school = schoolRepository.save(school);
        SchoolDTO result = schoolMapper.schoolToSchoolDTO(school);
        return result;
    }

    /**
     *  Get all the schools.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SchoolDTO> findAll() {
        log.debug("Request to get all Schools");
        List<SchoolDTO> result = schoolRepository.findAll().stream()
            .map(schoolMapper::schoolToSchoolDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one school by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SchoolDTO findOne(Long id) {
        log.debug("Request to get School : {}", id);
        School school = schoolRepository.findOne(id);
        SchoolDTO schoolDTO = schoolMapper.schoolToSchoolDTO(school);
        return schoolDTO;
    }

    /**
     *  Delete the  school by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete School : {}", id);
        schoolRepository.delete(id);
    }

}
