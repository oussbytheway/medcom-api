package com.pharmaresolve.medcom.service.mapper;

import static com.pharmaresolve.medcom.domain.WatchListLogAsserts.*;
import static com.pharmaresolve.medcom.domain.WatchListLogTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WatchListLogMapperTest {

    private WatchListLogMapper watchListLogMapper;

    @BeforeEach
    void setUp() {
        watchListLogMapper = new WatchListLogMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getWatchListLogSample1();
        var actual = watchListLogMapper.toEntity(watchListLogMapper.toDto(expected));
        assertWatchListLogAllPropertiesEquals(expected, actual);
    }
}
