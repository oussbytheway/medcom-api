package com.pharmaresolve.medcom.service;

import com.pharmaresolve.medcom.domain.WatchList;
import com.pharmaresolve.medcom.repository.PharmacyRepository;
import com.pharmaresolve.medcom.repository.WatchListRepository;
import com.pharmaresolve.medcom.service.dto.WatchListDTO;
import com.pharmaresolve.medcom.service.mapper.WatchListMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.pharmaresolve.medcom.domain.WatchList}.
 */
@Service
@Transactional
public class WatchListService {

    private static final Logger LOG = LoggerFactory.getLogger(WatchListService.class);

    private final WatchListRepository watchListRepository;

    private final WatchListMapper watchListMapper;

    private final PharmacyRepository pharmacyRepository;

    public WatchListService(
        WatchListRepository watchListRepository,
        WatchListMapper watchListMapper,
        PharmacyRepository pharmacyRepository
    ) {
        this.watchListRepository = watchListRepository;
        this.watchListMapper = watchListMapper;
        this.pharmacyRepository = pharmacyRepository;
    }

    /**
     * Save a watchList.
     *
     * @param watchListDTO the entity to save.
     * @return the persisted entity.
     */
    public WatchListDTO save(WatchListDTO watchListDTO) {
        LOG.debug("Request to save WatchList : {}", watchListDTO);
        WatchList watchList = watchListMapper.toEntity(watchListDTO);
        Long pharmacyId = watchList.getPharmacy().getId();
        pharmacyRepository.findById(pharmacyId).ifPresent(watchList::pharmacy);
        watchList = watchListRepository.save(watchList);
        return watchListMapper.toDto(watchList);
    }

    /**
     * Update a watchList.
     *
     * @param watchListDTO the entity to save.
     * @return the persisted entity.
     */
    public WatchListDTO update(WatchListDTO watchListDTO) {
        LOG.debug("Request to update WatchList : {}", watchListDTO);
        WatchList watchList = watchListMapper.toEntity(watchListDTO);
        watchList = watchListRepository.save(watchList);
        return watchListMapper.toDto(watchList);
    }

    /**
     * Partially update a watchList.
     *
     * @param watchListDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WatchListDTO> partialUpdate(WatchListDTO watchListDTO) {
        LOG.debug("Request to partially update WatchList : {}", watchListDTO);

        return watchListRepository
            .findById(watchListDTO.getId())
            .map(existingWatchList -> {
                watchListMapper.partialUpdate(existingWatchList, watchListDTO);

                return existingWatchList;
            })
            .map(watchListRepository::save)
            .map(watchListMapper::toDto);
    }

    /**
     * Get all the watchLists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WatchListDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all WatchLists");
        return watchListRepository.findAll(pageable).map(watchListMapper::toDto);
    }

    /**
     * Get one watchList by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WatchListDTO> findOne(Long id) {
        LOG.debug("Request to get WatchList : {}", id);
        return watchListRepository.findById(id).map(watchListMapper::toDto);
    }

    /**
     * Delete the watchList by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete WatchList : {}", id);
        watchListRepository.deleteById(id);
    }
}
