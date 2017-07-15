package com.inva.hipstertest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.inva.hipstertest.service.AttendancesService;
import com.inva.hipstertest.web.rest.util.HeaderUtil;
import com.inva.hipstertest.service.dto.AttendancesDTO;
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
 * REST controller for managing Attendances.
 */
@RestController
@RequestMapping("/api")
public class AttendancesResource {

    private final Logger log = LoggerFactory.getLogger(AttendancesResource.class);

    private static final String ENTITY_NAME = "attendances";

    private final AttendancesService attendancesService;

    public AttendancesResource(AttendancesService attendancesService) {
        this.attendancesService = attendancesService;
    }

    /**
     * POST  /attendances : Create a new attendances.
     *
     * @param attendancesDTO the attendancesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new attendancesDTO, or with status 400 (Bad Request) if the attendances has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/attendances")
    @Timed
    public ResponseEntity<AttendancesDTO> createAttendances(@Valid @RequestBody AttendancesDTO attendancesDTO) throws URISyntaxException {
        log.debug("REST request to save Attendances : {}", attendancesDTO);
        if (attendancesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new attendances cannot already have an ID")).body(null);
        }
        AttendancesDTO result = attendancesService.save(attendancesDTO);
        return ResponseEntity.created(new URI("/api/attendances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /attendances : Updates an existing attendances.
     *
     * @param attendancesDTO the attendancesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated attendancesDTO,
     * or with status 400 (Bad Request) if the attendancesDTO is not valid,
     * or with status 500 (Internal Server Error) if the attendancesDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/attendances")
    @Timed
    public ResponseEntity<AttendancesDTO> updateAttendances(@Valid @RequestBody AttendancesDTO attendancesDTO) throws URISyntaxException {
        log.debug("REST request to update Attendances : {}", attendancesDTO);
        if (attendancesDTO.getId() == null) {
            return createAttendances(attendancesDTO);
        }
        AttendancesDTO result = attendancesService.save(attendancesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attendancesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attendances : get all the attendances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of attendances in body
     */
    @GetMapping("/attendances")
    @Timed
    public List<AttendancesDTO> getAllAttendances() {
        log.debug("REST request to get all Attendances");
        return attendancesService.findAll();
    }

    /**
     * GET  /attendances/:id : get the "id" attendances.
     *
     * @param id the id of the attendancesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the attendancesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/attendances/{id}")
    @Timed
    public ResponseEntity<AttendancesDTO> getAttendances(@PathVariable Long id) {
        log.debug("REST request to get Attendances : {}", id);
        AttendancesDTO attendancesDTO = attendancesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(attendancesDTO));
    }

    @GetMapping("/pupilhome/{pupilId}/{lessonId}")
    @Timed
    public List<AttendancesDTO> findAllByPupilIdAndLessonId(@PathVariable("pupilId") Long pupilId, @PathVariable("lessonId") Long lessonId) {
        log.debug("REST request to get Attendances : {}", pupilId);
        return attendancesService.findAllByPupilIdAndLessonId(pupilId, lessonId);
    }


    /**
     * DELETE  /attendances/:id : delete the "id" attendances.
     *
     * @param id the id of the attendancesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/attendances/{id}")
    @Timed
    public ResponseEntity<Void> deleteAttendances(@PathVariable Long id) {
        log.debug("REST request to delete Attendances : {}", id);
        attendancesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
