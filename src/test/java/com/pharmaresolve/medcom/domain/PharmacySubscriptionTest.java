package com.pharmaresolve.medcom.domain;

import static com.pharmaresolve.medcom.domain.PharmacySubscriptionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.pharmaresolve.medcom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PharmacySubscriptionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PharmacySubscription.class);
        PharmacySubscription pharmacySubscription1 = getPharmacySubscriptionSample1();
        PharmacySubscription pharmacySubscription2 = new PharmacySubscription();
        assertThat(pharmacySubscription1).isNotEqualTo(pharmacySubscription2);

        pharmacySubscription2.setId(pharmacySubscription1.getId());
        assertThat(pharmacySubscription1).isEqualTo(pharmacySubscription2);

        pharmacySubscription2 = getPharmacySubscriptionSample2();
        assertThat(pharmacySubscription1).isNotEqualTo(pharmacySubscription2);
    }
}
