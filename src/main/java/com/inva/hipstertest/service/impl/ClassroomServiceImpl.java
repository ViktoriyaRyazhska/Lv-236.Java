package com.inva.hipstertest.service.impl;

import com.inva.hipstertest.service.ClassroomService;
import com.inva.hipstertest.domain.Classroom;
import com.inva.hipstertest.repository.ClassroomRepository;
import com.inva.hipstertest.service.dto.ClassroomDTO;
import com.inva.hipstertest.service.mapper.ClassroomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Classroom.
 */
@Service
@Transactional
public class ClassroomServiceImpl implements ClassroomService{

    private final Logger log = LoggerFactory.getLogger(ClassroomServiceImpl.class);

    private final ClassroomRepository classroomRepository;

    private final ClassroomMapper classroomMapper;

    public ClassroomServiceImpl(ClassroomRepository classroomRepository, ClassroomMapper classroomMapper) {
        this.classroomRepository = classroomRepository;
        this.classroomMapper = classroomMapper;
    }

    /**
     * Save a classroom.
     *
     * @param classroomDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClassroomDTO save(ClassroomDTO classroomDTO) {
        log.debug("Request to save Classroom : {}", classroomDTO);
        Classroom classroom = classroomMapper.classroomDTOToClassroom(classroomDTO);
        classroom = classroomRepository.save(classroom);
        ClassroomDTO result = classroomMapper.classroomToClassroomDTO(classroom);
        return result;
    }

    /**
     *  Get all the classrooms.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ClassroomDTO> findAll() {
        log.debug("Request to get all Classrooms");
        List<ClassroomDTO> result = classroomRepository.findAll().stream()
            .map(classroomMapper::classroomToClassroomDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one classroom by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClassroomDTO findOne(Long id) {
        log.debug("Request to get Classroom : {}", id);
        Classroom classroom = classroomRepository.findOne(id);
        ClassroomDTO classroomDTO = classroomMapper.classroomToClassroomDTO(classroom);
        return classroomDTO;
    }

    /**
     *  Delete the  classroom by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Classroom : {}", id);
        classroomRepository.delete(id);
    }

//    @Override
//    public ClassroomDTO saveClassroomWithUser(ClassroomDTO classroomDTO) {
//        log.debug("Request to save Classroom : {}", classroomDTO);
//        Classroom classroom = classroomMapper.classroomDTOToClassroom(classroomDTO);
//        return classroomMapper.classroomToClassroomDTO(classroomRepository.save(classroom));
//    }
}
