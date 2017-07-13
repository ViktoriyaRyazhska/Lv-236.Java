package com.inva.hipstertest.service;

import com.inva.hipstertest.service.dto.AttendancesLogDTO;
import java.util.List;

/**
 * Service Interface for managing AttendancesLog.
 */
public interface AttendancesLogService {

    /**
     * Save a attendancesLog.
     *
     * @param attendancesLogDTO the entity to save
     * @return the persisted entity
     */
    AttendancesLogDTO save(AttendancesLogDTO attendancesLogDTO);

    /**
     *  Get all the attendancesLogs.
     *  
     *  @return the list of entities
     */
    List<AttendancesLogDTO> findAll();

    /**
     *  Get the "id" attendancesLog.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AttendancesLogDTO findOne(Long id);

    /**
     *  Delete the "id" attendancesLog.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
