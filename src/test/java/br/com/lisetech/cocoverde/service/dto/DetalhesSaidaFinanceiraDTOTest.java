package br.com.lisetech.cocoverde.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetalhesSaidaFinanceiraDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalhesSaidaFinanceiraDTO.class);
        DetalhesSaidaFinanceiraDTO detalhesSaidaFinanceiraDTO1 = new DetalhesSaidaFinanceiraDTO();
        detalhesSaidaFinanceiraDTO1.setId(1L);
        DetalhesSaidaFinanceiraDTO detalhesSaidaFinanceiraDTO2 = new DetalhesSaidaFinanceiraDTO();
        assertThat(detalhesSaidaFinanceiraDTO1).isNotEqualTo(detalhesSaidaFinanceiraDTO2);
        detalhesSaidaFinanceiraDTO2.setId(detalhesSaidaFinanceiraDTO1.getId());
        assertThat(detalhesSaidaFinanceiraDTO1).isEqualTo(detalhesSaidaFinanceiraDTO2);
        detalhesSaidaFinanceiraDTO2.setId(2L);
        assertThat(detalhesSaidaFinanceiraDTO1).isNotEqualTo(detalhesSaidaFinanceiraDTO2);
        detalhesSaidaFinanceiraDTO1.setId(null);
        assertThat(detalhesSaidaFinanceiraDTO1).isNotEqualTo(detalhesSaidaFinanceiraDTO2);
    }
}
