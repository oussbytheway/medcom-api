package com.pharmaresolve.medcom.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pharmaresolve.medcom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppConnectionLogDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppConnectionLogDTO.class);
        AppConnectionLogDTO appConnectionLogDTO1 = new AppConnectionLogDTO();
        appConnectionLogDTO1.setId(1L);
        AppConnectionLogDTO appConnectionLogDTO2 = new AppConnectionLogDTO();
        assertThat(appConnectionLogDTO1).isNotEqualTo(appConnectionLogDTO2);
        appConnectionLogDTO2.setId(appConnectionLogDTO1.getId());
        assertThat(appConnectionLogDTO1).isEqualTo(appConnectionLogDTO2);
        appConnectionLogDTO2.setId(2L);
        assertThat(appConnectionLogDTO1).isNotEqualTo(appConnectionLogDTO2);
        appConnectionLogDTO1.setId(null);
        assertThat(appConnectionLogDTO1).isNotEqualTo(appConnectionLogDTO2);
    }
}
