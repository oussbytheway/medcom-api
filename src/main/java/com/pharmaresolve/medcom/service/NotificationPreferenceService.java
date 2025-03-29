package com.pharmaresolve.medcom.service;

import com.pharmaresolve.medcom.domain.NotificationPreference;
import com.pharmaresolve.medcom.repository.NotificationPreferenceRepository;
import com.pharmaresolve.medcom.service.dto.NotificationPreferenceDTO;
import com.pharmaresolve.medcom.service.mapper.NotificationPreferenceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.pharmaresolve.medcom.domain.NotificationPreference}.
 */
@Service
@Transactional
public class NotificationPreferenceService {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationPreferenceService.class);

    private final NotificationPreferenceRepository notificationPreferenceRepository;

    private final NotificationPreferenceMapper notificationPreferenceMapper;

    public NotificationPreferenceService(
        NotificationPreferenceRepository notificationPreferenceRepository,
        NotificationPreferenceMapper notificationPreferenceMapper
    ) {
        this.notificationPreferenceRepository = notificationPreferenceRepository;
        this.notificationPreferenceMapper = notificationPreferenceMapper;
    }

    /**
     * Save a notificationPreference.
     *
     * @param notificationPreferenceDTO the entity to save.
     * @return the persisted entity.
     */
    public NotificationPreferenceDTO save(NotificationPreferenceDTO notificationPreferenceDTO) {
        LOG.debug("Request to save NotificationPreference : {}", notificationPreferenceDTO);
        NotificationPreference notificationPreference = notificationPreferenceMapper.toEntity(notificationPreferenceDTO);
        notificationPreference = notificationPreferenceRepository.save(notificationPreference);
        return notificationPreferenceMapper.toDto(notificationPreference);
    }

    /**
     * Update a notificationPreference.
     *
     * @param notificationPreferenceDTO the entity to save.
     * @return the persisted entity.
     */
    public NotificationPreferenceDTO update(NotificationPreferenceDTO notificationPreferenceDTO) {
        LOG.debug("Request to update NotificationPreference : {}", notificationPreferenceDTO);
        NotificationPreference notificationPreference = notificationPreferenceMapper.toEntity(notificationPreferenceDTO);
        notificationPreference = notificationPreferenceRepository.save(notificationPreference);
        return notificationPreferenceMapper.toDto(notificationPreference);
    }

    /**
     * Partially update a notificationPreference.
     *
     * @param notificationPreferenceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NotificationPreferenceDTO> partialUpdate(NotificationPreferenceDTO notificationPreferenceDTO) {
        LOG.debug("Request to partially update NotificationPreference : {}", notificationPreferenceDTO);

        return notificationPreferenceRepository
            .findById(notificationPreferenceDTO.getId())
            .map(existingNotificationPreference -> {
                notificationPreferenceMapper.partialUpdate(existingNotificationPreference, notificationPreferenceDTO);

                return existingNotificationPreference;
            })
            .map(notificationPreferenceRepository::save)
            .map(notificationPreferenceMapper::toDto);
    }

    /**
     * Get all the notificationPreferences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificationPreferenceDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all NotificationPreferences");
        return notificationPreferenceRepository.findAll(pageable).map(notificationPreferenceMapper::toDto);
    }

    /**
     * Get one notificationPreference by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NotificationPreferenceDTO> findOne(Long id) {
        LOG.debug("Request to get NotificationPreference : {}", id);
        return notificationPreferenceRepository.findById(id).map(notificationPreferenceMapper::toDto);
    }

    /**
     * Delete the notificationPreference by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete NotificationPreference : {}", id);
        notificationPreferenceRepository.deleteById(id);
    }
}
