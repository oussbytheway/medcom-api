package com.pharmaresolve.medcom.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class WatchlistItemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static WatchlistItem getWatchlistItemSample1() {
        return new WatchlistItem().id(1L).priority(1).addedBy("addedBy1");
    }

    public static WatchlistItem getWatchlistItemSample2() {
        return new WatchlistItem().id(2L).priority(2).addedBy("addedBy2");
    }

    public static WatchlistItem getWatchlistItemRandomSampleGenerator() {
        return new WatchlistItem()
            .id(longCount.incrementAndGet())
            .priority(intCount.incrementAndGet())
            .addedBy(UUID.randomUUID().toString());
    }
}
