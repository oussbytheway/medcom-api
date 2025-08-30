package com.pharmaresolve.medcom.service.mapper;

import static com.pharmaresolve.medcom.domain.AlertAsserts.*;
import static com.pharmaresolve.medcom.domain.AlertTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlertMapperTest {

    private AlertMapper alertMapper;

    @BeforeEach
    void setUp() {
        alertMapper = new AlertMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAlertSample1();
        var actual = alertMapper.toEntity(alertMapper.toDto(expected));
        assertAlertAllPropertiesEquals(expected, actual);
    }
}
