package com.inva.hipstertest.service.impl;

import com.inva.hipstertest.domain.Teacher;
import com.inva.hipstertest.freemarker.searchcriteria.ClassroomSearchCriteria;
import com.inva.hipstertest.repository.TeacherRepository;
import com.inva.hipstertest.service.ClassroomService;
import com.inva.hipstertest.domain.Classroom;
import com.inva.hipstertest.repository.ClassroomRepository;
import com.inva.hipstertest.service.dto.ClassroomDTO;
import com.inva.hipstertest.service.mapper.ClassroomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Classroom.
 */
@Service
@Transactional
public class ClassroomServiceImpl implements ClassroomService {

    private final Logger log = LoggerFactory.getLogger(ClassroomServiceImpl.class);

    private final ClassroomRepository classroomRepository;

    private final ClassroomMapper classroomMapper;

    @Autowired
    private TeacherRepository teacherRepository;

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
        Teacher hteacher = teacherRepository.findOneWithSchool();
        classroomDTO.setSchoolId(hteacher.getSchool().getId());
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
     * Get one classroom by id.
     *
     * @param id the id of the entity
     * @return the entity
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
     * Delete the  classroom by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Classroom : {}", id);
        classroomRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomDTO> findAllByCurrentSchool() {
        log.debug("Request to get all Classrooms for current school");
        long idSchool = teacherRepository.findOneWithSchool().getSchool().getId();

        return classroomRepository.findAllClassroomsByCurrentSchool(idSchool).stream()
            .map(classroomMapper::classroomToClassroomDTO)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<ClassroomDTO> findAvailableByCurrentSchoolAndSearchCriteria(ClassroomSearchCriteria classroomSearchCriteria) {
        ZonedDateTime date = classroomSearchCriteria.getDate().truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime endDate = date.plusDays(1).minusNanos(1);
        log.debug("Request to get all available Classrooms for current school by search criteria");
        long schoolId = teacherRepository.findOneWithSchool().getSchool().getId();
        List<Classroom> classrooms = classroomRepository.findAllAvailableClassroomsBySchoolIdAndSearchCriteria(schoolId, classroomSearchCriteria.getLessonPosition(), date, endDate);
        return classroomMapper.classroomsToClassroomDTOs(classrooms);
    }

    @Override
    public List<ClassroomDTO> findAvailablePlusOneById(ClassroomSearchCriteria classroomSearchCriteria) {
        List<ClassroomDTO> classroomDTOs = findAvailableByCurrentSchoolAndSearchCriteria(classroomSearchCriteria);
        ClassroomDTO classroomDTO = classroomMapper.classroomToClassroomDTO(classroomRepository.findOne(classroomSearchCriteria.getClassroomId()));
        if (!classroomDTOs.contains(classroomDTO)) {
            classroomDTOs.add(classroomDTO);
        }
        return classroomDTOs;
    }
}
