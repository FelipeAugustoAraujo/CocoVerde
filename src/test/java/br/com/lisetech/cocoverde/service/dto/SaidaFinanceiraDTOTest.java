package br.com.lisetech.cocoverde.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SaidaFinanceiraDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaidaFinanceiraDTO.class);
        SaidaFinanceiraDTO saidaFinanceiraDTO1 = new SaidaFinanceiraDTO();
        saidaFinanceiraDTO1.setId(1L);
        SaidaFinanceiraDTO saidaFinanceiraDTO2 = new SaidaFinanceiraDTO();
        assertThat(saidaFinanceiraDTO1).isNotEqualTo(saidaFinanceiraDTO2);
        saidaFinanceiraDTO2.setId(saidaFinanceiraDTO1.getId());
        assertThat(saidaFinanceiraDTO1).isEqualTo(saidaFinanceiraDTO2);
        saidaFinanceiraDTO2.setId(2L);
        assertThat(saidaFinanceiraDTO1).isNotEqualTo(saidaFinanceiraDTO2);
        saidaFinanceiraDTO1.setId(null);
        assertThat(saidaFinanceiraDTO1).isNotEqualTo(saidaFinanceiraDTO2);
    }
}
