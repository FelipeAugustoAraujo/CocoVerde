package br.com.lisetech.cocoverde.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetalhesEntradaFinanceiraDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalhesEntradaFinanceiraDTO.class);
        DetalhesEntradaFinanceiraDTO detalhesEntradaFinanceiraDTO1 = new DetalhesEntradaFinanceiraDTO();
        detalhesEntradaFinanceiraDTO1.setId(1L);
        DetalhesEntradaFinanceiraDTO detalhesEntradaFinanceiraDTO2 = new DetalhesEntradaFinanceiraDTO();
        assertThat(detalhesEntradaFinanceiraDTO1).isNotEqualTo(detalhesEntradaFinanceiraDTO2);
        detalhesEntradaFinanceiraDTO2.setId(detalhesEntradaFinanceiraDTO1.getId());
        assertThat(detalhesEntradaFinanceiraDTO1).isEqualTo(detalhesEntradaFinanceiraDTO2);
        detalhesEntradaFinanceiraDTO2.setId(2L);
        assertThat(detalhesEntradaFinanceiraDTO1).isNotEqualTo(detalhesEntradaFinanceiraDTO2);
        detalhesEntradaFinanceiraDTO1.setId(null);
        assertThat(detalhesEntradaFinanceiraDTO1).isNotEqualTo(detalhesEntradaFinanceiraDTO2);
    }
}
