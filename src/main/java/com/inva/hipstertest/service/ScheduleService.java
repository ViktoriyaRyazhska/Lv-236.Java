package com.inva.hipstertest.service;

import com.inva.hipstertest.domain.Schedule;
import com.inva.hipstertest.service.dto.ScheduleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    List<ScheduleDTO> findAllByTeacherIdOrderByDate(Long teacherId);


    /**
     *  Get all the schedules by school id.
     *
     *  @param schoolId the id of the school
     *  @return the list of entities
     */
    List<ScheduleDTO> findAllBySchoolId(Long schoolId);


    /**
     *FOR PAGEABLE
     * @param pageable
     * @return
     */
    Page<ScheduleDTO> findAll(Pageable pageable);

    /**
     *FOR PAGEABLE
     * @return
     */
    Long countAllSchedule();
}
