package com.pharmaresolve.medcom.service;

import com.pharmaresolve.medcom.domain.Watchlist;
import com.pharmaresolve.medcom.repository.PharmacyRepository;
import com.pharmaresolve.medcom.repository.WatchlistRepository;
import com.pharmaresolve.medcom.service.dto.WatchlistDTO;
import com.pharmaresolve.medcom.service.mapper.WatchlistMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.pharmaresolve.medcom.domain.Watchlist}.
 */
@Service
@Transactional
public class WatchlistService {

    private static final Logger LOG = LoggerFactory.getLogger(WatchlistService.class);

    private final WatchlistRepository watchlistRepository;

    private final WatchlistMapper watchlistMapper;

    private final PharmacyRepository pharmacyRepository;

    public WatchlistService(
        WatchlistRepository watchlistRepository,
        WatchlistMapper watchlistMapper,
        PharmacyRepository pharmacyRepository
    ) {
        this.watchlistRepository = watchlistRepository;
        this.watchlistMapper = watchlistMapper;
        this.pharmacyRepository = pharmacyRepository;
    }

    /**
     * Save a watchlist.
     *
     * @param watchlistDTO the entity to save.
     * @return the persisted entity.
     */
    public WatchlistDTO save(WatchlistDTO watchlistDTO) {
        LOG.debug("Request to save Watchlist : {}", watchlistDTO);
        Watchlist watchlist = watchlistMapper.toEntity(watchlistDTO);
        Long pharmacyId = watchlist.getPharmacy().getId();
        pharmacyRepository.findById(pharmacyId).ifPresent(watchlist::pharmacy);
        watchlist = watchlistRepository.save(watchlist);
        return watchlistMapper.toDto(watchlist);
    }

    /**
     * Update a watchlist.
     *
     * @param watchlistDTO the entity to save.
     * @return the persisted entity.
     */
    public WatchlistDTO update(WatchlistDTO watchlistDTO) {
        LOG.debug("Request to update Watchlist : {}", watchlistDTO);
        Watchlist watchlist = watchlistMapper.toEntity(watchlistDTO);
        watchlist = watchlistRepository.save(watchlist);
        return watchlistMapper.toDto(watchlist);
    }

    /**
     * Partially update a watchlist.
     *
     * @param watchlistDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WatchlistDTO> partialUpdate(WatchlistDTO watchlistDTO) {
        LOG.debug("Request to partially update Watchlist : {}", watchlistDTO);

        return watchlistRepository
            .findById(watchlistDTO.getId())
            .map(existingWatchlist -> {
                watchlistMapper.partialUpdate(existingWatchlist, watchlistDTO);

                return existingWatchlist;
            })
            .map(watchlistRepository::save)
            .map(watchlistMapper::toDto);
    }

    /**
     * Get all the watchlists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WatchlistDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Watchlists");
        return watchlistRepository.findAll(pageable).map(watchlistMapper::toDto);
    }

    /**
     * Get one watchlist by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WatchlistDTO> findOne(Long id) {
        LOG.debug("Request to get Watchlist : {}", id);
        return watchlistRepository.findById(id).map(watchlistMapper::toDto);
    }

    /**
     * Delete the watchlist by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Watchlist : {}", id);
        watchlistRepository.deleteById(id);
    }
}
