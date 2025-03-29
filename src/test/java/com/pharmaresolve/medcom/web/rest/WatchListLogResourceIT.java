package com.pharmaresolve.medcom.web.rest;

import static com.pharmaresolve.medcom.domain.WatchListLogAsserts.*;
import static com.pharmaresolve.medcom.web.rest.TestUtil.createUpdateProxyForBean;
import static com.pharmaresolve.medcom.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmaresolve.medcom.IntegrationTest;
import com.pharmaresolve.medcom.domain.WatchListLog;
import com.pharmaresolve.medcom.domain.enumeration.WatchListUpdateType;
import com.pharmaresolve.medcom.repository.WatchListLogRepository;
import com.pharmaresolve.medcom.service.dto.WatchListLogDTO;
import com.pharmaresolve.medcom.service.mapper.WatchListLogMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WatchListLogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WatchListLogResourceIT {

    private static final WatchListUpdateType DEFAULT_UPDATE_TYPE = WatchListUpdateType.ADD;
    private static final WatchListUpdateType UPDATED_UPDATE_TYPE = WatchListUpdateType.REMOVE;

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_USER_AGENT = "AAAAAAAAAA";
    private static final String UPDATED_USER_AGENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/watch-list-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WatchListLogRepository watchListLogRepository;

    @Autowired
    private WatchListLogMapper watchListLogMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWatchListLogMockMvc;

    private WatchListLog watchListLog;

    private WatchListLog insertedWatchListLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WatchListLog createEntity() {
        return new WatchListLog().updateType(DEFAULT_UPDATE_TYPE).updateTime(DEFAULT_UPDATE_TIME).userAgent(DEFAULT_USER_AGENT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WatchListLog createUpdatedEntity() {
        return new WatchListLog().updateType(UPDATED_UPDATE_TYPE).updateTime(UPDATED_UPDATE_TIME).userAgent(UPDATED_USER_AGENT);
    }

    @BeforeEach
    public void initTest() {
        watchListLog = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedWatchListLog != null) {
            watchListLogRepository.delete(insertedWatchListLog);
            insertedWatchListLog = null;
        }
    }

    @Test
    @Transactional
    void createWatchListLog() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the WatchListLog
        WatchListLogDTO watchListLogDTO = watchListLogMapper.toDto(watchListLog);
        var returnedWatchListLogDTO = om.readValue(
            restWatchListLogMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(watchListLogDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            WatchListLogDTO.class
        );

        // Validate the WatchListLog in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedWatchListLog = watchListLogMapper.toEntity(returnedWatchListLogDTO);
        assertWatchListLogUpdatableFieldsEquals(returnedWatchListLog, getPersistedWatchListLog(returnedWatchListLog));

        insertedWatchListLog = returnedWatchListLog;
    }

    @Test
    @Transactional
    void createWatchListLogWithExistingId() throws Exception {
        // Create the WatchListLog with an existing ID
        watchListLog.setId(1L);
        WatchListLogDTO watchListLogDTO = watchListLogMapper.toDto(watchListLog);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWatchListLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(watchListLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WatchListLog in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWatchListLogs() throws Exception {
        // Initialize the database
        insertedWatchListLog = watchListLogRepository.saveAndFlush(watchListLog);

        // Get all the watchListLogList
        restWatchListLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(watchListLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].updateType").value(hasItem(DEFAULT_UPDATE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].userAgent").value(hasItem(DEFAULT_USER_AGENT)));
    }

    @Test
    @Transactional
    void getWatchListLog() throws Exception {
        // Initialize the database
        insertedWatchListLog = watchListLogRepository.saveAndFlush(watchListLog);

        // Get the watchListLog
        restWatchListLogMockMvc
            .perform(get(ENTITY_API_URL_ID, watchListLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(watchListLog.getId().intValue()))
            .andExpect(jsonPath("$.updateType").value(DEFAULT_UPDATE_TYPE.toString()))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.userAgent").value(DEFAULT_USER_AGENT));
    }

    @Test
    @Transactional
    void getNonExistingWatchListLog() throws Exception {
        // Get the watchListLog
        restWatchListLogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWatchListLog() throws Exception {
        // Initialize the database
        insertedWatchListLog = watchListLogRepository.saveAndFlush(watchListLog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the watchListLog
        WatchListLog updatedWatchListLog = watchListLogRepository.findById(watchListLog.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWatchListLog are not directly saved in db
        em.detach(updatedWatchListLog);
        updatedWatchListLog.updateType(UPDATED_UPDATE_TYPE).updateTime(UPDATED_UPDATE_TIME).userAgent(UPDATED_USER_AGENT);
        WatchListLogDTO watchListLogDTO = watchListLogMapper.toDto(updatedWatchListLog);

        restWatchListLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, watchListLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(watchListLogDTO))
            )
            .andExpect(status().isOk());

        // Validate the WatchListLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWatchListLogToMatchAllProperties(updatedWatchListLog);
    }

    @Test
    @Transactional
    void putNonExistingWatchListLog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchListLog.setId(longCount.incrementAndGet());

        // Create the WatchListLog
        WatchListLogDTO watchListLogDTO = watchListLogMapper.toDto(watchListLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWatchListLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, watchListLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(watchListLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchListLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWatchListLog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchListLog.setId(longCount.incrementAndGet());

        // Create the WatchListLog
        WatchListLogDTO watchListLogDTO = watchListLogMapper.toDto(watchListLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchListLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(watchListLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchListLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWatchListLog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchListLog.setId(longCount.incrementAndGet());

        // Create the WatchListLog
        WatchListLogDTO watchListLogDTO = watchListLogMapper.toDto(watchListLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchListLogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(watchListLogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WatchListLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWatchListLogWithPatch() throws Exception {
        // Initialize the database
        insertedWatchListLog = watchListLogRepository.saveAndFlush(watchListLog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the watchListLog using partial update
        WatchListLog partialUpdatedWatchListLog = new WatchListLog();
        partialUpdatedWatchListLog.setId(watchListLog.getId());

        partialUpdatedWatchListLog.updateType(UPDATED_UPDATE_TYPE);

        restWatchListLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWatchListLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWatchListLog))
            )
            .andExpect(status().isOk());

        // Validate the WatchListLog in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWatchListLogUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWatchListLog, watchListLog),
            getPersistedWatchListLog(watchListLog)
        );
    }

    @Test
    @Transactional
    void fullUpdateWatchListLogWithPatch() throws Exception {
        // Initialize the database
        insertedWatchListLog = watchListLogRepository.saveAndFlush(watchListLog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the watchListLog using partial update
        WatchListLog partialUpdatedWatchListLog = new WatchListLog();
        partialUpdatedWatchListLog.setId(watchListLog.getId());

        partialUpdatedWatchListLog.updateType(UPDATED_UPDATE_TYPE).updateTime(UPDATED_UPDATE_TIME).userAgent(UPDATED_USER_AGENT);

        restWatchListLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWatchListLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWatchListLog))
            )
            .andExpect(status().isOk());

        // Validate the WatchListLog in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWatchListLogUpdatableFieldsEquals(partialUpdatedWatchListLog, getPersistedWatchListLog(partialUpdatedWatchListLog));
    }

    @Test
    @Transactional
    void patchNonExistingWatchListLog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchListLog.setId(longCount.incrementAndGet());

        // Create the WatchListLog
        WatchListLogDTO watchListLogDTO = watchListLogMapper.toDto(watchListLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWatchListLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, watchListLogDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(watchListLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchListLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWatchListLog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchListLog.setId(longCount.incrementAndGet());

        // Create the WatchListLog
        WatchListLogDTO watchListLogDTO = watchListLogMapper.toDto(watchListLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchListLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(watchListLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchListLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWatchListLog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchListLog.setId(longCount.incrementAndGet());

        // Create the WatchListLog
        WatchListLogDTO watchListLogDTO = watchListLogMapper.toDto(watchListLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchListLogMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(watchListLogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WatchListLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWatchListLog() throws Exception {
        // Initialize the database
        insertedWatchListLog = watchListLogRepository.saveAndFlush(watchListLog);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the watchListLog
        restWatchListLogMockMvc
            .perform(delete(ENTITY_API_URL_ID, watchListLog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return watchListLogRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected WatchListLog getPersistedWatchListLog(WatchListLog watchListLog) {
        return watchListLogRepository.findById(watchListLog.getId()).orElseThrow();
    }

    protected void assertPersistedWatchListLogToMatchAllProperties(WatchListLog expectedWatchListLog) {
        assertWatchListLogAllPropertiesEquals(expectedWatchListLog, getPersistedWatchListLog(expectedWatchListLog));
    }

    protected void assertPersistedWatchListLogToMatchUpdatableProperties(WatchListLog expectedWatchListLog) {
        assertWatchListLogAllUpdatablePropertiesEquals(expectedWatchListLog, getPersistedWatchListLog(expectedWatchListLog));
    }
}
