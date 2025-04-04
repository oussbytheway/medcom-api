package com.pharmaresolve.medcom.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PharmacyTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Pharmacy getPharmacySample1() {
        return new Pharmacy()
            .id(1L)
            .name("name1")
            .address("address1")
            .email("email1")
            .phone("phone1")
            .timeZone("timeZone1")
            .website("website1")
            .activatedBy("activatedBy1");
    }

    public static Pharmacy getPharmacySample2() {
        return new Pharmacy()
            .id(2L)
            .name("name2")
            .address("address2")
            .email("email2")
            .phone("phone2")
            .timeZone("timeZone2")
            .website("website2")
            .activatedBy("activatedBy2");
    }

    public static Pharmacy getPharmacyRandomSampleGenerator() {
        return new Pharmacy()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .timeZone(UUID.randomUUID().toString())
            .website(UUID.randomUUID().toString())
            .activatedBy(UUID.randomUUID().toString());
    }
}
