package com.pharmaresolve.medcom.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class WatchlistTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Watchlist getWatchlistSample1() {
        return new Watchlist().id(1L).name("name1").limit(1);
    }

    public static Watchlist getWatchlistSample2() {
        return new Watchlist().id(2L).name("name2").limit(2);
    }

    public static Watchlist getWatchlistRandomSampleGenerator() {
        return new Watchlist().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).limit(intCount.incrementAndGet());
    }
}
