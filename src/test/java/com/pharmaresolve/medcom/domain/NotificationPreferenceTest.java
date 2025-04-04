package com.pharmaresolve.medcom.domain;

import static com.pharmaresolve.medcom.domain.AppUserTestSamples.*;
import static com.pharmaresolve.medcom.domain.NotificationPreferenceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.pharmaresolve.medcom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotificationPreferenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationPreference.class);
        NotificationPreference notificationPreference1 = getNotificationPreferenceSample1();
        NotificationPreference notificationPreference2 = new NotificationPreference();
        assertThat(notificationPreference1).isNotEqualTo(notificationPreference2);

        notificationPreference2.setId(notificationPreference1.getId());
        assertThat(notificationPreference1).isEqualTo(notificationPreference2);

        notificationPreference2 = getNotificationPreferenceSample2();
        assertThat(notificationPreference1).isNotEqualTo(notificationPreference2);
    }

    @Test
    void appUserTest() {
        NotificationPreference notificationPreference = getNotificationPreferenceRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        notificationPreference.setAppUser(appUserBack);
        assertThat(notificationPreference.getAppUser()).isEqualTo(appUserBack);
        assertThat(appUserBack.getNotificationPreference()).isEqualTo(notificationPreference);

        notificationPreference.appUser(null);
        assertThat(notificationPreference.getAppUser()).isNull();
        assertThat(appUserBack.getNotificationPreference()).isNull();
    }
}
