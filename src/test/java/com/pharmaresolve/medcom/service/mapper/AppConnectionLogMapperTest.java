package com.pharmaresolve.medcom.service.mapper;

import static com.pharmaresolve.medcom.domain.AppConnectionLogAsserts.*;
import static com.pharmaresolve.medcom.domain.AppConnectionLogTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppConnectionLogMapperTest {

    private AppConnectionLogMapper appConnectionLogMapper;

    @BeforeEach
    void setUp() {
        appConnectionLogMapper = new AppConnectionLogMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAppConnectionLogSample1();
        var actual = appConnectionLogMapper.toEntity(appConnectionLogMapper.toDto(expected));
        assertAppConnectionLogAllPropertiesEquals(expected, actual);
    }
}
