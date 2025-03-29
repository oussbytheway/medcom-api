package com.pharmaresolve.medcom.web.rest;

import static com.pharmaresolve.medcom.domain.WatchListAsserts.*;
import static com.pharmaresolve.medcom.web.rest.TestUtil.createUpdateProxyForBean;
import static com.pharmaresolve.medcom.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmaresolve.medcom.IntegrationTest;
import com.pharmaresolve.medcom.domain.Pharmacy;
import com.pharmaresolve.medcom.domain.WatchList;
import com.pharmaresolve.medcom.repository.WatchListRepository;
import com.pharmaresolve.medcom.service.dto.WatchListDTO;
import com.pharmaresolve.medcom.service.mapper.WatchListMapper;
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
 * Integration tests for the {@link WatchListResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WatchListResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_LIMIT = 1;
    private static final Integer UPDATED_LIMIT = 2;

    private static final String ENTITY_API_URL = "/api/watch-lists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WatchListRepository watchListRepository;

    @Autowired
    private WatchListMapper watchListMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWatchListMockMvc;

    private WatchList watchList;

    private WatchList insertedWatchList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WatchList createEntity(EntityManager em) {
        WatchList watchList = new WatchList().name(DEFAULT_NAME).created(DEFAULT_CREATED).limit(DEFAULT_LIMIT);
        // Add required entity
        Pharmacy pharmacy;
        if (TestUtil.findAll(em, Pharmacy.class).isEmpty()) {
            pharmacy = PharmacyResourceIT.createEntity();
            em.persist(pharmacy);
            em.flush();
        } else {
            pharmacy = TestUtil.findAll(em, Pharmacy.class).get(0);
        }
        watchList.setPharmacy(pharmacy);
        return watchList;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WatchList createUpdatedEntity(EntityManager em) {
        WatchList updatedWatchList = new WatchList().name(UPDATED_NAME).created(UPDATED_CREATED).limit(UPDATED_LIMIT);
        // Add required entity
        Pharmacy pharmacy;
        pharmacy = PharmacyResourceIT.createUpdatedEntity();
        em.persist(pharmacy);
        em.flush();
        updatedWatchList.setPharmacy(pharmacy);
        return updatedWatchList;
    }

    @BeforeEach
    public void initTest() {
        watchList = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedWatchList != null) {
            watchListRepository.delete(insertedWatchList);
            insertedWatchList = null;
        }
    }

    @Test
    @Transactional
    void createWatchList() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the WatchList
        WatchListDTO watchListDTO = watchListMapper.toDto(watchList);
        var returnedWatchListDTO = om.readValue(
            restWatchListMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(watchListDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            WatchListDTO.class
        );

        // Validate the WatchList in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedWatchList = watchListMapper.toEntity(returnedWatchListDTO);
        assertWatchListUpdatableFieldsEquals(returnedWatchList, getPersistedWatchList(returnedWatchList));

        assertWatchListMapsIdRelationshipPersistedValue(watchList, returnedWatchList);

        insertedWatchList = returnedWatchList;
    }

    @Test
    @Transactional
    void createWatchListWithExistingId() throws Exception {
        // Create the WatchList with an existing ID
        watchList.setId(1L);
        WatchListDTO watchListDTO = watchListMapper.toDto(watchList);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWatchListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(watchListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WatchList in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void updateWatchListMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        insertedWatchList = watchListRepository.saveAndFlush(watchList);
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Add a new parent entity
        Pharmacy pharmacy = PharmacyResourceIT.createUpdatedEntity();
        em.persist(pharmacy);
        em.flush();

        // Load the watchList
        WatchList updatedWatchList = watchListRepository.findById(watchList.getId()).orElseThrow();
        assertThat(updatedWatchList).isNotNull();
        // Disconnect from session so that the updates on updatedWatchList are not directly saved in db
        em.detach(updatedWatchList);

        // Update the Pharmacy with new association value
        updatedWatchList.setPharmacy(pharmacy);
        WatchListDTO updatedWatchListDTO = watchListMapper.toDto(updatedWatchList);
        assertThat(updatedWatchListDTO).isNotNull();

        // Update the entity
        restWatchListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWatchListDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWatchListDTO))
            )
            .andExpect(status().isOk());

        // Validate the WatchList in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        /**
         * Validate the id for MapsId, the ids must be same
         * Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
         * Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
         * assertThat(testWatchList.getId()).isEqualTo(testWatchList.getPharmacy().getId());
         */
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        watchList.setName(null);

        // Create the WatchList, which fails.
        WatchListDTO watchListDTO = watchListMapper.toDto(watchList);

        restWatchListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(watchListDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWatchLists() throws Exception {
        // Initialize the database
        insertedWatchList = watchListRepository.saveAndFlush(watchList);

        // Get all the watchListList
        restWatchListMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(watchList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].limit").value(hasItem(DEFAULT_LIMIT)));
    }

    @Test
    @Transactional
    void getWatchList() throws Exception {
        // Initialize the database
        insertedWatchList = watchListRepository.saveAndFlush(watchList);

        // Get the watchList
        restWatchListMockMvc
            .perform(get(ENTITY_API_URL_ID, watchList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(watchList.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.limit").value(DEFAULT_LIMIT));
    }

    @Test
    @Transactional
    void getNonExistingWatchList() throws Exception {
        // Get the watchList
        restWatchListMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWatchList() throws Exception {
        // Initialize the database
        insertedWatchList = watchListRepository.saveAndFlush(watchList);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the watchList
        WatchList updatedWatchList = watchListRepository.findById(watchList.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWatchList are not directly saved in db
        em.detach(updatedWatchList);
        updatedWatchList.name(UPDATED_NAME).created(UPDATED_CREATED).limit(UPDATED_LIMIT);
        WatchListDTO watchListDTO = watchListMapper.toDto(updatedWatchList);

        restWatchListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, watchListDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(watchListDTO))
            )
            .andExpect(status().isOk());

        // Validate the WatchList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWatchListToMatchAllProperties(updatedWatchList);
    }

    @Test
    @Transactional
    void putNonExistingWatchList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchList.setId(longCount.incrementAndGet());

        // Create the WatchList
        WatchListDTO watchListDTO = watchListMapper.toDto(watchList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWatchListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, watchListDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(watchListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWatchList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchList.setId(longCount.incrementAndGet());

        // Create the WatchList
        WatchListDTO watchListDTO = watchListMapper.toDto(watchList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(watchListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWatchList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchList.setId(longCount.incrementAndGet());

        // Create the WatchList
        WatchListDTO watchListDTO = watchListMapper.toDto(watchList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchListMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(watchListDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WatchList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWatchListWithPatch() throws Exception {
        // Initialize the database
        insertedWatchList = watchListRepository.saveAndFlush(watchList);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the watchList using partial update
        WatchList partialUpdatedWatchList = new WatchList();
        partialUpdatedWatchList.setId(watchList.getId());

        partialUpdatedWatchList.name(UPDATED_NAME);

        restWatchListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWatchList.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWatchList))
            )
            .andExpect(status().isOk());

        // Validate the WatchList in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWatchListUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWatchList, watchList),
            getPersistedWatchList(watchList)
        );
    }

    @Test
    @Transactional
    void fullUpdateWatchListWithPatch() throws Exception {
        // Initialize the database
        insertedWatchList = watchListRepository.saveAndFlush(watchList);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the watchList using partial update
        WatchList partialUpdatedWatchList = new WatchList();
        partialUpdatedWatchList.setId(watchList.getId());

        partialUpdatedWatchList.name(UPDATED_NAME).created(UPDATED_CREATED).limit(UPDATED_LIMIT);

        restWatchListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWatchList.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWatchList))
            )
            .andExpect(status().isOk());

        // Validate the WatchList in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWatchListUpdatableFieldsEquals(partialUpdatedWatchList, getPersistedWatchList(partialUpdatedWatchList));
    }

    @Test
    @Transactional
    void patchNonExistingWatchList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchList.setId(longCount.incrementAndGet());

        // Create the WatchList
        WatchListDTO watchListDTO = watchListMapper.toDto(watchList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWatchListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, watchListDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(watchListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWatchList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchList.setId(longCount.incrementAndGet());

        // Create the WatchList
        WatchListDTO watchListDTO = watchListMapper.toDto(watchList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(watchListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWatchList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchList.setId(longCount.incrementAndGet());

        // Create the WatchList
        WatchListDTO watchListDTO = watchListMapper.toDto(watchList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchListMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(watchListDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WatchList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWatchList() throws Exception {
        // Initialize the database
        insertedWatchList = watchListRepository.saveAndFlush(watchList);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the watchList
        restWatchListMockMvc
            .perform(delete(ENTITY_API_URL_ID, watchList.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return watchListRepository.count();
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

    protected WatchList getPersistedWatchList(WatchList watchList) {
        return watchListRepository.findById(watchList.getId()).orElseThrow();
    }

    protected void assertPersistedWatchListToMatchAllProperties(WatchList expectedWatchList) {
        assertWatchListAllPropertiesEquals(expectedWatchList, getPersistedWatchList(expectedWatchList));
    }

    protected void assertPersistedWatchListToMatchUpdatableProperties(WatchList expectedWatchList) {
        assertWatchListAllUpdatablePropertiesEquals(expectedWatchList, getPersistedWatchList(expectedWatchList));
    }
}
