package com.pharmaresolve.medcom.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pharmaresolve.medcom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WatchListLogDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WatchListLogDTO.class);
        WatchListLogDTO watchListLogDTO1 = new WatchListLogDTO();
        watchListLogDTO1.setId(1L);
        WatchListLogDTO watchListLogDTO2 = new WatchListLogDTO();
        assertThat(watchListLogDTO1).isNotEqualTo(watchListLogDTO2);
        watchListLogDTO2.setId(watchListLogDTO1.getId());
        assertThat(watchListLogDTO1).isEqualTo(watchListLogDTO2);
        watchListLogDTO2.setId(2L);
        assertThat(watchListLogDTO1).isNotEqualTo(watchListLogDTO2);
        watchListLogDTO1.setId(null);
        assertThat(watchListLogDTO1).isNotEqualTo(watchListLogDTO2);
    }
}
