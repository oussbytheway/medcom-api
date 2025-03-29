package com.pharmaresolve.medcom.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class WatchListItemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static WatchListItem getWatchListItemSample1() {
        return new WatchListItem().id(1L).priority(1);
    }

    public static WatchListItem getWatchListItemSample2() {
        return new WatchListItem().id(2L).priority(2);
    }

    public static WatchListItem getWatchListItemRandomSampleGenerator() {
        return new WatchListItem().id(longCount.incrementAndGet()).priority(intCount.incrementAndGet());
    }
}
