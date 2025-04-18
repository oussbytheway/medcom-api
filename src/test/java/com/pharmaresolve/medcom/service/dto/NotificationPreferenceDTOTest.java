package com.pharmaresolve.medcom.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pharmaresolve.medcom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotificationPreferenceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationPreferenceDTO.class);
        NotificationPreferenceDTO notificationPreferenceDTO1 = new NotificationPreferenceDTO();
        notificationPreferenceDTO1.setId(1L);
        NotificationPreferenceDTO notificationPreferenceDTO2 = new NotificationPreferenceDTO();
        assertThat(notificationPreferenceDTO1).isNotEqualTo(notificationPreferenceDTO2);
        notificationPreferenceDTO2.setId(notificationPreferenceDTO1.getId());
        assertThat(notificationPreferenceDTO1).isEqualTo(notificationPreferenceDTO2);
        notificationPreferenceDTO2.setId(2L);
        assertThat(notificationPreferenceDTO1).isNotEqualTo(notificationPreferenceDTO2);
        notificationPreferenceDTO1.setId(null);
        assertThat(notificationPreferenceDTO1).isNotEqualTo(notificationPreferenceDTO2);
    }
}
