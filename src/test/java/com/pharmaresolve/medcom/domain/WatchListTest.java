package com.pharmaresolve.medcom.domain;

import static com.pharmaresolve.medcom.domain.PharmacyTestSamples.*;
import static com.pharmaresolve.medcom.domain.WatchListItemTestSamples.*;
import static com.pharmaresolve.medcom.domain.WatchListTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.pharmaresolve.medcom.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class WatchListTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WatchList.class);
        WatchList watchList1 = getWatchListSample1();
        WatchList watchList2 = new WatchList();
        assertThat(watchList1).isNotEqualTo(watchList2);

        watchList2.setId(watchList1.getId());
        assertThat(watchList1).isEqualTo(watchList2);

        watchList2 = getWatchListSample2();
        assertThat(watchList1).isNotEqualTo(watchList2);
    }

    @Test
    void pharmacyTest() {
        WatchList watchList = getWatchListRandomSampleGenerator();
        Pharmacy pharmacyBack = getPharmacyRandomSampleGenerator();

        watchList.setPharmacy(pharmacyBack);
        assertThat(watchList.getPharmacy()).isEqualTo(pharmacyBack);

        watchList.pharmacy(null);
        assertThat(watchList.getPharmacy()).isNull();
    }

    @Test
    void watchListItemTest() {
        WatchList watchList = getWatchListRandomSampleGenerator();
        WatchListItem watchListItemBack = getWatchListItemRandomSampleGenerator();

        watchList.addWatchListItem(watchListItemBack);
        assertThat(watchList.getWatchListItems()).containsOnly(watchListItemBack);
        assertThat(watchListItemBack.getWatchList()).isEqualTo(watchList);

        watchList.removeWatchListItem(watchListItemBack);
        assertThat(watchList.getWatchListItems()).doesNotContain(watchListItemBack);
        assertThat(watchListItemBack.getWatchList()).isNull();

        watchList.watchListItems(new HashSet<>(Set.of(watchListItemBack)));
        assertThat(watchList.getWatchListItems()).containsOnly(watchListItemBack);
        assertThat(watchListItemBack.getWatchList()).isEqualTo(watchList);

        watchList.setWatchListItems(new HashSet<>());
        assertThat(watchList.getWatchListItems()).doesNotContain(watchListItemBack);
        assertThat(watchListItemBack.getWatchList()).isNull();
    }
}
