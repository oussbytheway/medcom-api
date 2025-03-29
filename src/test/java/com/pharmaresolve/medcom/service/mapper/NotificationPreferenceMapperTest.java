package com.pharmaresolve.medcom.service.mapper;

import static com.pharmaresolve.medcom.domain.NotificationPreferenceAsserts.*;
import static com.pharmaresolve.medcom.domain.NotificationPreferenceTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotificationPreferenceMapperTest {

    private NotificationPreferenceMapper notificationPreferenceMapper;

    @BeforeEach
    void setUp() {
        notificationPreferenceMapper = new NotificationPreferenceMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNotificationPreferenceSample1();
        var actual = notificationPreferenceMapper.toEntity(notificationPreferenceMapper.toDto(expected));
        assertNotificationPreferenceAllPropertiesEquals(expected, actual);
    }
}
