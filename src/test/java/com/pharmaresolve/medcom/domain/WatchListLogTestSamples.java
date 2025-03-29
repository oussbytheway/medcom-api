package com.pharmaresolve.medcom.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WatchListLogTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static WatchListLog getWatchListLogSample1() {
        return new WatchListLog().id(1L).userAgent("userAgent1");
    }

    public static WatchListLog getWatchListLogSample2() {
        return new WatchListLog().id(2L).userAgent("userAgent2");
    }

    public static WatchListLog getWatchListLogRandomSampleGenerator() {
        return new WatchListLog().id(longCount.incrementAndGet()).userAgent(UUID.randomUUID().toString());
    }
}
