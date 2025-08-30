package com.pharmaresolve.medcom.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Product getProductSample1() {
        return new Product()
            .id(1L)
            .name("name1")
            .uniqueId("uniqueId1")
            .atcCode("atcCode1")
            .officialUrl("officialUrl1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static Product getProductSample2() {
        return new Product()
            .id(2L)
            .name("name2")
            .uniqueId("uniqueId2")
            .atcCode("atcCode2")
            .officialUrl("officialUrl2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static Product getProductRandomSampleGenerator() {
        return new Product()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .uniqueId(UUID.randomUUID().toString())
            .atcCode(UUID.randomUUID().toString())
            .officialUrl(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
