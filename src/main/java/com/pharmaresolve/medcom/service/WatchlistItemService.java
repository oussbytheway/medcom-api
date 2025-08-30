package com.pharmaresolve.medcom.service;

import com.pharmaresolve.medcom.domain.WatchlistItem;
import com.pharmaresolve.medcom.repository.WatchlistItemRepository;
import com.pharmaresolve.medcom.service.dto.WatchlistItemDTO;
import com.pharmaresolve.medcom.service.mapper.WatchlistItemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.pharmaresolve.medcom.domain.WatchlistItem}.
 */
@Service
@Transactional
public class WatchlistItemService {

    private static final Logger LOG = LoggerFactory.getLogger(WatchlistItemService.class);

    private final WatchlistItemRepository watchlistItemRepository;

    private final WatchlistItemMapper watchlistItemMapper;

    public WatchlistItemService(WatchlistItemRepository watchlistItemRepository, WatchlistItemMapper watchlistItemMapper) {
        this.watchlistItemRepository = watchlistItemRepository;
        this.watchlistItemMapper = watchlistItemMapper;
    }

    /**
     * Save a watchlistItem.
     *
     * @param watchlistItemDTO the entity to save.
     * @return the persisted entity.
     */
    public WatchlistItemDTO save(WatchlistItemDTO watchlistItemDTO) {
        LOG.debug("Request to save WatchlistItem : {}", watchlistItemDTO);
        WatchlistItem watchlistItem = watchlistItemMapper.toEntity(watchlistItemDTO);
        watchlistItem = watchlistItemRepository.save(watchlistItem);
        return watchlistItemMapper.toDto(watchlistItem);
    }

    /**
     * Update a watchlistItem.
     *
     * @param watchlistItemDTO the entity to save.
     * @return the persisted entity.
     */
    public WatchlistItemDTO update(WatchlistItemDTO watchlistItemDTO) {
        LOG.debug("Request to update WatchlistItem : {}", watchlistItemDTO);
        WatchlistItem watchlistItem = watchlistItemMapper.toEntity(watchlistItemDTO);
        watchlistItem = watchlistItemRepository.save(watchlistItem);
        return watchlistItemMapper.toDto(watchlistItem);
    }

    /**
     * Partially update a watchlistItem.
     *
     * @param watchlistItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WatchlistItemDTO> partialUpdate(WatchlistItemDTO watchlistItemDTO) {
        LOG.debug("Request to partially update WatchlistItem : {}", watchlistItemDTO);

        return watchlistItemRepository
            .findById(watchlistItemDTO.getId())
            .map(existingWatchlistItem -> {
                watchlistItemMapper.partialUpdate(existingWatchlistItem, watchlistItemDTO);

                return existingWatchlistItem;
            })
            .map(watchlistItemRepository::save)
            .map(watchlistItemMapper::toDto);
    }

    /**
     * Get all the watchlistItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WatchlistItemDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all WatchlistItems");
        return watchlistItemRepository.findAll(pageable).map(watchlistItemMapper::toDto);
    }

    /**
     * Get one watchlistItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WatchlistItemDTO> findOne(Long id) {
        LOG.debug("Request to get WatchlistItem : {}", id);
        return watchlistItemRepository.findById(id).map(watchlistItemMapper::toDto);
    }

    /**
     * Delete the watchlistItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete WatchlistItem : {}", id);
        watchlistItemRepository.deleteById(id);
    }
}
