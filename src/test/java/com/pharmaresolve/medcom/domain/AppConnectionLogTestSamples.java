package com.pharmaresolve.medcom.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AppConnectionLogTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AppConnectionLog getAppConnectionLogSample1() {
        return new AppConnectionLog().id(1L).ipAddress("ipAddress1").userAgent("userAgent1").latitude("latitude1").longitude("longitude1");
    }

    public static AppConnectionLog getAppConnectionLogSample2() {
        return new AppConnectionLog().id(2L).ipAddress("ipAddress2").userAgent("userAgent2").latitude("latitude2").longitude("longitude2");
    }

    public static AppConnectionLog getAppConnectionLogRandomSampleGenerator() {
        return new AppConnectionLog()
            .id(longCount.incrementAndGet())
            .ipAddress(UUID.randomUUID().toString())
            .userAgent(UUID.randomUUID().toString())
            .latitude(UUID.randomUUID().toString())
            .longitude(UUID.randomUUID().toString());
    }
}
