package com.pharmaresolve.medcom.web.rest;

import com.pharmaresolve.medcom.repository.WatchListLogRepository;
import com.pharmaresolve.medcom.service.WatchListLogService;
import com.pharmaresolve.medcom.service.dto.WatchListLogDTO;
import com.pharmaresolve.medcom.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.pharmaresolve.medcom.domain.WatchListLog}.
 */
@RestController
@RequestMapping("/api/watch-list-logs")
public class WatchListLogResource {

    private static final Logger LOG = LoggerFactory.getLogger(WatchListLogResource.class);

    private static final String ENTITY_NAME = "watchListLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WatchListLogService watchListLogService;

    private final WatchListLogRepository watchListLogRepository;

    public WatchListLogResource(WatchListLogService watchListLogService, WatchListLogRepository watchListLogRepository) {
        this.watchListLogService = watchListLogService;
        this.watchListLogRepository = watchListLogRepository;
    }

    /**
     * {@code POST  /watch-list-logs} : Create a new watchListLog.
     *
     * @param watchListLogDTO the watchListLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new watchListLogDTO, or with status {@code 400 (Bad Request)} if the watchListLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<WatchListLogDTO> createWatchListLog(@RequestBody WatchListLogDTO watchListLogDTO) throws URISyntaxException {
        LOG.debug("REST request to save WatchListLog : {}", watchListLogDTO);
        if (watchListLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new watchListLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        watchListLogDTO = watchListLogService.save(watchListLogDTO);
        return ResponseEntity.created(new URI("/api/watch-list-logs/" + watchListLogDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, watchListLogDTO.getId().toString()))
            .body(watchListLogDTO);
    }

    /**
     * {@code PUT  /watch-list-logs/:id} : Updates an existing watchListLog.
     *
     * @param id the id of the watchListLogDTO to save.
     * @param watchListLogDTO the watchListLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated watchListLogDTO,
     * or with status {@code 400 (Bad Request)} if the watchListLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the watchListLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WatchListLogDTO> updateWatchListLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WatchListLogDTO watchListLogDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update WatchListLog : {}, {}", id, watchListLogDTO);
        if (watchListLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, watchListLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!watchListLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        watchListLogDTO = watchListLogService.update(watchListLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, watchListLogDTO.getId().toString()))
            .body(watchListLogDTO);
    }

    /**
     * {@code PATCH  /watch-list-logs/:id} : Partial updates given fields of an existing watchListLog, field will ignore if it is null
     *
     * @param id the id of the watchListLogDTO to save.
     * @param watchListLogDTO the watchListLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated watchListLogDTO,
     * or with status {@code 400 (Bad Request)} if the watchListLogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the watchListLogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the watchListLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WatchListLogDTO> partialUpdateWatchListLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WatchListLogDTO watchListLogDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update WatchListLog partially : {}, {}", id, watchListLogDTO);
        if (watchListLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, watchListLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!watchListLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WatchListLogDTO> result = watchListLogService.partialUpdate(watchListLogDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, watchListLogDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /watch-list-logs} : get all the watchListLogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of watchListLogs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<WatchListLogDTO>> getAllWatchListLogs(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of WatchListLogs");
        Page<WatchListLogDTO> page = watchListLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /watch-list-logs/:id} : get the "id" watchListLog.
     *
     * @param id the id of the watchListLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the watchListLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WatchListLogDTO> getWatchListLog(@PathVariable("id") Long id) {
        LOG.debug("REST request to get WatchListLog : {}", id);
        Optional<WatchListLogDTO> watchListLogDTO = watchListLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(watchListLogDTO);
    }

    /**
     * {@code DELETE  /watch-list-logs/:id} : delete the "id" watchListLog.
     *
     * @param id the id of the watchListLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWatchListLog(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete WatchListLog : {}", id);
        watchListLogService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
