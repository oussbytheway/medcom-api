package com.pharmaresolve.medcom.domain;

import static com.pharmaresolve.medcom.domain.AlertTestSamples.*;
import static com.pharmaresolve.medcom.domain.NotificationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.pharmaresolve.medcom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotificationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notification.class);
        Notification notification1 = getNotificationSample1();
        Notification notification2 = new Notification();
        assertThat(notification1).isNotEqualTo(notification2);

        notification2.setId(notification1.getId());
        assertThat(notification1).isEqualTo(notification2);

        notification2 = getNotificationSample2();
        assertThat(notification1).isNotEqualTo(notification2);
    }

    @Test
    void alertTest() {
        Notification notification = getNotificationRandomSampleGenerator();
        Alert alertBack = getAlertRandomSampleGenerator();

        notification.setAlert(alertBack);
        assertThat(notification.getAlert()).isEqualTo(alertBack);

        notification.alert(null);
        assertThat(notification.getAlert()).isNull();
    }
}
