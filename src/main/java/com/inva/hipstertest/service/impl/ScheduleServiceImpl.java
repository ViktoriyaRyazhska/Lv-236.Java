package com.inva.hipstertest.service.impl;

import com.inva.hipstertest.domain.Schedule;
import com.inva.hipstertest.repository.PupilRepository;
import com.inva.hipstertest.repository.ScheduleRepository;
import com.inva.hipstertest.service.ScheduleService;
import com.inva.hipstertest.service.dto.ScheduleDTO;
import com.inva.hipstertest.service.mapper.ScheduleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Schedule.
 */
@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final Logger log = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    private final ScheduleRepository scheduleRepository;

    private final PupilRepository pupilRepository;

    private final ScheduleMapper scheduleMapper;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, PupilRepository pupilRepository, ScheduleMapper scheduleMapper) {
        this.scheduleRepository = scheduleRepository;
        this.pupilRepository = pupilRepository;
        this.scheduleMapper = scheduleMapper;
    }

    /**
     * Save a schedule.
     *
     * @param scheduleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ScheduleDTO save(ScheduleDTO scheduleDTO) {
        log.debug("Request to save Schedule : {}", scheduleDTO);
        Schedule schedule = scheduleMapper.scheduleDTOToSchedule(scheduleDTO);
        schedule = scheduleRepository.save(schedule);
        ScheduleDTO result = scheduleMapper.scheduleToScheduleDTO(schedule);
        return result;
    }

    /**
     * Get all the schedules.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDTO> findAll() {
        log.debug("Request to get all Schedules");
        //log.debug(scheduleRepository.findAllByFormId(1L).toString());
        List<ScheduleDTO> result = scheduleRepository.findAll().stream()
            .map(scheduleMapper::scheduleToScheduleDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     * Get one schedule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ScheduleDTO findOne(Long id) {
        log.debug("Request to get Schedule : {}", id);
        Schedule schedule = scheduleRepository.findOne(id);
        ScheduleDTO scheduleDTO = scheduleMapper.scheduleToScheduleDTO(schedule);
        return scheduleDTO;
    }

    /**
     * Delete the  schedule by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Schedule : {}", id);
        scheduleRepository.delete(id);
    }

    @Override
    public List<ScheduleDTO> findAllByFormId(Long id) {
        log.debug("Request to get schedules for form {}", id);
        List<ScheduleDTO> schedules = scheduleRepository.findByFormId(id).stream()
            .map(scheduleMapper::scheduleToScheduleDTO).collect(Collectors.toCollection(LinkedList::new));
        return schedules;
    }

    /**
     * Get all schedules by teacher id.
     *
     * @param id the teacher id
     * @return the list of entities ordered by date
     */
    @Override
    public List<ScheduleDTO> findAllByTeacherIdOrderByDate(Long id) {
        log.debug("Request to get schedules for teacher {}", id);
        List<Schedule> schedules = scheduleRepository.findAllByTeacherIdOrderByDate(id);
        List<ScheduleDTO> scheduleDTOS = scheduleMapper.schedulesToScheduleDTOs(schedules);
        return scheduleDTOS;
    }

    /**
     * Get all the schedules by school id.
     *
     * @param schoolId the id of the school
     * @return the list of entities
     */
    @Override
    public List<ScheduleDTO> findAllBySchoolId(Long schoolId) {
        log.debug("Request to get all Schedules by school id : {}", schoolId);
        //log.debug(scheduleRepository.findAllByFormId(1L).toString());
        List<ScheduleDTO> result = scheduleRepository.findAllBySchoolId(schoolId).stream()
            .map(scheduleMapper::scheduleToScheduleDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    @Override
    public Page<ScheduleDTO> findSchedulesByTeacherIdFormIdSubjectIdMaxDate(Pageable pageable, Long teacherId, Long formId, Long lessonId, ZonedDateTime today) {
        log.debug("Request to get lessons dates where teacher {} gives lessons for class {} on subject {}", teacherId, formId, lessonId);
        return scheduleRepository.findSchedulesByTeacherIdFormIdSubjectIdMaxDate(pageable, teacherId, formId, lessonId, today).map(scheduleMapper::scheduleToScheduleDTO);
    }

    @Override
    public Long countSchedulesForGradeBook(Long teacherId, Long formId, Long lessonId, ZonedDateTime today) {
        return scheduleRepository.countSchedulesForGradeBook(teacherId, formId, lessonId, today);
    }

    @Override
    public List<ScheduleDTO> findAllByFormIdAndDateBetween(Long formId, ZonedDateTime startDate, ZonedDateTime endDate) {
        log.debug("Request to get schedules by formId {} and date between {} and {}", formId, startDate, endDate);
        List<Schedule> schedules = scheduleRepository.findAllByFormIdAndDateBetween(formId, startDate, endDate);
        List<ScheduleDTO> scheduleDTOS = scheduleMapper.schedulesToScheduleDTOs(schedules);
        return scheduleDTOS;
    }

}
