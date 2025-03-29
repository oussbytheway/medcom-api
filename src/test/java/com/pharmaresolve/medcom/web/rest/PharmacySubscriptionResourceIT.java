package com.pharmaresolve.medcom.web.rest;

import static com.pharmaresolve.medcom.domain.PharmacySubscriptionAsserts.*;
import static com.pharmaresolve.medcom.web.rest.TestUtil.createUpdateProxyForBean;
import static com.pharmaresolve.medcom.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmaresolve.medcom.IntegrationTest;
import com.pharmaresolve.medcom.domain.PharmacySubscription;
import com.pharmaresolve.medcom.domain.enumeration.BillingCycle;
import com.pharmaresolve.medcom.domain.enumeration.NotificationType;
import com.pharmaresolve.medcom.repository.PharmacySubscriptionRepository;
import com.pharmaresolve.medcom.service.dto.PharmacySubscriptionDTO;
import com.pharmaresolve.medcom.service.mapper.PharmacySubscriptionMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link PharmacySubscriptionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PharmacySubscriptionResourceIT {

    private static final String DEFAULT_PLAN = "AAAAAAAAAA";
    private static final String UPDATED_PLAN = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAX_WATCH_LIST_ITEMS = 1;
    private static final Integer UPDATED_MAX_WATCH_LIST_ITEMS = 2;

    private static final Integer DEFAULT_MAX_USERS = 1;
    private static final Integer UPDATED_MAX_USERS = 2;

    private static final Integer DEFAULT_MAX_EMAILS_PER_MONTH = 1;
    private static final Integer UPDATED_MAX_EMAILS_PER_MONTH = 2;

    private static final Integer DEFAULT_MAX_SMS_PER_MONTH = 1;
    private static final Integer UPDATED_MAX_SMS_PER_MONTH = 2;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final BillingCycle DEFAULT_BILLING_CYCLE = BillingCycle.MONTHLY;
    private static final BillingCycle UPDATED_BILLING_CYCLE = BillingCycle.YEARLY;

    private static final Integer DEFAULT_TRIAL_PERIOD_DAYS = 1;
    private static final Integer UPDATED_TRIAL_PERIOD_DAYS = 2;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final NotificationType DEFAULT_NOTIFICATION_TYPES = NotificationType.EMAIL;
    private static final NotificationType UPDATED_NOTIFICATION_TYPES = NotificationType.SMS;

    private static final String ENTITY_API_URL = "/api/pharmacy-subscriptions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PharmacySubscriptionRepository pharmacySubscriptionRepository;

    @Autowired
    private PharmacySubscriptionMapper pharmacySubscriptionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPharmacySubscriptionMockMvc;

    private PharmacySubscription pharmacySubscription;

    private PharmacySubscription insertedPharmacySubscription;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PharmacySubscription createEntity() {
        return new PharmacySubscription()
            .plan(DEFAULT_PLAN)
            .maxWatchListItems(DEFAULT_MAX_WATCH_LIST_ITEMS)
            .maxUsers(DEFAULT_MAX_USERS)
            .maxEmailsPerMonth(DEFAULT_MAX_EMAILS_PER_MONTH)
            .maxSmsPerMonth(DEFAULT_MAX_SMS_PER_MONTH)
            .price(DEFAULT_PRICE)
            .billingCycle(DEFAULT_BILLING_CYCLE)
            .trialPeriodDays(DEFAULT_TRIAL_PERIOD_DAYS)
            .active(DEFAULT_ACTIVE)
            .notificationTypes(DEFAULT_NOTIFICATION_TYPES);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PharmacySubscription createUpdatedEntity() {
        return new PharmacySubscription()
            .plan(UPDATED_PLAN)
            .maxWatchListItems(UPDATED_MAX_WATCH_LIST_ITEMS)
            .maxUsers(UPDATED_MAX_USERS)
            .maxEmailsPerMonth(UPDATED_MAX_EMAILS_PER_MONTH)
            .maxSmsPerMonth(UPDATED_MAX_SMS_PER_MONTH)
            .price(UPDATED_PRICE)
            .billingCycle(UPDATED_BILLING_CYCLE)
            .trialPeriodDays(UPDATED_TRIAL_PERIOD_DAYS)
            .active(UPDATED_ACTIVE)
            .notificationTypes(UPDATED_NOTIFICATION_TYPES);
    }

    @BeforeEach
    public void initTest() {
        pharmacySubscription = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPharmacySubscription != null) {
            pharmacySubscriptionRepository.delete(insertedPharmacySubscription);
            insertedPharmacySubscription = null;
        }
    }

    @Test
    @Transactional
    void createPharmacySubscription() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PharmacySubscription
        PharmacySubscriptionDTO pharmacySubscriptionDTO = pharmacySubscriptionMapper.toDto(pharmacySubscription);
        var returnedPharmacySubscriptionDTO = om.readValue(
            restPharmacySubscriptionMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pharmacySubscriptionDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PharmacySubscriptionDTO.class
        );

        // Validate the PharmacySubscription in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPharmacySubscription = pharmacySubscriptionMapper.toEntity(returnedPharmacySubscriptionDTO);
        assertPharmacySubscriptionUpdatableFieldsEquals(
            returnedPharmacySubscription,
            getPersistedPharmacySubscription(returnedPharmacySubscription)
        );

        insertedPharmacySubscription = returnedPharmacySubscription;
    }

    @Test
    @Transactional
    void createPharmacySubscriptionWithExistingId() throws Exception {
        // Create the PharmacySubscription with an existing ID
        pharmacySubscription.setId(1L);
        PharmacySubscriptionDTO pharmacySubscriptionDTO = pharmacySubscriptionMapper.toDto(pharmacySubscription);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPharmacySubscriptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pharmacySubscriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PharmacySubscription in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPharmacySubscriptions() throws Exception {
        // Initialize the database
        insertedPharmacySubscription = pharmacySubscriptionRepository.saveAndFlush(pharmacySubscription);

        // Get all the pharmacySubscriptionList
        restPharmacySubscriptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pharmacySubscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].plan").value(hasItem(DEFAULT_PLAN)))
            .andExpect(jsonPath("$.[*].maxWatchListItems").value(hasItem(DEFAULT_MAX_WATCH_LIST_ITEMS)))
            .andExpect(jsonPath("$.[*].maxUsers").value(hasItem(DEFAULT_MAX_USERS)))
            .andExpect(jsonPath("$.[*].maxEmailsPerMonth").value(hasItem(DEFAULT_MAX_EMAILS_PER_MONTH)))
            .andExpect(jsonPath("$.[*].maxSmsPerMonth").value(hasItem(DEFAULT_MAX_SMS_PER_MONTH)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].billingCycle").value(hasItem(DEFAULT_BILLING_CYCLE.toString())))
            .andExpect(jsonPath("$.[*].trialPeriodDays").value(hasItem(DEFAULT_TRIAL_PERIOD_DAYS)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].notificationTypes").value(hasItem(DEFAULT_NOTIFICATION_TYPES.toString())));
    }

    @Test
    @Transactional
    void getPharmacySubscription() throws Exception {
        // Initialize the database
        insertedPharmacySubscription = pharmacySubscriptionRepository.saveAndFlush(pharmacySubscription);

        // Get the pharmacySubscription
        restPharmacySubscriptionMockMvc
            .perform(get(ENTITY_API_URL_ID, pharmacySubscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pharmacySubscription.getId().intValue()))
            .andExpect(jsonPath("$.plan").value(DEFAULT_PLAN))
            .andExpect(jsonPath("$.maxWatchListItems").value(DEFAULT_MAX_WATCH_LIST_ITEMS))
            .andExpect(jsonPath("$.maxUsers").value(DEFAULT_MAX_USERS))
            .andExpect(jsonPath("$.maxEmailsPerMonth").value(DEFAULT_MAX_EMAILS_PER_MONTH))
            .andExpect(jsonPath("$.maxSmsPerMonth").value(DEFAULT_MAX_SMS_PER_MONTH))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.billingCycle").value(DEFAULT_BILLING_CYCLE.toString()))
            .andExpect(jsonPath("$.trialPeriodDays").value(DEFAULT_TRIAL_PERIOD_DAYS))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.notificationTypes").value(DEFAULT_NOTIFICATION_TYPES.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPharmacySubscription() throws Exception {
        // Get the pharmacySubscription
        restPharmacySubscriptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPharmacySubscription() throws Exception {
        // Initialize the database
        insertedPharmacySubscription = pharmacySubscriptionRepository.saveAndFlush(pharmacySubscription);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pharmacySubscription
        PharmacySubscription updatedPharmacySubscription = pharmacySubscriptionRepository
            .findById(pharmacySubscription.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedPharmacySubscription are not directly saved in db
        em.detach(updatedPharmacySubscription);
        updatedPharmacySubscription
            .plan(UPDATED_PLAN)
            .maxWatchListItems(UPDATED_MAX_WATCH_LIST_ITEMS)
            .maxUsers(UPDATED_MAX_USERS)
            .maxEmailsPerMonth(UPDATED_MAX_EMAILS_PER_MONTH)
            .maxSmsPerMonth(UPDATED_MAX_SMS_PER_MONTH)
            .price(UPDATED_PRICE)
            .billingCycle(UPDATED_BILLING_CYCLE)
            .trialPeriodDays(UPDATED_TRIAL_PERIOD_DAYS)
            .active(UPDATED_ACTIVE)
            .notificationTypes(UPDATED_NOTIFICATION_TYPES);
        PharmacySubscriptionDTO pharmacySubscriptionDTO = pharmacySubscriptionMapper.toDto(updatedPharmacySubscription);

        restPharmacySubscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pharmacySubscriptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pharmacySubscriptionDTO))
            )
            .andExpect(status().isOk());

        // Validate the PharmacySubscription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPharmacySubscriptionToMatchAllProperties(updatedPharmacySubscription);
    }

    @Test
    @Transactional
    void putNonExistingPharmacySubscription() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pharmacySubscription.setId(longCount.incrementAndGet());

        // Create the PharmacySubscription
        PharmacySubscriptionDTO pharmacySubscriptionDTO = pharmacySubscriptionMapper.toDto(pharmacySubscription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPharmacySubscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pharmacySubscriptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pharmacySubscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PharmacySubscription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPharmacySubscription() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pharmacySubscription.setId(longCount.incrementAndGet());

        // Create the PharmacySubscription
        PharmacySubscriptionDTO pharmacySubscriptionDTO = pharmacySubscriptionMapper.toDto(pharmacySubscription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPharmacySubscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pharmacySubscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PharmacySubscription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPharmacySubscription() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pharmacySubscription.setId(longCount.incrementAndGet());

        // Create the PharmacySubscription
        PharmacySubscriptionDTO pharmacySubscriptionDTO = pharmacySubscriptionMapper.toDto(pharmacySubscription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPharmacySubscriptionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pharmacySubscriptionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PharmacySubscription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePharmacySubscriptionWithPatch() throws Exception {
        // Initialize the database
        insertedPharmacySubscription = pharmacySubscriptionRepository.saveAndFlush(pharmacySubscription);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pharmacySubscription using partial update
        PharmacySubscription partialUpdatedPharmacySubscription = new PharmacySubscription();
        partialUpdatedPharmacySubscription.setId(pharmacySubscription.getId());

        partialUpdatedPharmacySubscription
            .maxWatchListItems(UPDATED_MAX_WATCH_LIST_ITEMS)
            .maxUsers(UPDATED_MAX_USERS)
            .price(UPDATED_PRICE)
            .billingCycle(UPDATED_BILLING_CYCLE)
            .trialPeriodDays(UPDATED_TRIAL_PERIOD_DAYS)
            .active(UPDATED_ACTIVE)
            .notificationTypes(UPDATED_NOTIFICATION_TYPES);

        restPharmacySubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPharmacySubscription.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPharmacySubscription))
            )
            .andExpect(status().isOk());

        // Validate the PharmacySubscription in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPharmacySubscriptionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPharmacySubscription, pharmacySubscription),
            getPersistedPharmacySubscription(pharmacySubscription)
        );
    }

    @Test
    @Transactional
    void fullUpdatePharmacySubscriptionWithPatch() throws Exception {
        // Initialize the database
        insertedPharmacySubscription = pharmacySubscriptionRepository.saveAndFlush(pharmacySubscription);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pharmacySubscription using partial update
        PharmacySubscription partialUpdatedPharmacySubscription = new PharmacySubscription();
        partialUpdatedPharmacySubscription.setId(pharmacySubscription.getId());

        partialUpdatedPharmacySubscription
            .plan(UPDATED_PLAN)
            .maxWatchListItems(UPDATED_MAX_WATCH_LIST_ITEMS)
            .maxUsers(UPDATED_MAX_USERS)
            .maxEmailsPerMonth(UPDATED_MAX_EMAILS_PER_MONTH)
            .maxSmsPerMonth(UPDATED_MAX_SMS_PER_MONTH)
            .price(UPDATED_PRICE)
            .billingCycle(UPDATED_BILLING_CYCLE)
            .trialPeriodDays(UPDATED_TRIAL_PERIOD_DAYS)
            .active(UPDATED_ACTIVE)
            .notificationTypes(UPDATED_NOTIFICATION_TYPES);

        restPharmacySubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPharmacySubscription.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPharmacySubscription))
            )
            .andExpect(status().isOk());

        // Validate the PharmacySubscription in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPharmacySubscriptionUpdatableFieldsEquals(
            partialUpdatedPharmacySubscription,
            getPersistedPharmacySubscription(partialUpdatedPharmacySubscription)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPharmacySubscription() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pharmacySubscription.setId(longCount.incrementAndGet());

        // Create the PharmacySubscription
        PharmacySubscriptionDTO pharmacySubscriptionDTO = pharmacySubscriptionMapper.toDto(pharmacySubscription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPharmacySubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pharmacySubscriptionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pharmacySubscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PharmacySubscription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPharmacySubscription() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pharmacySubscription.setId(longCount.incrementAndGet());

        // Create the PharmacySubscription
        PharmacySubscriptionDTO pharmacySubscriptionDTO = pharmacySubscriptionMapper.toDto(pharmacySubscription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPharmacySubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pharmacySubscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PharmacySubscription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPharmacySubscription() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pharmacySubscription.setId(longCount.incrementAndGet());

        // Create the PharmacySubscription
        PharmacySubscriptionDTO pharmacySubscriptionDTO = pharmacySubscriptionMapper.toDto(pharmacySubscription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPharmacySubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pharmacySubscriptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PharmacySubscription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePharmacySubscription() throws Exception {
        // Initialize the database
        insertedPharmacySubscription = pharmacySubscriptionRepository.saveAndFlush(pharmacySubscription);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pharmacySubscription
        restPharmacySubscriptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, pharmacySubscription.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pharmacySubscriptionRepository.count();
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

    protected PharmacySubscription getPersistedPharmacySubscription(PharmacySubscription pharmacySubscription) {
        return pharmacySubscriptionRepository.findById(pharmacySubscription.getId()).orElseThrow();
    }

    protected void assertPersistedPharmacySubscriptionToMatchAllProperties(PharmacySubscription expectedPharmacySubscription) {
        assertPharmacySubscriptionAllPropertiesEquals(
            expectedPharmacySubscription,
            getPersistedPharmacySubscription(expectedPharmacySubscription)
        );
    }

    protected void assertPersistedPharmacySubscriptionToMatchUpdatableProperties(PharmacySubscription expectedPharmacySubscription) {
        assertPharmacySubscriptionAllUpdatablePropertiesEquals(
            expectedPharmacySubscription,
            getPersistedPharmacySubscription(expectedPharmacySubscription)
        );
    }
}
