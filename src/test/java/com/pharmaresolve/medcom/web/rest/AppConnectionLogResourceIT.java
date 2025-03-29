package com.pharmaresolve.medcom.web.rest;

import static com.pharmaresolve.medcom.domain.AppConnectionLogAsserts.*;
import static com.pharmaresolve.medcom.web.rest.TestUtil.createUpdateProxyForBean;
import static com.pharmaresolve.medcom.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmaresolve.medcom.IntegrationTest;
import com.pharmaresolve.medcom.domain.AppConnectionLog;
import com.pharmaresolve.medcom.domain.enumeration.ConnectionType;
import com.pharmaresolve.medcom.repository.AppConnectionLogRepository;
import com.pharmaresolve.medcom.service.dto.AppConnectionLogDTO;
import com.pharmaresolve.medcom.service.mapper.AppConnectionLogMapper;
import jakarta.persistence.EntityManager;
import java.time.Duration;
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
 * Integration tests for the {@link AppConnectionLogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppConnectionLogResourceIT {

    private static final ConnectionType DEFAULT_TYPE = ConnectionType.LOGIN;
    private static final ConnectionType UPDATED_TYPE = ConnectionType.LOGOUT;

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_USER_AGENT = "AAAAAAAAAA";
    private static final String UPDATED_USER_AGENT = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final Duration DEFAULT_SESSION_DURATION = Duration.ofHours(6);
    private static final Duration UPDATED_SESSION_DURATION = Duration.ofHours(12);

    private static final ZonedDateTime DEFAULT_PREVIOUS_LOGIN_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PREVIOUS_LOGIN_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/app-connection-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AppConnectionLogRepository appConnectionLogRepository;

    @Autowired
    private AppConnectionLogMapper appConnectionLogMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppConnectionLogMockMvc;

    private AppConnectionLog appConnectionLog;

    private AppConnectionLog insertedAppConnectionLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppConnectionLog createEntity() {
        return new AppConnectionLog()
            .type(DEFAULT_TYPE)
            .time(DEFAULT_TIME)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .userAgent(DEFAULT_USER_AGENT)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .sessionDuration(DEFAULT_SESSION_DURATION)
            .previousLoginTime(DEFAULT_PREVIOUS_LOGIN_TIME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppConnectionLog createUpdatedEntity() {
        return new AppConnectionLog()
            .type(UPDATED_TYPE)
            .time(UPDATED_TIME)
            .ipAddress(UPDATED_IP_ADDRESS)
            .userAgent(UPDATED_USER_AGENT)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .sessionDuration(UPDATED_SESSION_DURATION)
            .previousLoginTime(UPDATED_PREVIOUS_LOGIN_TIME);
    }

    @BeforeEach
    public void initTest() {
        appConnectionLog = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAppConnectionLog != null) {
            appConnectionLogRepository.delete(insertedAppConnectionLog);
            insertedAppConnectionLog = null;
        }
    }

    @Test
    @Transactional
    void createAppConnectionLog() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AppConnectionLog
        AppConnectionLogDTO appConnectionLogDTO = appConnectionLogMapper.toDto(appConnectionLog);
        var returnedAppConnectionLogDTO = om.readValue(
            restAppConnectionLogMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appConnectionLogDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AppConnectionLogDTO.class
        );

        // Validate the AppConnectionLog in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAppConnectionLog = appConnectionLogMapper.toEntity(returnedAppConnectionLogDTO);
        assertAppConnectionLogUpdatableFieldsEquals(returnedAppConnectionLog, getPersistedAppConnectionLog(returnedAppConnectionLog));

        insertedAppConnectionLog = returnedAppConnectionLog;
    }

    @Test
    @Transactional
    void createAppConnectionLogWithExistingId() throws Exception {
        // Create the AppConnectionLog with an existing ID
        appConnectionLog.setId(1L);
        AppConnectionLogDTO appConnectionLogDTO = appConnectionLogMapper.toDto(appConnectionLog);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppConnectionLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appConnectionLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppConnectionLog in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppConnectionLogs() throws Exception {
        // Initialize the database
        insertedAppConnectionLog = appConnectionLogRepository.saveAndFlush(appConnectionLog);

        // Get all the appConnectionLogList
        restAppConnectionLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appConnectionLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].userAgent").value(hasItem(DEFAULT_USER_AGENT)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].sessionDuration").value(hasItem(DEFAULT_SESSION_DURATION.toString())))
            .andExpect(jsonPath("$.[*].previousLoginTime").value(hasItem(sameInstant(DEFAULT_PREVIOUS_LOGIN_TIME))));
    }

    @Test
    @Transactional
    void getAppConnectionLog() throws Exception {
        // Initialize the database
        insertedAppConnectionLog = appConnectionLogRepository.saveAndFlush(appConnectionLog);

        // Get the appConnectionLog
        restAppConnectionLogMockMvc
            .perform(get(ENTITY_API_URL_ID, appConnectionLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appConnectionLog.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
            .andExpect(jsonPath("$.userAgent").value(DEFAULT_USER_AGENT))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.sessionDuration").value(DEFAULT_SESSION_DURATION.toString()))
            .andExpect(jsonPath("$.previousLoginTime").value(sameInstant(DEFAULT_PREVIOUS_LOGIN_TIME)));
    }

    @Test
    @Transactional
    void getNonExistingAppConnectionLog() throws Exception {
        // Get the appConnectionLog
        restAppConnectionLogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppConnectionLog() throws Exception {
        // Initialize the database
        insertedAppConnectionLog = appConnectionLogRepository.saveAndFlush(appConnectionLog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appConnectionLog
        AppConnectionLog updatedAppConnectionLog = appConnectionLogRepository.findById(appConnectionLog.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAppConnectionLog are not directly saved in db
        em.detach(updatedAppConnectionLog);
        updatedAppConnectionLog
            .type(UPDATED_TYPE)
            .time(UPDATED_TIME)
            .ipAddress(UPDATED_IP_ADDRESS)
            .userAgent(UPDATED_USER_AGENT)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .sessionDuration(UPDATED_SESSION_DURATION)
            .previousLoginTime(UPDATED_PREVIOUS_LOGIN_TIME);
        AppConnectionLogDTO appConnectionLogDTO = appConnectionLogMapper.toDto(updatedAppConnectionLog);

        restAppConnectionLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appConnectionLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appConnectionLogDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppConnectionLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAppConnectionLogToMatchAllProperties(updatedAppConnectionLog);
    }

    @Test
    @Transactional
    void putNonExistingAppConnectionLog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appConnectionLog.setId(longCount.incrementAndGet());

        // Create the AppConnectionLog
        AppConnectionLogDTO appConnectionLogDTO = appConnectionLogMapper.toDto(appConnectionLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppConnectionLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appConnectionLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appConnectionLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppConnectionLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppConnectionLog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appConnectionLog.setId(longCount.incrementAndGet());

        // Create the AppConnectionLog
        AppConnectionLogDTO appConnectionLogDTO = appConnectionLogMapper.toDto(appConnectionLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppConnectionLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appConnectionLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppConnectionLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppConnectionLog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appConnectionLog.setId(longCount.incrementAndGet());

        // Create the AppConnectionLog
        AppConnectionLogDTO appConnectionLogDTO = appConnectionLogMapper.toDto(appConnectionLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppConnectionLogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appConnectionLogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppConnectionLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppConnectionLogWithPatch() throws Exception {
        // Initialize the database
        insertedAppConnectionLog = appConnectionLogRepository.saveAndFlush(appConnectionLog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appConnectionLog using partial update
        AppConnectionLog partialUpdatedAppConnectionLog = new AppConnectionLog();
        partialUpdatedAppConnectionLog.setId(appConnectionLog.getId());

        partialUpdatedAppConnectionLog.type(UPDATED_TYPE).latitude(UPDATED_LATITUDE).previousLoginTime(UPDATED_PREVIOUS_LOGIN_TIME);

        restAppConnectionLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppConnectionLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppConnectionLog))
            )
            .andExpect(status().isOk());

        // Validate the AppConnectionLog in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppConnectionLogUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAppConnectionLog, appConnectionLog),
            getPersistedAppConnectionLog(appConnectionLog)
        );
    }

    @Test
    @Transactional
    void fullUpdateAppConnectionLogWithPatch() throws Exception {
        // Initialize the database
        insertedAppConnectionLog = appConnectionLogRepository.saveAndFlush(appConnectionLog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appConnectionLog using partial update
        AppConnectionLog partialUpdatedAppConnectionLog = new AppConnectionLog();
        partialUpdatedAppConnectionLog.setId(appConnectionLog.getId());

        partialUpdatedAppConnectionLog
            .type(UPDATED_TYPE)
            .time(UPDATED_TIME)
            .ipAddress(UPDATED_IP_ADDRESS)
            .userAgent(UPDATED_USER_AGENT)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .sessionDuration(UPDATED_SESSION_DURATION)
            .previousLoginTime(UPDATED_PREVIOUS_LOGIN_TIME);

        restAppConnectionLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppConnectionLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppConnectionLog))
            )
            .andExpect(status().isOk());

        // Validate the AppConnectionLog in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppConnectionLogUpdatableFieldsEquals(
            partialUpdatedAppConnectionLog,
            getPersistedAppConnectionLog(partialUpdatedAppConnectionLog)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAppConnectionLog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appConnectionLog.setId(longCount.incrementAndGet());

        // Create the AppConnectionLog
        AppConnectionLogDTO appConnectionLogDTO = appConnectionLogMapper.toDto(appConnectionLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppConnectionLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appConnectionLogDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appConnectionLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppConnectionLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppConnectionLog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appConnectionLog.setId(longCount.incrementAndGet());

        // Create the AppConnectionLog
        AppConnectionLogDTO appConnectionLogDTO = appConnectionLogMapper.toDto(appConnectionLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppConnectionLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appConnectionLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppConnectionLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppConnectionLog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appConnectionLog.setId(longCount.incrementAndGet());

        // Create the AppConnectionLog
        AppConnectionLogDTO appConnectionLogDTO = appConnectionLogMapper.toDto(appConnectionLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppConnectionLogMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(appConnectionLogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppConnectionLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppConnectionLog() throws Exception {
        // Initialize the database
        insertedAppConnectionLog = appConnectionLogRepository.saveAndFlush(appConnectionLog);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the appConnectionLog
        restAppConnectionLogMockMvc
            .perform(delete(ENTITY_API_URL_ID, appConnectionLog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return appConnectionLogRepository.count();
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

    protected AppConnectionLog getPersistedAppConnectionLog(AppConnectionLog appConnectionLog) {
        return appConnectionLogRepository.findById(appConnectionLog.getId()).orElseThrow();
    }

    protected void assertPersistedAppConnectionLogToMatchAllProperties(AppConnectionLog expectedAppConnectionLog) {
        assertAppConnectionLogAllPropertiesEquals(expectedAppConnectionLog, getPersistedAppConnectionLog(expectedAppConnectionLog));
    }

    protected void assertPersistedAppConnectionLogToMatchUpdatableProperties(AppConnectionLog expectedAppConnectionLog) {
        assertAppConnectionLogAllUpdatablePropertiesEquals(
            expectedAppConnectionLog,
            getPersistedAppConnectionLog(expectedAppConnectionLog)
        );
    }
}
