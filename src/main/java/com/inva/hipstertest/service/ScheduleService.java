package com.inva.hipstertest.service;

import com.inva.hipstertest.domain.Schedule;
import com.inva.hipstertest.service.dto.ScheduleDTO;

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
     *  Get all the schedules.
     *
     *  @return the list of entities
     */
    List<ScheduleDTO> findAll();

    /**
     *  Get the "id" schedule.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ScheduleDTO findOne(Long id);

    /**
     *  Delete the "id" schedule.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
    /**
     * Find all Schedules by form ID
     * @param id form Id of Pupil
     * @return
     */
    List<ScheduleDTO> findAllByFormId(Long id);

    List<ScheduleDTO> findAllByTeacherId(Long teacherId);

    void updateHomeworkById(String homework, Long scheduleId);
}
