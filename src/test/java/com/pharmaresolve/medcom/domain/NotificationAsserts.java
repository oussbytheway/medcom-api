package com.pharmaresolve.medcom.domain;

import static com.pharmaresolve.medcom.domain.AssertUtils.zonedDataTimeSameInstant;
import static org.assertj.core.api.Assertions.assertThat;

public class NotificationAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNotificationAllPropertiesEquals(Notification expected, Notification actual) {
        assertNotificationAutoGeneratedPropertiesEquals(expected, actual);
        assertNotificationAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNotificationAllUpdatablePropertiesEquals(Notification expected, Notification actual) {
        assertNotificationUpdatableFieldsEquals(expected, actual);
        assertNotificationUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNotificationAutoGeneratedPropertiesEquals(Notification expected, Notification actual) {
        assertThat(expected)
            .as("Verify Notification auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNotificationUpdatableFieldsEquals(Notification expected, Notification actual) {
        assertThat(expected)
            .as("Verify Notification relevant properties")
            .satisfies(e -> assertThat(e.getType()).as("check type").isEqualTo(actual.getType()))
            .satisfies(e -> assertThat(e.getMessage()).as("check message").isEqualTo(actual.getMessage()))
            .satisfies(e -> assertThat(e.getDelivered()).as("check delivered").isEqualTo(actual.getDelivered()))
            .satisfies(e ->
                assertThat(e.getSentAt()).as("check sentAt").usingComparator(zonedDataTimeSameInstant).isEqualTo(actual.getSentAt())
            )
            .satisfies(e -> assertThat(e.getDeliveryAttempts()).as("check deliveryAttempts").isEqualTo(actual.getDeliveryAttempts()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNotificationUpdatableRelationshipsEquals(Notification expected, Notification actual) {
        assertThat(expected)
            .as("Verify Notification relationships")
            .satisfies(e -> assertThat(e.getAlert()).as("check alert").isEqualTo(actual.getAlert()))
            .satisfies(e -> assertThat(e.getAppUser()).as("check appUser").isEqualTo(actual.getAppUser()));
    }
}
