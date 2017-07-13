package com.inva.hipstertest.service.impl;

import com.inva.hipstertest.service.LessonTypeService;
import com.inva.hipstertest.domain.LessonType;
import com.inva.hipstertest.repository.LessonTypeRepository;
import com.inva.hipstertest.service.dto.LessonTypeDTO;
import com.inva.hipstertest.service.mapper.LessonTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing LessonType.
 */
@Service
@Transactional
public class LessonTypeServiceImpl implements LessonTypeService{

    private final Logger log = LoggerFactory.getLogger(LessonTypeServiceImpl.class);
    
    private final LessonTypeRepository lessonTypeRepository;

    private final LessonTypeMapper lessonTypeMapper;

    public LessonTypeServiceImpl(LessonTypeRepository lessonTypeRepository, LessonTypeMapper lessonTypeMapper) {
        this.lessonTypeRepository = lessonTypeRepository;
        this.lessonTypeMapper = lessonTypeMapper;
    }

    /**
     * Save a lessonType.
     *
     * @param lessonTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LessonTypeDTO save(LessonTypeDTO lessonTypeDTO) {
        log.debug("Request to save LessonType : {}", lessonTypeDTO);
        LessonType lessonType = lessonTypeMapper.lessonTypeDTOToLessonType(lessonTypeDTO);
        lessonType = lessonTypeRepository.save(lessonType);
        LessonTypeDTO result = lessonTypeMapper.lessonTypeToLessonTypeDTO(lessonType);
        return result;
    }

    /**
     *  Get all the lessonTypes.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LessonTypeDTO> findAll() {
        log.debug("Request to get all LessonTypes");
        List<LessonTypeDTO> result = lessonTypeRepository.findAll().stream()
            .map(lessonTypeMapper::lessonTypeToLessonTypeDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one lessonType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LessonTypeDTO findOne(Long id) {
        log.debug("Request to get LessonType : {}", id);
        LessonType lessonType = lessonTypeRepository.findOne(id);
        LessonTypeDTO lessonTypeDTO = lessonTypeMapper.lessonTypeToLessonTypeDTO(lessonType);
        return lessonTypeDTO;
    }

    /**
     *  Delete the  lessonType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LessonType : {}", id);
        lessonTypeRepository.delete(id);
    }
}
