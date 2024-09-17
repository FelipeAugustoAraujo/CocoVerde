package br.com.lisetech.cocoverde.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntradaFinanceiraDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntradaFinanceiraDTO.class);
        EntradaFinanceiraDTO entradaFinanceiraDTO1 = new EntradaFinanceiraDTO();
        entradaFinanceiraDTO1.setId(1L);
        EntradaFinanceiraDTO entradaFinanceiraDTO2 = new EntradaFinanceiraDTO();
        assertThat(entradaFinanceiraDTO1).isNotEqualTo(entradaFinanceiraDTO2);
        entradaFinanceiraDTO2.setId(entradaFinanceiraDTO1.getId());
        assertThat(entradaFinanceiraDTO1).isEqualTo(entradaFinanceiraDTO2);
        entradaFinanceiraDTO2.setId(2L);
        assertThat(entradaFinanceiraDTO1).isNotEqualTo(entradaFinanceiraDTO2);
        entradaFinanceiraDTO1.setId(null);
        assertThat(entradaFinanceiraDTO1).isNotEqualTo(entradaFinanceiraDTO2);
    }
}
