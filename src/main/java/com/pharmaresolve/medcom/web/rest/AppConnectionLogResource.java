package com.pharmaresolve.medcom.web.rest;

import com.pharmaresolve.medcom.repository.AppConnectionLogRepository;
import com.pharmaresolve.medcom.service.AppConnectionLogService;
import com.pharmaresolve.medcom.service.dto.AppConnectionLogDTO;
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
 * REST controller for managing {@link com.pharmaresolve.medcom.domain.AppConnectionLog}.
 */
@RestController
@RequestMapping("/api/app-connection-logs")
public class AppConnectionLogResource {

    private static final Logger LOG = LoggerFactory.getLogger(AppConnectionLogResource.class);

    private static final String ENTITY_NAME = "appConnectionLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppConnectionLogService appConnectionLogService;

    private final AppConnectionLogRepository appConnectionLogRepository;

    public AppConnectionLogResource(
        AppConnectionLogService appConnectionLogService,
        AppConnectionLogRepository appConnectionLogRepository
    ) {
        this.appConnectionLogService = appConnectionLogService;
        this.appConnectionLogRepository = appConnectionLogRepository;
    }

    /**
     * {@code POST  /app-connection-logs} : Create a new appConnectionLog.
     *
     * @param appConnectionLogDTO the appConnectionLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appConnectionLogDTO, or with status {@code 400 (Bad Request)} if the appConnectionLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AppConnectionLogDTO> createAppConnectionLog(@RequestBody AppConnectionLogDTO appConnectionLogDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save AppConnectionLog : {}", appConnectionLogDTO);
        if (appConnectionLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new appConnectionLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        appConnectionLogDTO = appConnectionLogService.save(appConnectionLogDTO);
        return ResponseEntity.created(new URI("/api/app-connection-logs/" + appConnectionLogDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, appConnectionLogDTO.getId().toString()))
            .body(appConnectionLogDTO);
    }

    /**
     * {@code PUT  /app-connection-logs/:id} : Updates an existing appConnectionLog.
     *
     * @param id the id of the appConnectionLogDTO to save.
     * @param appConnectionLogDTO the appConnectionLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appConnectionLogDTO,
     * or with status {@code 400 (Bad Request)} if the appConnectionLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appConnectionLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppConnectionLogDTO> updateAppConnectionLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppConnectionLogDTO appConnectionLogDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update AppConnectionLog : {}, {}", id, appConnectionLogDTO);
        if (appConnectionLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appConnectionLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appConnectionLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        appConnectionLogDTO = appConnectionLogService.update(appConnectionLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appConnectionLogDTO.getId().toString()))
            .body(appConnectionLogDTO);
    }

    /**
     * {@code PATCH  /app-connection-logs/:id} : Partial updates given fields of an existing appConnectionLog, field will ignore if it is null
     *
     * @param id the id of the appConnectionLogDTO to save.
     * @param appConnectionLogDTO the appConnectionLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appConnectionLogDTO,
     * or with status {@code 400 (Bad Request)} if the appConnectionLogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appConnectionLogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appConnectionLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppConnectionLogDTO> partialUpdateAppConnectionLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppConnectionLogDTO appConnectionLogDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update AppConnectionLog partially : {}, {}", id, appConnectionLogDTO);
        if (appConnectionLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appConnectionLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appConnectionLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppConnectionLogDTO> result = appConnectionLogService.partialUpdate(appConnectionLogDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appConnectionLogDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-connection-logs} : get all the appConnectionLogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appConnectionLogs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AppConnectionLogDTO>> getAllAppConnectionLogs(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of AppConnectionLogs");
        Page<AppConnectionLogDTO> page = appConnectionLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /app-connection-logs/:id} : get the "id" appConnectionLog.
     *
     * @param id the id of the appConnectionLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appConnectionLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppConnectionLogDTO> getAppConnectionLog(@PathVariable("id") Long id) {
        LOG.debug("REST request to get AppConnectionLog : {}", id);
        Optional<AppConnectionLogDTO> appConnectionLogDTO = appConnectionLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appConnectionLogDTO);
    }

    /**
     * {@code DELETE  /app-connection-logs/:id} : delete the "id" appConnectionLog.
     *
     * @param id the id of the appConnectionLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppConnectionLog(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete AppConnectionLog : {}", id);
        appConnectionLogService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
