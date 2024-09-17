package br.com.lisetech.cocoverde.domain;

import static br.com.lisetech.cocoverde.domain.DetalhesSaidaFinanceiraTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetalhesSaidaFinanceiraTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalhesSaidaFinanceira.class);
        DetalhesSaidaFinanceira detalhesSaidaFinanceira1 = getDetalhesSaidaFinanceiraSample1();
        DetalhesSaidaFinanceira detalhesSaidaFinanceira2 = new DetalhesSaidaFinanceira();
        assertThat(detalhesSaidaFinanceira1).isNotEqualTo(detalhesSaidaFinanceira2);

        detalhesSaidaFinanceira2.setId(detalhesSaidaFinanceira1.getId());
        assertThat(detalhesSaidaFinanceira1).isEqualTo(detalhesSaidaFinanceira2);

        detalhesSaidaFinanceira2 = getDetalhesSaidaFinanceiraSample2();
        assertThat(detalhesSaidaFinanceira1).isNotEqualTo(detalhesSaidaFinanceira2);
    }
}
