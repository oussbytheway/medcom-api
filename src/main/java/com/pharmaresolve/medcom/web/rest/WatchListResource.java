package com.pharmaresolve.medcom.web.rest;

import com.pharmaresolve.medcom.repository.WatchListRepository;
import com.pharmaresolve.medcom.service.WatchListService;
import com.pharmaresolve.medcom.service.dto.WatchListDTO;
import com.pharmaresolve.medcom.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.pharmaresolve.medcom.domain.WatchList}.
 */
@RestController
@RequestMapping("/api/watch-lists")
public class WatchListResource {

    private static final Logger LOG = LoggerFactory.getLogger(WatchListResource.class);

    private static final String ENTITY_NAME = "watchList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WatchListService watchListService;

    private final WatchListRepository watchListRepository;

    public WatchListResource(WatchListService watchListService, WatchListRepository watchListRepository) {
        this.watchListService = watchListService;
        this.watchListRepository = watchListRepository;
    }

    /**
     * {@code POST  /watch-lists} : Create a new watchList.
     *
     * @param watchListDTO the watchListDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new watchListDTO, or with status {@code 400 (Bad Request)} if the watchList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<WatchListDTO> createWatchList(@Valid @RequestBody WatchListDTO watchListDTO) throws URISyntaxException {
        LOG.debug("REST request to save WatchList : {}", watchListDTO);
        if (watchListDTO.getId() != null) {
            throw new BadRequestAlertException("A new watchList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(watchListDTO.getPharmacy())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        watchListDTO = watchListService.save(watchListDTO);
        return ResponseEntity.created(new URI("/api/watch-lists/" + watchListDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, watchListDTO.getId().toString()))
            .body(watchListDTO);
    }

    /**
     * {@code PUT  /watch-lists/:id} : Updates an existing watchList.
     *
     * @param id the id of the watchListDTO to save.
     * @param watchListDTO the watchListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated watchListDTO,
     * or with status {@code 400 (Bad Request)} if the watchListDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the watchListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WatchListDTO> updateWatchList(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WatchListDTO watchListDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update WatchList : {}, {}", id, watchListDTO);
        if (watchListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, watchListDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!watchListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        watchListDTO = watchListService.update(watchListDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, watchListDTO.getId().toString()))
            .body(watchListDTO);
    }

    /**
     * {@code PATCH  /watch-lists/:id} : Partial updates given fields of an existing watchList, field will ignore if it is null
     *
     * @param id the id of the watchListDTO to save.
     * @param watchListDTO the watchListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated watchListDTO,
     * or with status {@code 400 (Bad Request)} if the watchListDTO is not valid,
     * or with status {@code 404 (Not Found)} if the watchListDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the watchListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WatchListDTO> partialUpdateWatchList(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WatchListDTO watchListDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update WatchList partially : {}, {}", id, watchListDTO);
        if (watchListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, watchListDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!watchListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WatchListDTO> result = watchListService.partialUpdate(watchListDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, watchListDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /watch-lists} : get all the watchLists.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of watchLists in body.
     */
    @GetMapping("")
    public ResponseEntity<List<WatchListDTO>> getAllWatchLists(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of WatchLists");
        Page<WatchListDTO> page = watchListService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /watch-lists/:id} : get the "id" watchList.
     *
     * @param id the id of the watchListDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the watchListDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WatchListDTO> getWatchList(@PathVariable("id") Long id) {
        LOG.debug("REST request to get WatchList : {}", id);
        Optional<WatchListDTO> watchListDTO = watchListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(watchListDTO);
    }

    /**
     * {@code DELETE  /watch-lists/:id} : delete the "id" watchList.
     *
     * @param id the id of the watchListDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWatchList(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete WatchList : {}", id);
        watchListService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
