package com.pharmaresolve.medcom.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PharmacySubscriptionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PharmacySubscription getPharmacySubscriptionSample1() {
        return new PharmacySubscription()
            .id(1L)
            .plan("plan1")
            .maxWatchListItems(1)
            .maxUsers(1)
            .maxEmailsPerMonth(1)
            .maxSmsPerMonth(1)
            .trialPeriodDays(1);
    }

    public static PharmacySubscription getPharmacySubscriptionSample2() {
        return new PharmacySubscription()
            .id(2L)
            .plan("plan2")
            .maxWatchListItems(2)
            .maxUsers(2)
            .maxEmailsPerMonth(2)
            .maxSmsPerMonth(2)
            .trialPeriodDays(2);
    }

    public static PharmacySubscription getPharmacySubscriptionRandomSampleGenerator() {
        return new PharmacySubscription()
            .id(longCount.incrementAndGet())
            .plan(UUID.randomUUID().toString())
            .maxWatchListItems(intCount.incrementAndGet())
            .maxUsers(intCount.incrementAndGet())
            .maxEmailsPerMonth(intCount.incrementAndGet())
            .maxSmsPerMonth(intCount.incrementAndGet())
            .trialPeriodDays(intCount.incrementAndGet());
    }
}
