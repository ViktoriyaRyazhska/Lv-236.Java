package com.inva.hipstertest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.inva.hipstertest.service.PupilService;
import com.inva.hipstertest.web.rest.util.HeaderUtil;
import com.inva.hipstertest.service.dto.PupilDTO;
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
 * REST controller for managing Pupil.
 */
@RestController
@RequestMapping("/api")
public class PupilResource {

    private final Logger log = LoggerFactory.getLogger(PupilResource.class);

    private static final String ENTITY_NAME = "pupil";

    private final PupilService pupilService;

    public PupilResource(PupilService pupilService) {
        this.pupilService = pupilService;
    }

    /**
     * POST  /pupils : Create a new pupil.
     *
     * @param pupilDTO the pupilDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pupilDTO, or with status 400 (Bad Request) if the pupil has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pupils")
    @Timed
    public ResponseEntity<PupilDTO> createPupil(@Valid @RequestBody PupilDTO pupilDTO) throws URISyntaxException {
        log.debug("REST request to save Pupil : {}", pupilDTO);
        if (pupilDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pupil cannot already have an ID")).body(null);
        }
        PupilDTO result = pupilService.save(pupilDTO);
        return ResponseEntity.created(new URI("/api/pupils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pupils : Updates an existing pupil.
     *
     * @param pupilDTO the pupilDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pupilDTO,
     * or with status 400 (Bad Request) if the pupilDTO is not valid,
     * or with status 500 (Internal Server Error) if the pupilDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pupils")
    @Timed
    public ResponseEntity<PupilDTO> updatePupil(@Valid @RequestBody PupilDTO pupilDTO) throws URISyntaxException {
        log.debug("REST request to update Pupil : {}", pupilDTO);
        if (pupilDTO.getId() == null) {
            return createPupil(pupilDTO);
        }
        PupilDTO result = pupilService.save(pupilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pupilDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pupils : get all the pupils.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pupils in body
     */
    @GetMapping("/pupils")
    @Timed
    public List<PupilDTO> getAllPupils() {
        log.debug("REST request to get all Pupils");
        return pupilService.findAll();
    }

    /**
     * GET  /pupils/:id : get the "id" pupil.
     *
     * @param id the id of the pupilDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pupilDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pupils/{id}")
    @Timed
    public ResponseEntity<PupilDTO> getPupil(@PathVariable Long id) {
        log.debug("REST request to get Pupil : {}", id);
        PupilDTO pupilDTO = pupilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pupilDTO));
    }

    /**
     * GET  pupil.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the pupilDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pupilhome/getpupil")
    @Timed
    public ResponseEntity<PupilDTO> getPupil() {
        log.debug("REST request to get Pupil by current User");
        PupilDTO pupilDTO = pupilService.findPupilByCurrentUser();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pupilDTO));
    }

    /**
     * DELETE  /pupils/:id : delete the "id" pupil.
     *
     * @param id the id of the pupilDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pupils/{id}")
    @Timed
    public ResponseEntity<Void> deletePupil(@PathVariable Long id) {
        log.debug("REST request to delete Pupil : {}", id);
        pupilService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
