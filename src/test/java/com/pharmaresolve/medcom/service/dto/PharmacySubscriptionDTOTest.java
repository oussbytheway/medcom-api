package com.pharmaresolve.medcom.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pharmaresolve.medcom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PharmacySubscriptionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PharmacySubscriptionDTO.class);
        PharmacySubscriptionDTO pharmacySubscriptionDTO1 = new PharmacySubscriptionDTO();
        pharmacySubscriptionDTO1.setId(1L);
        PharmacySubscriptionDTO pharmacySubscriptionDTO2 = new PharmacySubscriptionDTO();
        assertThat(pharmacySubscriptionDTO1).isNotEqualTo(pharmacySubscriptionDTO2);
        pharmacySubscriptionDTO2.setId(pharmacySubscriptionDTO1.getId());
        assertThat(pharmacySubscriptionDTO1).isEqualTo(pharmacySubscriptionDTO2);
        pharmacySubscriptionDTO2.setId(2L);
        assertThat(pharmacySubscriptionDTO1).isNotEqualTo(pharmacySubscriptionDTO2);
        pharmacySubscriptionDTO1.setId(null);
        assertThat(pharmacySubscriptionDTO1).isNotEqualTo(pharmacySubscriptionDTO2);
    }
}
