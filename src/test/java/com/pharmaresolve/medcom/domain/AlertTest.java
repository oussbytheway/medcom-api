package com.pharmaresolve.medcom.domain;

import static com.pharmaresolve.medcom.domain.AlertTestSamples.*;
import static com.pharmaresolve.medcom.domain.NotificationTestSamples.*;
import static com.pharmaresolve.medcom.domain.WatchListItemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.pharmaresolve.medcom.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
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
    void watchListItemTest() {
        Alert alert = getAlertRandomSampleGenerator();
        WatchListItem watchListItemBack = getWatchListItemRandomSampleGenerator();

        alert.setWatchListItem(watchListItemBack);
        assertThat(alert.getWatchListItem()).isEqualTo(watchListItemBack);

        alert.watchListItem(null);
        assertThat(alert.getWatchListItem()).isNull();
    }

    @Test
    void notificationTest() {
        Alert alert = getAlertRandomSampleGenerator();
        Notification notificationBack = getNotificationRandomSampleGenerator();

        alert.addNotification(notificationBack);
        assertThat(alert.getNotifications()).containsOnly(notificationBack);
        assertThat(notificationBack.getAlert()).isEqualTo(alert);

        alert.removeNotification(notificationBack);
        assertThat(alert.getNotifications()).doesNotContain(notificationBack);
        assertThat(notificationBack.getAlert()).isNull();

        alert.notifications(new HashSet<>(Set.of(notificationBack)));
        assertThat(alert.getNotifications()).containsOnly(notificationBack);
        assertThat(notificationBack.getAlert()).isEqualTo(alert);

        alert.setNotifications(new HashSet<>());
        assertThat(alert.getNotifications()).doesNotContain(notificationBack);
        assertThat(notificationBack.getAlert()).isNull();
    }
}
