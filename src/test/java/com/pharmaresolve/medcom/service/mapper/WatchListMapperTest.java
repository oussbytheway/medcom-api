package com.pharmaresolve.medcom.service.mapper;

import static com.pharmaresolve.medcom.domain.WatchListAsserts.*;
import static com.pharmaresolve.medcom.domain.WatchListTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WatchListMapperTest {

    private WatchListMapper watchListMapper;

    @BeforeEach
    void setUp() {
        watchListMapper = new WatchListMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getWatchListSample1();
        var actual = watchListMapper.toEntity(watchListMapper.toDto(expected));
        assertWatchListAllPropertiesEquals(expected, actual);
    }
}
