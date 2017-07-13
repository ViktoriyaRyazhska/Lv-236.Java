package com.inva.hipstertest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.inva.hipstertest.service.AttendancesLogService;
import com.inva.hipstertest.web.rest.util.HeaderUtil;
import com.inva.hipstertest.service.dto.AttendancesLogDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing AttendancesLog.
 */
@RestController
@RequestMapping("/api")
public class AttendancesLogResource {

    private final Logger log = LoggerFactory.getLogger(AttendancesLogResource.class);

    private static final String ENTITY_NAME = "attendancesLog";
        
    private final AttendancesLogService attendancesLogService;

    public AttendancesLogResource(AttendancesLogService attendancesLogService) {
        this.attendancesLogService = attendancesLogService;
    }

    /**
     * POST  /attendances-logs : Create a new attendancesLog.
     *
     * @param attendancesLogDTO the attendancesLogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new attendancesLogDTO, or with status 400 (Bad Request) if the attendancesLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/attendances-logs")
    @Timed
    public ResponseEntity<AttendancesLogDTO> createAttendancesLog(@Valid @RequestBody AttendancesLogDTO attendancesLogDTO) throws URISyntaxException {
        log.debug("REST request to save AttendancesLog : {}", attendancesLogDTO);
        if (attendancesLogDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new attendancesLog cannot already have an ID")).body(null);
        }
        AttendancesLogDTO result = attendancesLogService.save(attendancesLogDTO);
        return ResponseEntity.created(new URI("/api/attendances-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /attendances-logs : Updates an existing attendancesLog.
     *
     * @param attendancesLogDTO the attendancesLogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated attendancesLogDTO,
     * or with status 400 (Bad Request) if the attendancesLogDTO is not valid,
     * or with status 500 (Internal Server Error) if the attendancesLogDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/attendances-logs")
    @Timed
    public ResponseEntity<AttendancesLogDTO> updateAttendancesLog(@Valid @RequestBody AttendancesLogDTO attendancesLogDTO) throws URISyntaxException {
        log.debug("REST request to update AttendancesLog : {}", attendancesLogDTO);
        if (attendancesLogDTO.getId() == null) {
            return createAttendancesLog(attendancesLogDTO);
        }
        AttendancesLogDTO result = attendancesLogService.save(attendancesLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attendancesLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attendances-logs : get all the attendancesLogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of attendancesLogs in body
     */
    @GetMapping("/attendances-logs")
    @Timed
    public List<AttendancesLogDTO> getAllAttendancesLogs() {
        log.debug("REST request to get all AttendancesLogs");
        return attendancesLogService.findAll();
    }

    /**
     * GET  /attendances-logs/:id : get the "id" attendancesLog.
     *
     * @param id the id of the attendancesLogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the attendancesLogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/attendances-logs/{id}")
    @Timed
    public ResponseEntity<AttendancesLogDTO> getAttendancesLog(@PathVariable Long id) {
        log.debug("REST request to get AttendancesLog : {}", id);
        AttendancesLogDTO attendancesLogDTO = attendancesLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(attendancesLogDTO));
    }

    /**
     * DELETE  /attendances-logs/:id : delete the "id" attendancesLog.
     *
     * @param id the id of the attendancesLogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/attendances-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteAttendancesLog(@PathVariable Long id) {
        log.debug("REST request to delete AttendancesLog : {}", id);
        attendancesLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
