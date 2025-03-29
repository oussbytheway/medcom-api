package com.pharmaresolve.medcom.service;

import com.pharmaresolve.medcom.domain.PharmacySubscription;
import com.pharmaresolve.medcom.repository.PharmacySubscriptionRepository;
import com.pharmaresolve.medcom.service.dto.PharmacySubscriptionDTO;
import com.pharmaresolve.medcom.service.mapper.PharmacySubscriptionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.pharmaresolve.medcom.domain.PharmacySubscription}.
 */
@Service
@Transactional
public class PharmacySubscriptionService {

    private static final Logger LOG = LoggerFactory.getLogger(PharmacySubscriptionService.class);

    private final PharmacySubscriptionRepository pharmacySubscriptionRepository;

    private final PharmacySubscriptionMapper pharmacySubscriptionMapper;

    public PharmacySubscriptionService(
        PharmacySubscriptionRepository pharmacySubscriptionRepository,
        PharmacySubscriptionMapper pharmacySubscriptionMapper
    ) {
        this.pharmacySubscriptionRepository = pharmacySubscriptionRepository;
        this.pharmacySubscriptionMapper = pharmacySubscriptionMapper;
    }

    /**
     * Save a pharmacySubscription.
     *
     * @param pharmacySubscriptionDTO the entity to save.
     * @return the persisted entity.
     */
    public PharmacySubscriptionDTO save(PharmacySubscriptionDTO pharmacySubscriptionDTO) {
        LOG.debug("Request to save PharmacySubscription : {}", pharmacySubscriptionDTO);
        PharmacySubscription pharmacySubscription = pharmacySubscriptionMapper.toEntity(pharmacySubscriptionDTO);
        pharmacySubscription = pharmacySubscriptionRepository.save(pharmacySubscription);
        return pharmacySubscriptionMapper.toDto(pharmacySubscription);
    }

    /**
     * Update a pharmacySubscription.
     *
     * @param pharmacySubscriptionDTO the entity to save.
     * @return the persisted entity.
     */
    public PharmacySubscriptionDTO update(PharmacySubscriptionDTO pharmacySubscriptionDTO) {
        LOG.debug("Request to update PharmacySubscription : {}", pharmacySubscriptionDTO);
        PharmacySubscription pharmacySubscription = pharmacySubscriptionMapper.toEntity(pharmacySubscriptionDTO);
        pharmacySubscription = pharmacySubscriptionRepository.save(pharmacySubscription);
        return pharmacySubscriptionMapper.toDto(pharmacySubscription);
    }

    /**
     * Partially update a pharmacySubscription.
     *
     * @param pharmacySubscriptionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PharmacySubscriptionDTO> partialUpdate(PharmacySubscriptionDTO pharmacySubscriptionDTO) {
        LOG.debug("Request to partially update PharmacySubscription : {}", pharmacySubscriptionDTO);

        return pharmacySubscriptionRepository
            .findById(pharmacySubscriptionDTO.getId())
            .map(existingPharmacySubscription -> {
                pharmacySubscriptionMapper.partialUpdate(existingPharmacySubscription, pharmacySubscriptionDTO);

                return existingPharmacySubscription;
            })
            .map(pharmacySubscriptionRepository::save)
            .map(pharmacySubscriptionMapper::toDto);
    }

    /**
     * Get all the pharmacySubscriptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PharmacySubscriptionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all PharmacySubscriptions");
        return pharmacySubscriptionRepository.findAll(pageable).map(pharmacySubscriptionMapper::toDto);
    }

    /**
     * Get one pharmacySubscription by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PharmacySubscriptionDTO> findOne(Long id) {
        LOG.debug("Request to get PharmacySubscription : {}", id);
        return pharmacySubscriptionRepository.findById(id).map(pharmacySubscriptionMapper::toDto);
    }

    /**
     * Delete the pharmacySubscription by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete PharmacySubscription : {}", id);
        pharmacySubscriptionRepository.deleteById(id);
    }
}
