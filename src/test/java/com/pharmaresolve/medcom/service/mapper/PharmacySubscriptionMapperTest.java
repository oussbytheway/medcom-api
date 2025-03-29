package com.pharmaresolve.medcom.service.mapper;

import static com.pharmaresolve.medcom.domain.PharmacySubscriptionAsserts.*;
import static com.pharmaresolve.medcom.domain.PharmacySubscriptionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PharmacySubscriptionMapperTest {

    private PharmacySubscriptionMapper pharmacySubscriptionMapper;

    @BeforeEach
    void setUp() {
        pharmacySubscriptionMapper = new PharmacySubscriptionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPharmacySubscriptionSample1();
        var actual = pharmacySubscriptionMapper.toEntity(pharmacySubscriptionMapper.toDto(expected));
        assertPharmacySubscriptionAllPropertiesEquals(expected, actual);
    }
}
