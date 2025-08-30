package com.pharmaresolve.medcom.domain;

import static com.pharmaresolve.medcom.domain.AlertTestSamples.*;
import static com.pharmaresolve.medcom.domain.WatchlistItemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.pharmaresolve.medcom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlertTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alert.class);
        Alert alert1 = getAlertSample1();
        Alert alert2 = new Alert();
        assertThat(alert1).isNotEqualTo(alert2);

        alert2.setId(alert1.getId());
        assertThat(alert1).isEqualTo(alert2);

        alert2 = getAlertSample2();
        assertThat(alert1).isNotEqualTo(alert2);
    }

    @Test
    void watchlistItemTest() {
        Alert alert = getAlertRandomSampleGenerator();
        WatchlistItem watchlistItemBack = getWatchlistItemRandomSampleGenerator();

        alert.setWatchlistItem(watchlistItemBack);
        assertThat(alert.getWatchlistItem()).isEqualTo(watchlistItemBack);

        alert.watchlistItem(null);
        assertThat(alert.getWatchlistItem()).isNull();
    }
}
