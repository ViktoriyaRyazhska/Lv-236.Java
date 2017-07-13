package com.inva.hipstertest.service;

import com.inva.hipstertest.service.dto.ScheduleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.sql.Timestamp;
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
     * Find all schedules by teacher ID ordered by Date
     *
     * @param teacherId teacher id
     * @return the list of entities
     */
    List<ScheduleDTO> findAllByTeacherIdOrderByDate(Long teacherId);

    /**
     * Find all schedules by form id and requested date.
     *
     * @param date requested date
     * @return the list of entities
     */
    List<ScheduleDTO> findAllByFormIdAndDate(Long formId, String date);

    List<ScheduleDTO> findAllByFormIdAndDateBetween(Long formId, ZonedDateTime startDate, ZonedDateTime endDate);

    /**
     * Get all the schedules by school id.
     *
     * @param schoolId the id of the school
     * @return the list of entities
     */
    List<ScheduleDTO> findAllBySchoolId(Long schoolId);

    List<ScheduleDTO> findFormsAndLessonsByTeacherId(Long teacherId);

    Page<ScheduleDTO> findAllByFormIdLessonIdMaxDate(Pageable pageable, Long formId, Long lessonId, ZonedDateTime maxDate);

    Long countAllByFormIdLessonIdMaxDate(Long formId, Long lessonId, ZonedDateTime maxDate);

}
