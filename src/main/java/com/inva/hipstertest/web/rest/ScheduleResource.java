package com.inva.hipstertest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.inva.hipstertest.domain.Schedule;
import com.inva.hipstertest.repository.ScheduleRepository;
import com.inva.hipstertest.service.PupilService;
import com.inva.hipstertest.service.ScheduleService;
import com.inva.hipstertest.service.dto.LessonDTO;
import com.inva.hipstertest.service.dto.PupilDTO;
import com.inva.hipstertest.web.rest.util.HeaderUtil;
import com.inva.hipstertest.service.dto.ScheduleDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * REST controller for managing Schedule.
 */
@RestController
@RequestMapping("/api")
public class ScheduleResource {

    private final Logger log = LoggerFactory.getLogger(ScheduleResource.class);

    private static final String ENTITY_NAME = "schedule";

    private final ScheduleService scheduleService;

    private final PupilService pupilService;

    public ScheduleResource(ScheduleService scheduleService, PupilService pupilService) {
        this.scheduleService = scheduleService;
        this.pupilService = pupilService;
    }

    /**
     * POST  /schedules : Create a new schedule.
     *
     * @param scheduleDTO the scheduleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new scheduleDTO, or with status 400 (Bad Request) if the schedule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/schedules")
    @Timed
    public ResponseEntity<ScheduleDTO> createSchedule(@Valid @RequestBody ScheduleDTO scheduleDTO) throws URISyntaxException {
        log.debug("REST request to save Schedule : {}", scheduleDTO);
        if (scheduleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new schedule cannot already have an ID")).body(null);
        }
        ScheduleDTO result = scheduleService.save(scheduleDTO);
        return ResponseEntity.created(new URI("/api/schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schedules : Updates an existing schedule.
     *
     * @param scheduleDTO the scheduleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated scheduleDTO,
     * or with status 400 (Bad Request) if the scheduleDTO is not valid,
     * or with status 500 (Internal Server Error) if the scheduleDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/schedules")
    @Timed
    public ResponseEntity<ScheduleDTO> updateSchedule(@Valid @RequestBody ScheduleDTO scheduleDTO) throws URISyntaxException {
        log.debug("REST request to update Schedule : {}", scheduleDTO);
        if (scheduleDTO.getId() == null) {
            return createSchedule(scheduleDTO);
        }
        ScheduleDTO result = scheduleService.save(scheduleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, scheduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schedules : get all the schedules.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of schedules in body
     */
    @GetMapping("/schedules")
    @Timed
    public List<ScheduleDTO> getAllSchedules() {
        log.debug("REST request to get all Schedules");
        return scheduleService.findAll();
    }

    /**
     * GET  /schedules : get all the schedules for certain form id by current user.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of schedules in body
     */
    @RequestMapping(value = "pupilhome/getschedules", method = RequestMethod.GET)
    @Timed
    public List<ScheduleDTO> getSchedulesByFormIdAndCurrentUser() {
        log.debug("REST request to get schedule by formId");
        PupilDTO pupilDTO = pupilService.findPupilByCurrentUser();
        return scheduleService.findAllByFormId(pupilDTO.getFormId());
    }

    /**
     * GET  /schedules : get all the schedules for certain form id.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of schedules in body
     */
    @RequestMapping(value = "pupilhome/getschedules/{formId}", method = RequestMethod.GET)
    @Timed
    public List<ScheduleDTO> getSchedulesByFormId(@PathVariable Long formId) {
        return scheduleService.findAllByFormId(formId);
    }


    /**
     * GET  /schedules/:id : get the "id" schedule.
     *
     * @param id the id of the scheduleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the scheduleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/schedules/{id}")
    @Timed
    public ResponseEntity<ScheduleDTO> getSchedule(@PathVariable Long id) {
        log.debug("REST request to get Schedule : {}", id);
        ScheduleDTO scheduleDTO = scheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(scheduleDTO));
    }

    /**
     * DELETE  /schedules/:id : delete the "id" schedule.
     *
     * @param id the id of the scheduleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/schedules/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        log.debug("REST request to delete Schedule : {}", id);
        scheduleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /schedules : get all the schedules for certain teacher id.
     *
     * @return the ResponseEntity with status 200 (OK).
     */
    @RequestMapping(value = "schedules/teacher/{teacherId}", method = RequestMethod.GET)
    @Timed
    public List<ScheduleDTO> getScheduleByTecherId(@PathVariable("teacherId") Long teacherId) {
        log.debug("REST request to get schedule by teacher : {}", teacherId);
        return scheduleService.findAllByTeacherId(teacherId);
    }

    @PutMapping("teacherhome/{scheduleId}")
    @Timed
    void updateHomeworkByScheduleId(@Valid @RequestBody String homework, Long scheduleId) throws URISyntaxException {
        log.debug("REST request to update Schedule Homework : {}", homework, scheduleId);
        if (homework != null) {
            scheduleService.updateHomeworkById(homework, scheduleId);
        }
    }

}
