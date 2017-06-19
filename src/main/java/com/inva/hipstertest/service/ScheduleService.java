package com.inva.hipstertest.service;

import com.inva.hipstertest.service.dto.ScheduleDTO;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Interface for managing Schedule.
 */
public interface ScheduleService {

    /**
     * Save a schedule.
     *
     * @param scheduleDTO the entity to save
     * @return the persisted entity
     */
    ScheduleDTO save(ScheduleDTO scheduleDTO);

    /**
     * Get all the schedules.
     *
     * @return the list of entities
     */
    List<ScheduleDTO> findAll();

    /**
     * Get the "id" schedule.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ScheduleDTO findOne(Long id);

    /**
     * Delete the "id" schedule.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Find all Schedules by form ID
     *
     * @param id form Id of Pupil
     * @return the list of entities
     */
    List<ScheduleDTO> findAllByFormId(Long id);

    /**
     * Find all Schedules by teacher ID ordered by Date
     *
     * @param teacherId teacher id
     * @return the list of entities
     */
    List<ScheduleDTO> findAllByTeacherIdOrderByDate(Long teacherId);

    List<ScheduleDTO> findByFormIdAndDate(ZonedDateTime date);


    /**
     * Get all the schedules by school id.
     *
     * @param schoolId the id of the school
     * @return the list of entities
     */
    List<ScheduleDTO> findAllBySchoolId(Long schoolId);
}
