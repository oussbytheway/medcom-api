package com.pharmaresolve.medcom.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pharmaresolve.medcom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WatchListDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WatchListDTO.class);
        WatchListDTO watchListDTO1 = new WatchListDTO();
        watchListDTO1.setId(1L);
        WatchListDTO watchListDTO2 = new WatchListDTO();
        assertThat(watchListDTO1).isNotEqualTo(watchListDTO2);
        watchListDTO2.setId(watchListDTO1.getId());
        assertThat(watchListDTO1).isEqualTo(watchListDTO2);
        watchListDTO2.setId(2L);
        assertThat(watchListDTO1).isNotEqualTo(watchListDTO2);
        watchListDTO1.setId(null);
        assertThat(watchListDTO1).isNotEqualTo(watchListDTO2);
    }
}
