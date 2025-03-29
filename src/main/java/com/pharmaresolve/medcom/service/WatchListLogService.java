package com.pharmaresolve.medcom.service;

import com.pharmaresolve.medcom.domain.WatchListLog;
import com.pharmaresolve.medcom.repository.WatchListLogRepository;
import com.pharmaresolve.medcom.service.dto.WatchListLogDTO;
import com.pharmaresolve.medcom.service.mapper.WatchListLogMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.pharmaresolve.medcom.domain.WatchListLog}.
 */
@Service
@Transactional
public class WatchListLogService {

    private static final Logger LOG = LoggerFactory.getLogger(WatchListLogService.class);

    private final WatchListLogRepository watchListLogRepository;

    private final WatchListLogMapper watchListLogMapper;

    public WatchListLogService(WatchListLogRepository watchListLogRepository, WatchListLogMapper watchListLogMapper) {
        this.watchListLogRepository = watchListLogRepository;
        this.watchListLogMapper = watchListLogMapper;
    }

    /**
     * Save a watchListLog.
     *
     * @param watchListLogDTO the entity to save.
     * @return the persisted entity.
     */
    public WatchListLogDTO save(WatchListLogDTO watchListLogDTO) {
        LOG.debug("Request to save WatchListLog : {}", watchListLogDTO);
        WatchListLog watchListLog = watchListLogMapper.toEntity(watchListLogDTO);
        watchListLog = watchListLogRepository.save(watchListLog);
        return watchListLogMapper.toDto(watchListLog);
    }

    /**
     * Update a watchListLog.
     *
     * @param watchListLogDTO the entity to save.
     * @return the persisted entity.
     */
    public WatchListLogDTO update(WatchListLogDTO watchListLogDTO) {
        LOG.debug("Request to update WatchListLog : {}", watchListLogDTO);
        WatchListLog watchListLog = watchListLogMapper.toEntity(watchListLogDTO);
        watchListLog = watchListLogRepository.save(watchListLog);
        return watchListLogMapper.toDto(watchListLog);
    }

    /**
     * Partially update a watchListLog.
     *
     * @param watchListLogDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WatchListLogDTO> partialUpdate(WatchListLogDTO watchListLogDTO) {
        LOG.debug("Request to partially update WatchListLog : {}", watchListLogDTO);

        return watchListLogRepository
            .findById(watchListLogDTO.getId())
            .map(existingWatchListLog -> {
                watchListLogMapper.partialUpdate(existingWatchListLog, watchListLogDTO);

                return existingWatchListLog;
            })
            .map(watchListLogRepository::save)
            .map(watchListLogMapper::toDto);
    }

    /**
     * Get all the watchListLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WatchListLogDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all WatchListLogs");
        return watchListLogRepository.findAll(pageable).map(watchListLogMapper::toDto);
    }

    /**
     * Get one watchListLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WatchListLogDTO> findOne(Long id) {
        LOG.debug("Request to get WatchListLog : {}", id);
        return watchListLogRepository.findById(id).map(watchListLogMapper::toDto);
    }

    /**
     * Delete the watchListLog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete WatchListLog : {}", id);
        watchListLogRepository.deleteById(id);
    }
}
