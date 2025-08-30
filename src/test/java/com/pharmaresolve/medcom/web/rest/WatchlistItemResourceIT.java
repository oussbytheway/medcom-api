package com.pharmaresolve.medcom.web.rest;

import static com.pharmaresolve.medcom.domain.WatchlistItemAsserts.*;
import static com.pharmaresolve.medcom.web.rest.TestUtil.createUpdateProxyForBean;
import static com.pharmaresolve.medcom.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmaresolve.medcom.IntegrationTest;
import com.pharmaresolve.medcom.domain.WatchlistItem;
import com.pharmaresolve.medcom.repository.WatchlistItemRepository;
import com.pharmaresolve.medcom.service.dto.WatchlistItemDTO;
import com.pharmaresolve.medcom.service.mapper.WatchlistItemMapper;
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
 * Integration tests for the {@link WatchlistItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WatchlistItemResourceIT {

    private static final ZonedDateTime DEFAULT_DATE_ADDED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_ADDED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;

    private static final String DEFAULT_ADDED_BY = "AAAAAAAAAA";
    private static final String UPDATED_ADDED_BY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ALERT_ENABLED = false;
    private static final Boolean UPDATED_ALERT_ENABLED = true;

    private static final String ENTITY_API_URL = "/api/watchlist-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WatchlistItemRepository watchlistItemRepository;

    @Autowired
    private WatchlistItemMapper watchlistItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWatchlistItemMockMvc;

    private WatchlistItem watchlistItem;

    private WatchlistItem insertedWatchlistItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WatchlistItem createEntity() {
        return new WatchlistItem()
            .dateAdded(DEFAULT_DATE_ADDED)
            .priority(DEFAULT_PRIORITY)
            .addedBy(DEFAULT_ADDED_BY)
            .alertEnabled(DEFAULT_ALERT_ENABLED);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WatchlistItem createUpdatedEntity() {
        return new WatchlistItem()
            .dateAdded(UPDATED_DATE_ADDED)
            .priority(UPDATED_PRIORITY)
            .addedBy(UPDATED_ADDED_BY)
            .alertEnabled(UPDATED_ALERT_ENABLED);
    }

    @BeforeEach
    void initTest() {
        watchlistItem = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedWatchlistItem != null) {
            watchlistItemRepository.delete(insertedWatchlistItem);
            insertedWatchlistItem = null;
        }
    }

    @Test
    @Transactional
    void createWatchlistItem() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the WatchlistItem
        WatchlistItemDTO watchlistItemDTO = watchlistItemMapper.toDto(watchlistItem);
        var returnedWatchlistItemDTO = om.readValue(
            restWatchlistItemMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(watchlistItemDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            WatchlistItemDTO.class
        );

        // Validate the WatchlistItem in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedWatchlistItem = watchlistItemMapper.toEntity(returnedWatchlistItemDTO);
        assertWatchlistItemUpdatableFieldsEquals(returnedWatchlistItem, getPersistedWatchlistItem(returnedWatchlistItem));

        insertedWatchlistItem = returnedWatchlistItem;
    }

    @Test
    @Transactional
    void createWatchlistItemWithExistingId() throws Exception {
        // Create the WatchlistItem with an existing ID
        watchlistItem.setId(1L);
        WatchlistItemDTO watchlistItemDTO = watchlistItemMapper.toDto(watchlistItem);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWatchlistItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(watchlistItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WatchlistItem in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWatchlistItems() throws Exception {
        // Initialize the database
        insertedWatchlistItem = watchlistItemRepository.saveAndFlush(watchlistItem);

        // Get all the watchlistItemList
        restWatchlistItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(watchlistItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(sameInstant(DEFAULT_DATE_ADDED))))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY)))
            .andExpect(jsonPath("$.[*].alertEnabled").value(hasItem(DEFAULT_ALERT_ENABLED)));
    }

    @Test
    @Transactional
    void getWatchlistItem() throws Exception {
        // Initialize the database
        insertedWatchlistItem = watchlistItemRepository.saveAndFlush(watchlistItem);

        // Get the watchlistItem
        restWatchlistItemMockMvc
            .perform(get(ENTITY_API_URL_ID, watchlistItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(watchlistItem.getId().intValue()))
            .andExpect(jsonPath("$.dateAdded").value(sameInstant(DEFAULT_DATE_ADDED)))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.addedBy").value(DEFAULT_ADDED_BY))
            .andExpect(jsonPath("$.alertEnabled").value(DEFAULT_ALERT_ENABLED));
    }

    @Test
    @Transactional
    void getNonExistingWatchlistItem() throws Exception {
        // Get the watchlistItem
        restWatchlistItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWatchlistItem() throws Exception {
        // Initialize the database
        insertedWatchlistItem = watchlistItemRepository.saveAndFlush(watchlistItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the watchlistItem
        WatchlistItem updatedWatchlistItem = watchlistItemRepository.findById(watchlistItem.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWatchlistItem are not directly saved in db
        em.detach(updatedWatchlistItem);
        updatedWatchlistItem
            .dateAdded(UPDATED_DATE_ADDED)
            .priority(UPDATED_PRIORITY)
            .addedBy(UPDATED_ADDED_BY)
            .alertEnabled(UPDATED_ALERT_ENABLED);
        WatchlistItemDTO watchlistItemDTO = watchlistItemMapper.toDto(updatedWatchlistItem);

        restWatchlistItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, watchlistItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(watchlistItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the WatchlistItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWatchlistItemToMatchAllProperties(updatedWatchlistItem);
    }

    @Test
    @Transactional
    void putNonExistingWatchlistItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchlistItem.setId(longCount.incrementAndGet());

        // Create the WatchlistItem
        WatchlistItemDTO watchlistItemDTO = watchlistItemMapper.toDto(watchlistItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWatchlistItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, watchlistItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(watchlistItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchlistItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWatchlistItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchlistItem.setId(longCount.incrementAndGet());

        // Create the WatchlistItem
        WatchlistItemDTO watchlistItemDTO = watchlistItemMapper.toDto(watchlistItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchlistItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(watchlistItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchlistItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWatchlistItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchlistItem.setId(longCount.incrementAndGet());

        // Create the WatchlistItem
        WatchlistItemDTO watchlistItemDTO = watchlistItemMapper.toDto(watchlistItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchlistItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(watchlistItemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WatchlistItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWatchlistItemWithPatch() throws Exception {
        // Initialize the database
        insertedWatchlistItem = watchlistItemRepository.saveAndFlush(watchlistItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the watchlistItem using partial update
        WatchlistItem partialUpdatedWatchlistItem = new WatchlistItem();
        partialUpdatedWatchlistItem.setId(watchlistItem.getId());

        partialUpdatedWatchlistItem
            .dateAdded(UPDATED_DATE_ADDED)
            .priority(UPDATED_PRIORITY)
            .addedBy(UPDATED_ADDED_BY)
            .alertEnabled(UPDATED_ALERT_ENABLED);

        restWatchlistItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWatchlistItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWatchlistItem))
            )
            .andExpect(status().isOk());

        // Validate the WatchlistItem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWatchlistItemUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWatchlistItem, watchlistItem),
            getPersistedWatchlistItem(watchlistItem)
        );
    }

    @Test
    @Transactional
    void fullUpdateWatchlistItemWithPatch() throws Exception {
        // Initialize the database
        insertedWatchlistItem = watchlistItemRepository.saveAndFlush(watchlistItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the watchlistItem using partial update
        WatchlistItem partialUpdatedWatchlistItem = new WatchlistItem();
        partialUpdatedWatchlistItem.setId(watchlistItem.getId());

        partialUpdatedWatchlistItem
            .dateAdded(UPDATED_DATE_ADDED)
            .priority(UPDATED_PRIORITY)
            .addedBy(UPDATED_ADDED_BY)
            .alertEnabled(UPDATED_ALERT_ENABLED);

        restWatchlistItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWatchlistItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWatchlistItem))
            )
            .andExpect(status().isOk());

        // Validate the WatchlistItem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWatchlistItemUpdatableFieldsEquals(partialUpdatedWatchlistItem, getPersistedWatchlistItem(partialUpdatedWatchlistItem));
    }

    @Test
    @Transactional
    void patchNonExistingWatchlistItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchlistItem.setId(longCount.incrementAndGet());

        // Create the WatchlistItem
        WatchlistItemDTO watchlistItemDTO = watchlistItemMapper.toDto(watchlistItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWatchlistItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, watchlistItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(watchlistItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchlistItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWatchlistItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchlistItem.setId(longCount.incrementAndGet());

        // Create the WatchlistItem
        WatchlistItemDTO watchlistItemDTO = watchlistItemMapper.toDto(watchlistItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchlistItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(watchlistItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchlistItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWatchlistItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchlistItem.setId(longCount.incrementAndGet());

        // Create the WatchlistItem
        WatchlistItemDTO watchlistItemDTO = watchlistItemMapper.toDto(watchlistItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchlistItemMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(watchlistItemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WatchlistItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWatchlistItem() throws Exception {
        // Initialize the database
        insertedWatchlistItem = watchlistItemRepository.saveAndFlush(watchlistItem);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the watchlistItem
        restWatchlistItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, watchlistItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return watchlistItemRepository.count();
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

    protected WatchlistItem getPersistedWatchlistItem(WatchlistItem watchlistItem) {
        return watchlistItemRepository.findById(watchlistItem.getId()).orElseThrow();
    }

    protected void assertPersistedWatchlistItemToMatchAllProperties(WatchlistItem expectedWatchlistItem) {
        assertWatchlistItemAllPropertiesEquals(expectedWatchlistItem, getPersistedWatchlistItem(expectedWatchlistItem));
    }

    protected void assertPersistedWatchlistItemToMatchUpdatableProperties(WatchlistItem expectedWatchlistItem) {
        assertWatchlistItemAllUpdatablePropertiesEquals(expectedWatchlistItem, getPersistedWatchlistItem(expectedWatchlistItem));
    }
}
