package com.pharmaresolve.medcom.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class NotificationPreferenceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static NotificationPreference getNotificationPreferenceSample1() {
        return new NotificationPreference().id(1L);
    }

    public static NotificationPreference getNotificationPreferenceSample2() {
        return new NotificationPreference().id(2L);
    }

    public static NotificationPreference getNotificationPreferenceRandomSampleGenerator() {
        return new NotificationPreference().id(longCount.incrementAndGet());
    }
}
