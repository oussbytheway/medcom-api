package com.pharmaresolve.medcom.web.rest;

import com.pharmaresolve.medcom.repository.PharmacySubscriptionRepository;
import com.pharmaresolve.medcom.service.PharmacySubscriptionService;
import com.pharmaresolve.medcom.service.dto.PharmacySubscriptionDTO;
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
 * REST controller for managing {@link com.pharmaresolve.medcom.domain.PharmacySubscription}.
 */
@RestController
@RequestMapping("/api/pharmacy-subscriptions")
public class PharmacySubscriptionResource {

    private static final Logger LOG = LoggerFactory.getLogger(PharmacySubscriptionResource.class);

    private static final String ENTITY_NAME = "pharmacySubscription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PharmacySubscriptionService pharmacySubscriptionService;

    private final PharmacySubscriptionRepository pharmacySubscriptionRepository;

    public PharmacySubscriptionResource(
        PharmacySubscriptionService pharmacySubscriptionService,
        PharmacySubscriptionRepository pharmacySubscriptionRepository
    ) {
        this.pharmacySubscriptionService = pharmacySubscriptionService;
        this.pharmacySubscriptionRepository = pharmacySubscriptionRepository;
    }

    /**
     * {@code POST  /pharmacy-subscriptions} : Create a new pharmacySubscription.
     *
     * @param pharmacySubscriptionDTO the pharmacySubscriptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pharmacySubscriptionDTO, or with status {@code 400 (Bad Request)} if the pharmacySubscription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PharmacySubscriptionDTO> createPharmacySubscription(@RequestBody PharmacySubscriptionDTO pharmacySubscriptionDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save PharmacySubscription : {}", pharmacySubscriptionDTO);
        if (pharmacySubscriptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new pharmacySubscription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pharmacySubscriptionDTO = pharmacySubscriptionService.save(pharmacySubscriptionDTO);
        return ResponseEntity.created(new URI("/api/pharmacy-subscriptions/" + pharmacySubscriptionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, pharmacySubscriptionDTO.getId().toString()))
            .body(pharmacySubscriptionDTO);
    }

    /**
     * {@code PUT  /pharmacy-subscriptions/:id} : Updates an existing pharmacySubscription.
     *
     * @param id the id of the pharmacySubscriptionDTO to save.
     * @param pharmacySubscriptionDTO the pharmacySubscriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pharmacySubscriptionDTO,
     * or with status {@code 400 (Bad Request)} if the pharmacySubscriptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pharmacySubscriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PharmacySubscriptionDTO> updatePharmacySubscription(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PharmacySubscriptionDTO pharmacySubscriptionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PharmacySubscription : {}, {}", id, pharmacySubscriptionDTO);
        if (pharmacySubscriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pharmacySubscriptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pharmacySubscriptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pharmacySubscriptionDTO = pharmacySubscriptionService.update(pharmacySubscriptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pharmacySubscriptionDTO.getId().toString()))
            .body(pharmacySubscriptionDTO);
    }

    /**
     * {@code PATCH  /pharmacy-subscriptions/:id} : Partial updates given fields of an existing pharmacySubscription, field will ignore if it is null
     *
     * @param id the id of the pharmacySubscriptionDTO to save.
     * @param pharmacySubscriptionDTO the pharmacySubscriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pharmacySubscriptionDTO,
     * or with status {@code 400 (Bad Request)} if the pharmacySubscriptionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pharmacySubscriptionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pharmacySubscriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PharmacySubscriptionDTO> partialUpdatePharmacySubscription(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PharmacySubscriptionDTO pharmacySubscriptionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PharmacySubscription partially : {}, {}", id, pharmacySubscriptionDTO);
        if (pharmacySubscriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pharmacySubscriptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pharmacySubscriptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PharmacySubscriptionDTO> result = pharmacySubscriptionService.partialUpdate(pharmacySubscriptionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pharmacySubscriptionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pharmacy-subscriptions} : get all the pharmacySubscriptions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pharmacySubscriptions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PharmacySubscriptionDTO>> getAllPharmacySubscriptions(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of PharmacySubscriptions");
        Page<PharmacySubscriptionDTO> page = pharmacySubscriptionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pharmacy-subscriptions/:id} : get the "id" pharmacySubscription.
     *
     * @param id the id of the pharmacySubscriptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pharmacySubscriptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PharmacySubscriptionDTO> getPharmacySubscription(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PharmacySubscription : {}", id);
        Optional<PharmacySubscriptionDTO> pharmacySubscriptionDTO = pharmacySubscriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pharmacySubscriptionDTO);
    }

    /**
     * {@code DELETE  /pharmacy-subscriptions/:id} : delete the "id" pharmacySubscription.
     *
     * @param id the id of the pharmacySubscriptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePharmacySubscription(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PharmacySubscription : {}", id);
        pharmacySubscriptionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
