package com.inva.hipstertest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.inva.hipstertest.service.ParentService;
import com.inva.hipstertest.web.rest.util.HeaderUtil;
import com.inva.hipstertest.service.dto.ParentDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Parent.
 */
@RestController
@RequestMapping("/api")
public class ParentResource {

    private final Logger log = LoggerFactory.getLogger(ParentResource.class);

    private static final String ENTITY_NAME = "parent";

    private final ParentService parentService;

    public ParentResource(ParentService parentService) {
        this.parentService = parentService;
    }

    /**
     * POST  /parents : Create a new parent.
     *
     * @param parentDTO the parentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parentDTO, or with status 400 (Bad Request) if the parent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parents")
    @Timed
    public ResponseEntity<ParentDTO> createParent(@Valid @RequestBody ParentDTO parentDTO) throws URISyntaxException {
        log.debug("REST request to save Parent : {}", parentDTO);
        if (parentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new parent cannot already have an ID")).body(null);
        }
        ParentDTO result = parentService.save(parentDTO);
        return ResponseEntity.created(new URI("/api/parents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parents : Updates an existing parent.
     *
     * @param parentDTO the parentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parentDTO,
     * or with status 400 (Bad Request) if the parentDTO is not valid,
     * or with status 500 (Internal Server Error) if the parentDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parents")
    @Timed
    public ResponseEntity<ParentDTO> updateParent(@Valid @RequestBody ParentDTO parentDTO) throws URISyntaxException {
        log.debug("REST request to update Parent : {}", parentDTO);
        if (parentDTO.getId() == null) {
            return createParent(parentDTO);
        }
        ParentDTO result = parentService.save(parentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parents : get all the parents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of parents in body
     */
    @GetMapping("/parents")
    @Timed
    public List<ParentDTO> getAllParents() {
        log.debug("REST request to get all Parents");
        return parentService.findAll();
    }

    /**
     * GET  /parents/:id : get the "id" parent.
     *
     * @param id the id of the parentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/parents/{id}")
    @Timed
    public ResponseEntity<ParentDTO> getParent(@PathVariable Long id) {
        log.debug("REST request to get Parent : {}", id);
        ParentDTO parentDTO = parentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(parentDTO));
    }

    /**
     * DELETE  /parents/:id : delete the "id" parent.
     *
     * @param id the id of the parentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parents/{id}")
    @Timed
    public ResponseEntity<Void> deleteParent(@PathVariable Long id) {
        log.debug("REST request to delete Parent : {}", id);
        parentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
