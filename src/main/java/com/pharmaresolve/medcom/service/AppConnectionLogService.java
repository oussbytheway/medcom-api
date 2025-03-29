package com.pharmaresolve.medcom.service;

import com.pharmaresolve.medcom.domain.AppConnectionLog;
import com.pharmaresolve.medcom.repository.AppConnectionLogRepository;
import com.pharmaresolve.medcom.service.dto.AppConnectionLogDTO;
import com.pharmaresolve.medcom.service.mapper.AppConnectionLogMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.pharmaresolve.medcom.domain.AppConnectionLog}.
 */
@Service
@Transactional
public class AppConnectionLogService {

    private static final Logger LOG = LoggerFactory.getLogger(AppConnectionLogService.class);

    private final AppConnectionLogRepository appConnectionLogRepository;

    private final AppConnectionLogMapper appConnectionLogMapper;

    public AppConnectionLogService(AppConnectionLogRepository appConnectionLogRepository, AppConnectionLogMapper appConnectionLogMapper) {
        this.appConnectionLogRepository = appConnectionLogRepository;
        this.appConnectionLogMapper = appConnectionLogMapper;
    }

    /**
     * Save a appConnectionLog.
     *
     * @param appConnectionLogDTO the entity to save.
     * @return the persisted entity.
     */
    public AppConnectionLogDTO save(AppConnectionLogDTO appConnectionLogDTO) {
        LOG.debug("Request to save AppConnectionLog : {}", appConnectionLogDTO);
        AppConnectionLog appConnectionLog = appConnectionLogMapper.toEntity(appConnectionLogDTO);
        appConnectionLog = appConnectionLogRepository.save(appConnectionLog);
        return appConnectionLogMapper.toDto(appConnectionLog);
    }

    /**
     * Update a appConnectionLog.
     *
     * @param appConnectionLogDTO the entity to save.
     * @return the persisted entity.
     */
    public AppConnectionLogDTO update(AppConnectionLogDTO appConnectionLogDTO) {
        LOG.debug("Request to update AppConnectionLog : {}", appConnectionLogDTO);
        AppConnectionLog appConnectionLog = appConnectionLogMapper.toEntity(appConnectionLogDTO);
        appConnectionLog = appConnectionLogRepository.save(appConnectionLog);
        return appConnectionLogMapper.toDto(appConnectionLog);
    }

    /**
     * Partially update a appConnectionLog.
     *
     * @param appConnectionLogDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppConnectionLogDTO> partialUpdate(AppConnectionLogDTO appConnectionLogDTO) {
        LOG.debug("Request to partially update AppConnectionLog : {}", appConnectionLogDTO);

        return appConnectionLogRepository
            .findById(appConnectionLogDTO.getId())
            .map(existingAppConnectionLog -> {
                appConnectionLogMapper.partialUpdate(existingAppConnectionLog, appConnectionLogDTO);

                return existingAppConnectionLog;
            })
            .map(appConnectionLogRepository::save)
            .map(appConnectionLogMapper::toDto);
    }

    /**
     * Get all the appConnectionLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AppConnectionLogDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all AppConnectionLogs");
        return appConnectionLogRepository.findAll(pageable).map(appConnectionLogMapper::toDto);
    }

    /**
     * Get one appConnectionLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppConnectionLogDTO> findOne(Long id) {
        LOG.debug("Request to get AppConnectionLog : {}", id);
        return appConnectionLogRepository.findById(id).map(appConnectionLogMapper::toDto);
    }

    /**
     * Delete the appConnectionLog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete AppConnectionLog : {}", id);
        appConnectionLogRepository.deleteById(id);
    }
}
