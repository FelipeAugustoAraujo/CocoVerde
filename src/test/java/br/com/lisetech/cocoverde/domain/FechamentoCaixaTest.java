package br.com.lisetech.cocoverde.domain;

import static br.com.lisetech.cocoverde.domain.FechamentoCaixaDetalhesTestSamples.*;
import static br.com.lisetech.cocoverde.domain.FechamentoCaixaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FechamentoCaixaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FechamentoCaixa.class);
        FechamentoCaixa fechamentoCaixa1 = getFechamentoCaixaSample1();
        FechamentoCaixa fechamentoCaixa2 = new FechamentoCaixa();
        assertThat(fechamentoCaixa1).isNotEqualTo(fechamentoCaixa2);

        fechamentoCaixa2.setId(fechamentoCaixa1.getId());
        assertThat(fechamentoCaixa1).isEqualTo(fechamentoCaixa2);

        fechamentoCaixa2 = getFechamentoCaixaSample2();
        assertThat(fechamentoCaixa1).isNotEqualTo(fechamentoCaixa2);
    }

    @Test
    void fechamentoCaixaDetalhesTest() throws Exception {
        FechamentoCaixa fechamentoCaixa = getFechamentoCaixaRandomSampleGenerator();
        FechamentoCaixaDetalhes fechamentoCaixaDetalhesBack = getFechamentoCaixaDetalhesRandomSampleGenerator();

        fechamentoCaixa.setFechamentoCaixaDetalhes(fechamentoCaixaDetalhesBack);
        assertThat(fechamentoCaixa.getFechamentoCaixaDetalhes()).isEqualTo(fechamentoCaixaDetalhesBack);
        assertThat(fechamentoCaixaDetalhesBack.getFechamentoCaixa()).isEqualTo(fechamentoCaixa);

        fechamentoCaixa.fechamentoCaixaDetalhes(null);
        assertThat(fechamentoCaixa.getFechamentoCaixaDetalhes()).isNull();
        assertThat(fechamentoCaixaDetalhesBack.getFechamentoCaixa()).isNull();
    }
}
