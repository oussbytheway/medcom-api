package com.pharmaresolve.medcom.domain;

import static com.pharmaresolve.medcom.domain.AppUserTestSamples.*;
import static com.pharmaresolve.medcom.domain.NotificationPreferenceTestSamples.*;
import static com.pharmaresolve.medcom.domain.PharmacyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.pharmaresolve.medcom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUser.class);
        AppUser appUser1 = getAppUserSample1();
        AppUser appUser2 = new AppUser();
        assertThat(appUser1).isNotEqualTo(appUser2);

        appUser2.setId(appUser1.getId());
        assertThat(appUser1).isEqualTo(appUser2);

        appUser2 = getAppUserSample2();
        assertThat(appUser1).isNotEqualTo(appUser2);
    }

    @Test
    void pharmacyTest() {
        AppUser appUser = getAppUserRandomSampleGenerator();
        Pharmacy pharmacyBack = getPharmacyRandomSampleGenerator();

        appUser.setPharmacy(pharmacyBack);
        assertThat(appUser.getPharmacy()).isEqualTo(pharmacyBack);

        appUser.pharmacy(null);
        assertThat(appUser.getPharmacy()).isNull();
    }

    @Test
    void notificationPreferenceTest() {
        AppUser appUser = getAppUserRandomSampleGenerator();
        NotificationPreference notificationPreferenceBack = getNotificationPreferenceRandomSampleGenerator();

        appUser.setNotificationPreference(notificationPreferenceBack);
        assertThat(appUser.getNotificationPreference()).isEqualTo(notificationPreferenceBack);

        appUser.notificationPreference(null);
        assertThat(appUser.getNotificationPreference()).isNull();
    }
}
