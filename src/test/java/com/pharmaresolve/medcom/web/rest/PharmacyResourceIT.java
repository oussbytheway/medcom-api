package com.pharmaresolve.medcom.web.rest;

import static com.pharmaresolve.medcom.domain.PharmacyAsserts.*;
import static com.pharmaresolve.medcom.web.rest.TestUtil.createUpdateProxyForBean;
import static com.pharmaresolve.medcom.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmaresolve.medcom.IntegrationTest;
import com.pharmaresolve.medcom.domain.Pharmacy;
import com.pharmaresolve.medcom.repository.PharmacyRepository;
import com.pharmaresolve.medcom.service.dto.PharmacyDTO;
import com.pharmaresolve.medcom.service.mapper.PharmacyMapper;
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
 * Integration tests for the {@link PharmacyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PharmacyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_DEACTIVATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_DEACTIVATED_BY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final String DEFAULT_DELETED_BY = "AAAAAAAAAA";
    private static final String UPDATED_DELETED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pharmacies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private PharmacyMapper pharmacyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPharmacyMockMvc;

    private Pharmacy pharmacy;

    private Pharmacy insertedPharmacy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pharmacy createEntity() {
        return new Pharmacy()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .website(DEFAULT_WEBSITE)
            .active(DEFAULT_ACTIVE)
            .created(DEFAULT_CREATED)
            .createdBy(DEFAULT_CREATED_BY)
            .activatedBy(DEFAULT_ACTIVATED_BY)
            .deactivatedBy(DEFAULT_DEACTIVATED_BY)
            .deleted(DEFAULT_DELETED)
            .deletedBy(DEFAULT_DELETED_BY);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pharmacy createUpdatedEntity() {
        return new Pharmacy()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .website(UPDATED_WEBSITE)
            .active(UPDATED_ACTIVE)
            .created(UPDATED_CREATED)
            .createdBy(UPDATED_CREATED_BY)
            .activatedBy(UPDATED_ACTIVATED_BY)
            .deactivatedBy(UPDATED_DEACTIVATED_BY)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY);
    }

    @BeforeEach
    void initTest() {
        pharmacy = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPharmacy != null) {
            pharmacyRepository.delete(insertedPharmacy);
            insertedPharmacy = null;
        }
    }

    @Test
    @Transactional
    void createPharmacy() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Pharmacy
        PharmacyDTO pharmacyDTO = pharmacyMapper.toDto(pharmacy);
        var returnedPharmacyDTO = om.readValue(
            restPharmacyMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pharmacyDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PharmacyDTO.class
        );

        // Validate the Pharmacy in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPharmacy = pharmacyMapper.toEntity(returnedPharmacyDTO);
        assertPharmacyUpdatableFieldsEquals(returnedPharmacy, getPersistedPharmacy(returnedPharmacy));

        insertedPharmacy = returnedPharmacy;
    }

    @Test
    @Transactional
    void createPharmacyWithExistingId() throws Exception {
        // Create the Pharmacy with an existing ID
        pharmacy.setId(1L);
        PharmacyDTO pharmacyDTO = pharmacyMapper.toDto(pharmacy);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPharmacyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pharmacyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pharmacy in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pharmacy.setName(null);

        // Create the Pharmacy, which fails.
        PharmacyDTO pharmacyDTO = pharmacyMapper.toDto(pharmacy);

        restPharmacyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pharmacyDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pharmacy.setActive(null);

        // Create the Pharmacy, which fails.
        PharmacyDTO pharmacyDTO = pharmacyMapper.toDto(pharmacy);

        restPharmacyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pharmacyDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPharmacies() throws Exception {
        // Initialize the database
        insertedPharmacy = pharmacyRepository.saveAndFlush(pharmacy);

        // Get all the pharmacyList
        restPharmacyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pharmacy.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].activatedBy").value(hasItem(DEFAULT_ACTIVATED_BY)))
            .andExpect(jsonPath("$.[*].deactivatedBy").value(hasItem(DEFAULT_DEACTIVATED_BY)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED)))
            .andExpect(jsonPath("$.[*].deletedBy").value(hasItem(DEFAULT_DELETED_BY)));
    }

    @Test
    @Transactional
    void getPharmacy() throws Exception {
        // Initialize the database
        insertedPharmacy = pharmacyRepository.saveAndFlush(pharmacy);

        // Get the pharmacy
        restPharmacyMockMvc
            .perform(get(ENTITY_API_URL_ID, pharmacy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pharmacy.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.activatedBy").value(DEFAULT_ACTIVATED_BY))
            .andExpect(jsonPath("$.deactivatedBy").value(DEFAULT_DEACTIVATED_BY))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED))
            .andExpect(jsonPath("$.deletedBy").value(DEFAULT_DELETED_BY));
    }

    @Test
    @Transactional
    void getNonExistingPharmacy() throws Exception {
        // Get the pharmacy
        restPharmacyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPharmacy() throws Exception {
        // Initialize the database
        insertedPharmacy = pharmacyRepository.saveAndFlush(pharmacy);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pharmacy
        Pharmacy updatedPharmacy = pharmacyRepository.findById(pharmacy.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPharmacy are not directly saved in db
        em.detach(updatedPharmacy);
        updatedPharmacy
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .website(UPDATED_WEBSITE)
            .active(UPDATED_ACTIVE)
            .created(UPDATED_CREATED)
            .createdBy(UPDATED_CREATED_BY)
            .activatedBy(UPDATED_ACTIVATED_BY)
            .deactivatedBy(UPDATED_DEACTIVATED_BY)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY);
        PharmacyDTO pharmacyDTO = pharmacyMapper.toDto(updatedPharmacy);

        restPharmacyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pharmacyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pharmacyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Pharmacy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPharmacyToMatchAllProperties(updatedPharmacy);
    }

    @Test
    @Transactional
    void putNonExistingPharmacy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pharmacy.setId(longCount.incrementAndGet());

        // Create the Pharmacy
        PharmacyDTO pharmacyDTO = pharmacyMapper.toDto(pharmacy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPharmacyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pharmacyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pharmacyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pharmacy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPharmacy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pharmacy.setId(longCount.incrementAndGet());

        // Create the Pharmacy
        PharmacyDTO pharmacyDTO = pharmacyMapper.toDto(pharmacy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPharmacyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pharmacyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pharmacy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPharmacy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pharmacy.setId(longCount.incrementAndGet());

        // Create the Pharmacy
        PharmacyDTO pharmacyDTO = pharmacyMapper.toDto(pharmacy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPharmacyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pharmacyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pharmacy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePharmacyWithPatch() throws Exception {
        // Initialize the database
        insertedPharmacy = pharmacyRepository.saveAndFlush(pharmacy);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pharmacy using partial update
        Pharmacy partialUpdatedPharmacy = new Pharmacy();
        partialUpdatedPharmacy.setId(pharmacy.getId());

        partialUpdatedPharmacy
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .website(UPDATED_WEBSITE)
            .active(UPDATED_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .deletedBy(UPDATED_DELETED_BY);

        restPharmacyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPharmacy.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPharmacy))
            )
            .andExpect(status().isOk());

        // Validate the Pharmacy in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPharmacyUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPharmacy, pharmacy), getPersistedPharmacy(pharmacy));
    }

    @Test
    @Transactional
    void fullUpdatePharmacyWithPatch() throws Exception {
        // Initialize the database
        insertedPharmacy = pharmacyRepository.saveAndFlush(pharmacy);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pharmacy using partial update
        Pharmacy partialUpdatedPharmacy = new Pharmacy();
        partialUpdatedPharmacy.setId(pharmacy.getId());

        partialUpdatedPharmacy
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .website(UPDATED_WEBSITE)
            .active(UPDATED_ACTIVE)
            .created(UPDATED_CREATED)
            .createdBy(UPDATED_CREATED_BY)
            .activatedBy(UPDATED_ACTIVATED_BY)
            .deactivatedBy(UPDATED_DEACTIVATED_BY)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY);

        restPharmacyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPharmacy.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPharmacy))
            )
            .andExpect(status().isOk());

        // Validate the Pharmacy in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPharmacyUpdatableFieldsEquals(partialUpdatedPharmacy, getPersistedPharmacy(partialUpdatedPharmacy));
    }

    @Test
    @Transactional
    void patchNonExistingPharmacy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pharmacy.setId(longCount.incrementAndGet());

        // Create the Pharmacy
        PharmacyDTO pharmacyDTO = pharmacyMapper.toDto(pharmacy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPharmacyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pharmacyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pharmacyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pharmacy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPharmacy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pharmacy.setId(longCount.incrementAndGet());

        // Create the Pharmacy
        PharmacyDTO pharmacyDTO = pharmacyMapper.toDto(pharmacy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPharmacyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pharmacyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pharmacy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPharmacy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pharmacy.setId(longCount.incrementAndGet());

        // Create the Pharmacy
        PharmacyDTO pharmacyDTO = pharmacyMapper.toDto(pharmacy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPharmacyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pharmacyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pharmacy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePharmacy() throws Exception {
        // Initialize the database
        insertedPharmacy = pharmacyRepository.saveAndFlush(pharmacy);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pharmacy
        restPharmacyMockMvc
            .perform(delete(ENTITY_API_URL_ID, pharmacy.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pharmacyRepository.count();
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

    protected Pharmacy getPersistedPharmacy(Pharmacy pharmacy) {
        return pharmacyRepository.findById(pharmacy.getId()).orElseThrow();
    }

    protected void assertPersistedPharmacyToMatchAllProperties(Pharmacy expectedPharmacy) {
        assertPharmacyAllPropertiesEquals(expectedPharmacy, getPersistedPharmacy(expectedPharmacy));
    }

    protected void assertPersistedPharmacyToMatchUpdatableProperties(Pharmacy expectedPharmacy) {
        assertPharmacyAllUpdatablePropertiesEquals(expectedPharmacy, getPersistedPharmacy(expectedPharmacy));
    }
}
