package com.pharmaresolve.medcom.domain;

import static com.pharmaresolve.medcom.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class PharmacySubscriptionAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPharmacySubscriptionAllPropertiesEquals(PharmacySubscription expected, PharmacySubscription actual) {
        assertPharmacySubscriptionAutoGeneratedPropertiesEquals(expected, actual);
        assertPharmacySubscriptionAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPharmacySubscriptionAllUpdatablePropertiesEquals(PharmacySubscription expected, PharmacySubscription actual) {
        assertPharmacySubscriptionUpdatableFieldsEquals(expected, actual);
        assertPharmacySubscriptionUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPharmacySubscriptionAutoGeneratedPropertiesEquals(PharmacySubscription expected, PharmacySubscription actual) {
        assertThat(expected)
            .as("Verify PharmacySubscription auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPharmacySubscriptionUpdatableFieldsEquals(PharmacySubscription expected, PharmacySubscription actual) {
        assertThat(expected)
            .as("Verify PharmacySubscription relevant properties")
            .satisfies(e -> assertThat(e.getPlan()).as("check plan").isEqualTo(actual.getPlan()))
            .satisfies(e -> assertThat(e.getMaxWatchListItems()).as("check maxWatchListItems").isEqualTo(actual.getMaxWatchListItems()))
            .satisfies(e -> assertThat(e.getMaxUsers()).as("check maxUsers").isEqualTo(actual.getMaxUsers()))
            .satisfies(e -> assertThat(e.getMaxEmailsPerMonth()).as("check maxEmailsPerMonth").isEqualTo(actual.getMaxEmailsPerMonth()))
            .satisfies(e -> assertThat(e.getMaxSmsPerMonth()).as("check maxSmsPerMonth").isEqualTo(actual.getMaxSmsPerMonth()))
            .satisfies(e -> assertThat(e.getPrice()).as("check price").usingComparator(bigDecimalCompareTo).isEqualTo(actual.getPrice()))
            .satisfies(e -> assertThat(e.getBillingCycle()).as("check billingCycle").isEqualTo(actual.getBillingCycle()))
            .satisfies(e -> assertThat(e.getTrialPeriodDays()).as("check trialPeriodDays").isEqualTo(actual.getTrialPeriodDays()))
            .satisfies(e -> assertThat(e.getActive()).as("check active").isEqualTo(actual.getActive()))
            .satisfies(e -> assertThat(e.getNotificationTypes()).as("check notificationTypes").isEqualTo(actual.getNotificationTypes()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPharmacySubscriptionUpdatableRelationshipsEquals(PharmacySubscription expected, PharmacySubscription actual) {
        // empty method
    }
}
