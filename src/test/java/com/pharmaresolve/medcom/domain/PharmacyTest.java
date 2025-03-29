package com.pharmaresolve.medcom.domain;

import static com.pharmaresolve.medcom.domain.PharmacySubscriptionTestSamples.*;
import static com.pharmaresolve.medcom.domain.PharmacyTestSamples.*;
import static com.pharmaresolve.medcom.domain.WatchListTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.pharmaresolve.medcom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PharmacyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pharmacy.class);
        Pharmacy pharmacy1 = getPharmacySample1();
        Pharmacy pharmacy2 = new Pharmacy();
        assertThat(pharmacy1).isNotEqualTo(pharmacy2);

        pharmacy2.setId(pharmacy1.getId());
        assertThat(pharmacy1).isEqualTo(pharmacy2);

        pharmacy2 = getPharmacySample2();
        assertThat(pharmacy1).isNotEqualTo(pharmacy2);
    }

    @Test
    void subscriptionTest() {
        Pharmacy pharmacy = getPharmacyRandomSampleGenerator();
        PharmacySubscription pharmacySubscriptionBack = getPharmacySubscriptionRandomSampleGenerator();

        pharmacy.setSubscription(pharmacySubscriptionBack);
        assertThat(pharmacy.getSubscription()).isEqualTo(pharmacySubscriptionBack);

        pharmacy.subscription(null);
        assertThat(pharmacy.getSubscription()).isNull();
    }

    @Test
    void watchListTest() {
        Pharmacy pharmacy = getPharmacyRandomSampleGenerator();
        WatchList watchListBack = getWatchListRandomSampleGenerator();

        pharmacy.setWatchList(watchListBack);
        assertThat(pharmacy.getWatchList()).isEqualTo(watchListBack);
        assertThat(watchListBack.getPharmacy()).isEqualTo(pharmacy);

        pharmacy.watchList(null);
        assertThat(pharmacy.getWatchList()).isNull();
        assertThat(watchListBack.getPharmacy()).isNull();
    }
}
