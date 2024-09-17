package br.com.lisetech.cocoverde.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FechamentoCaixaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FechamentoCaixaDTO.class);
        FechamentoCaixaDTO fechamentoCaixaDTO1 = new FechamentoCaixaDTO();
        fechamentoCaixaDTO1.setId(1L);
        FechamentoCaixaDTO fechamentoCaixaDTO2 = new FechamentoCaixaDTO();
        assertThat(fechamentoCaixaDTO1).isNotEqualTo(fechamentoCaixaDTO2);
        fechamentoCaixaDTO2.setId(fechamentoCaixaDTO1.getId());
        assertThat(fechamentoCaixaDTO1).isEqualTo(fechamentoCaixaDTO2);
        fechamentoCaixaDTO2.setId(2L);
        assertThat(fechamentoCaixaDTO1).isNotEqualTo(fechamentoCaixaDTO2);
        fechamentoCaixaDTO1.setId(null);
        assertThat(fechamentoCaixaDTO1).isNotEqualTo(fechamentoCaixaDTO2);
    }
}
