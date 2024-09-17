package br.com.lisetech.cocoverde.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FrenteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FrenteDTO.class);
        FrenteDTO frenteDTO1 = new FrenteDTO();
        frenteDTO1.setId(1L);
        FrenteDTO frenteDTO2 = new FrenteDTO();
        assertThat(frenteDTO1).isNotEqualTo(frenteDTO2);
        frenteDTO2.setId(frenteDTO1.getId());
        assertThat(frenteDTO1).isEqualTo(frenteDTO2);
        frenteDTO2.setId(2L);
        assertThat(frenteDTO1).isNotEqualTo(frenteDTO2);
        frenteDTO1.setId(null);
        assertThat(frenteDTO1).isNotEqualTo(frenteDTO2);
    }
}
