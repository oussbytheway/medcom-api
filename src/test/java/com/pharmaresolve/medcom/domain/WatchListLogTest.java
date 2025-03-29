package com.pharmaresolve.medcom.domain;

import static com.pharmaresolve.medcom.domain.AppUserTestSamples.*;
import static com.pharmaresolve.medcom.domain.PharmacyTestSamples.*;
import static com.pharmaresolve.medcom.domain.WatchListItemTestSamples.*;
import static com.pharmaresolve.medcom.domain.WatchListLogTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.pharmaresolve.medcom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WatchListLogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WatchListLog.class);
        WatchListLog watchListLog1 = getWatchListLogSample1();
        WatchListLog watchListLog2 = new WatchListLog();
        assertThat(watchListLog1).isNotEqualTo(watchListLog2);

        watchListLog2.setId(watchListLog1.getId());
        assertThat(watchListLog1).isEqualTo(watchListLog2);

        watchListLog2 = getWatchListLogSample2();
        assertThat(watchListLog1).isNotEqualTo(watchListLog2);
    }

    @Test
    void watchListItemTest() {
        WatchListLog watchListLog = getWatchListLogRandomSampleGenerator();
        WatchListItem watchListItemBack = getWatchListItemRandomSampleGenerator();

        watchListLog.setWatchListItem(watchListItemBack);
        assertThat(watchListLog.getWatchListItem()).isEqualTo(watchListItemBack);

        watchListLog.watchListItem(null);
        assertThat(watchListLog.getWatchListItem()).isNull();
    }

    @Test
    void appUserTest() {
        WatchListLog watchListLog = getWatchListLogRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        watchListLog.setAppUser(appUserBack);
        assertThat(watchListLog.getAppUser()).isEqualTo(appUserBack);

        watchListLog.appUser(null);
        assertThat(watchListLog.getAppUser()).isNull();
    }

    @Test
    void pharmacyTest() {
        WatchListLog watchListLog = getWatchListLogRandomSampleGenerator();
        Pharmacy pharmacyBack = getPharmacyRandomSampleGenerator();

        watchListLog.setPharmacy(pharmacyBack);
        assertThat(watchListLog.getPharmacy()).isEqualTo(pharmacyBack);

        watchListLog.pharmacy(null);
        assertThat(watchListLog.getPharmacy()).isNull();
    }
}
