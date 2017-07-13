package com.inva.hipstertest.service;

import com.inva.hipstertest.service.dto.AttendancesDTO;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Interface for managing Attendances.
 */
public interface AttendancesService {

    /**
     * Save a attendances.
     *
     * @param attendancesDTO the entity to save
     * @return the persisted entity
     */
    AttendancesDTO save(AttendancesDTO attendancesDTO);

    /**
     * Get all the attendances.
     *
     * @return the list of entities
     */
    List<AttendancesDTO> findAll();

    /**
     * Get the "id" attendances.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AttendancesDTO findOne(Long id);

    /**
     * Delete the "id" attendances.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Find all by Pupil id and date.
     *
     * @param date requested date
     * @return list of entities
     */

    List<AttendancesDTO> findAllByPupilIdAndDate(Long pupilId, String date);

    List<AttendancesDTO> findAllByPupilIdAndDateBetween(Long pupilId, ZonedDateTime dateStart, ZonedDateTime dateEnd);

    List<AttendancesDTO> findAllByPupilIdAndLessonId(Long pupilId, Long lessonId);

    List<AttendancesDTO> findAllByFormIdAndLessonId(Long formId, Long lessonId);

}
