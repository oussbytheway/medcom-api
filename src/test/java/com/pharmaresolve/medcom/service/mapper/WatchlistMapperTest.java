package com.pharmaresolve.medcom.service.mapper;

import static com.pharmaresolve.medcom.domain.WatchlistAsserts.*;
import static com.pharmaresolve.medcom.domain.WatchlistTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WatchlistMapperTest {

    private WatchlistMapper watchlistMapper;

    @BeforeEach
    void setUp() {
        watchlistMapper = new WatchlistMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getWatchlistSample1();
        var actual = watchlistMapper.toEntity(watchlistMapper.toDto(expected));
        assertWatchlistAllPropertiesEquals(expected, actual);
    }
}
