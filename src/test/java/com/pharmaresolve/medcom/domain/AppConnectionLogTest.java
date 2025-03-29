package com.pharmaresolve.medcom.domain;

import static com.pharmaresolve.medcom.domain.AppConnectionLogTestSamples.*;
import static com.pharmaresolve.medcom.domain.AppUserTestSamples.*;
import static com.pharmaresolve.medcom.domain.PharmacyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.pharmaresolve.medcom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppConnectionLogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppConnectionLog.class);
        AppConnectionLog appConnectionLog1 = getAppConnectionLogSample1();
        AppConnectionLog appConnectionLog2 = new AppConnectionLog();
        assertThat(appConnectionLog1).isNotEqualTo(appConnectionLog2);

        appConnectionLog2.setId(appConnectionLog1.getId());
        assertThat(appConnectionLog1).isEqualTo(appConnectionLog2);

        appConnectionLog2 = getAppConnectionLogSample2();
        assertThat(appConnectionLog1).isNotEqualTo(appConnectionLog2);
    }

    @Test
    void appUserTest() {
        AppConnectionLog appConnectionLog = getAppConnectionLogRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        appConnectionLog.setAppUser(appUserBack);
        assertThat(appConnectionLog.getAppUser()).isEqualTo(appUserBack);

        appConnectionLog.appUser(null);
        assertThat(appConnectionLog.getAppUser()).isNull();
    }

    @Test
    void pharmacyTest() {
        AppConnectionLog appConnectionLog = getAppConnectionLogRandomSampleGenerator();
        Pharmacy pharmacyBack = getPharmacyRandomSampleGenerator();

        appConnectionLog.setPharmacy(pharmacyBack);
        assertThat(appConnectionLog.getPharmacy()).isEqualTo(pharmacyBack);

        appConnectionLog.pharmacy(null);
        assertThat(appConnectionLog.getPharmacy()).isNull();
    }
}
