package com.pharmaresolve.medcom.service.mapper;

import static com.pharmaresolve.medcom.domain.WatchListItemAsserts.*;
import static com.pharmaresolve.medcom.domain.WatchListItemTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WatchListItemMapperTest {

    private WatchListItemMapper watchListItemMapper;

    @BeforeEach
    void setUp() {
        watchListItemMapper = new WatchListItemMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getWatchListItemSample1();
        var actual = watchListItemMapper.toEntity(watchListItemMapper.toDto(expected));
        assertWatchListItemAllPropertiesEquals(expected, actual);
    }
}
