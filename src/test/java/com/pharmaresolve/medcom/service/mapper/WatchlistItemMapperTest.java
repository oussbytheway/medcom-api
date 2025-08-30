package com.pharmaresolve.medcom.service.mapper;

import static com.pharmaresolve.medcom.domain.WatchlistItemAsserts.*;
import static com.pharmaresolve.medcom.domain.WatchlistItemTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WatchlistItemMapperTest {

    private WatchlistItemMapper watchlistItemMapper;

    @BeforeEach
    void setUp() {
        watchlistItemMapper = new WatchlistItemMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getWatchlistItemSample1();
        var actual = watchlistItemMapper.toEntity(watchlistItemMapper.toDto(expected));
        assertWatchlistItemAllPropertiesEquals(expected, actual);
    }
}
