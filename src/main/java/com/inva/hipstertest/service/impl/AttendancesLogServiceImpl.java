package com.inva.hipstertest.service.impl;

import com.inva.hipstertest.service.AttendancesLogService;
import com.inva.hipstertest.domain.AttendancesLog;
import com.inva.hipstertest.repository.AttendancesLogRepository;
import com.inva.hipstertest.service.dto.AttendancesLogDTO;
import com.inva.hipstertest.service.mapper.AttendancesLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AttendancesLog.
 */
@Service
@Transactional
public class AttendancesLogServiceImpl implements AttendancesLogService{

    private final Logger log = LoggerFactory.getLogger(AttendancesLogServiceImpl.class);
    
    private final AttendancesLogRepository attendancesLogRepository;

    private final AttendancesLogMapper attendancesLogMapper;

    public AttendancesLogServiceImpl(AttendancesLogRepository attendancesLogRepository, AttendancesLogMapper attendancesLogMapper) {
        this.attendancesLogRepository = attendancesLogRepository;
        this.attendancesLogMapper = attendancesLogMapper;
    }

    /**
     * Save a attendancesLog.
     *
     * @param attendancesLogDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AttendancesLogDTO save(AttendancesLogDTO attendancesLogDTO) {
        log.debug("Request to save AttendancesLog : {}", attendancesLogDTO);
        AttendancesLog attendancesLog = attendancesLogMapper.attendancesLogDTOToAttendancesLog(attendancesLogDTO);
        attendancesLog = attendancesLogRepository.save(attendancesLog);
        AttendancesLogDTO result = attendancesLogMapper.attendancesLogToAttendancesLogDTO(attendancesLog);
        return result;
    }

    /**
     *  Get all the attendancesLogs.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AttendancesLogDTO> findAll() {
        log.debug("Request to get all AttendancesLogs");
        List<AttendancesLogDTO> result = attendancesLogRepository.findAll().stream()
            .map(attendancesLogMapper::attendancesLogToAttendancesLogDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one attendancesLog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AttendancesLogDTO findOne(Long id) {
        log.debug("Request to get AttendancesLog : {}", id);
        AttendancesLog attendancesLog = attendancesLogRepository.findOne(id);
        AttendancesLogDTO attendancesLogDTO = attendancesLogMapper.attendancesLogToAttendancesLogDTO(attendancesLog);
        return attendancesLogDTO;
    }

    /**
     *  Delete the  attendancesLog by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttendancesLog : {}", id);
        attendancesLogRepository.delete(id);
    }
}
