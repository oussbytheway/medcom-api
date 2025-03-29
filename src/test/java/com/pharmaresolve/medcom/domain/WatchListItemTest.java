package com.pharmaresolve.medcom.domain;

import static com.pharmaresolve.medcom.domain.AlertTestSamples.*;
import static com.pharmaresolve.medcom.domain.ProductTestSamples.*;
import static com.pharmaresolve.medcom.domain.WatchListItemTestSamples.*;
import static com.pharmaresolve.medcom.domain.WatchListTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.pharmaresolve.medcom.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class WatchListItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WatchListItem.class);
        WatchListItem watchListItem1 = getWatchListItemSample1();
        WatchListItem watchListItem2 = new WatchListItem();
        assertThat(watchListItem1).isNotEqualTo(watchListItem2);

        watchListItem2.setId(watchListItem1.getId());
        assertThat(watchListItem1).isEqualTo(watchListItem2);

        watchListItem2 = getWatchListItemSample2();
        assertThat(watchListItem1).isNotEqualTo(watchListItem2);
    }

    @Test
    void watchListTest() {
        WatchListItem watchListItem = getWatchListItemRandomSampleGenerator();
        WatchList watchListBack = getWatchListRandomSampleGenerator();

        watchListItem.setWatchList(watchListBack);
        assertThat(watchListItem.getWatchList()).isEqualTo(watchListBack);

        watchListItem.watchList(null);
        assertThat(watchListItem.getWatchList()).isNull();
    }

    @Test
    void productTest() {
        WatchListItem watchListItem = getWatchListItemRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        watchListItem.setProduct(productBack);
        assertThat(watchListItem.getProduct()).isEqualTo(productBack);

        watchListItem.product(null);
        assertThat(watchListItem.getProduct()).isNull();
    }

    @Test
    void alertTest() {
        WatchListItem watchListItem = getWatchListItemRandomSampleGenerator();
        Alert alertBack = getAlertRandomSampleGenerator();

        watchListItem.addAlert(alertBack);
        assertThat(watchListItem.getAlerts()).containsOnly(alertBack);
        assertThat(alertBack.getWatchListItem()).isEqualTo(watchListItem);

        watchListItem.removeAlert(alertBack);
        assertThat(watchListItem.getAlerts()).doesNotContain(alertBack);
        assertThat(alertBack.getWatchListItem()).isNull();

        watchListItem.alerts(new HashSet<>(Set.of(alertBack)));
        assertThat(watchListItem.getAlerts()).containsOnly(alertBack);
        assertThat(alertBack.getWatchListItem()).isEqualTo(watchListItem);

        watchListItem.setAlerts(new HashSet<>());
        assertThat(watchListItem.getAlerts()).doesNotContain(alertBack);
        assertThat(alertBack.getWatchListItem()).isNull();
    }
}
