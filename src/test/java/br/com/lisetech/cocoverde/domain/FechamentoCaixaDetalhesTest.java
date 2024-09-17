package br.com.lisetech.cocoverde.domain;

import static br.com.lisetech.cocoverde.domain.EntradaFinanceiraTestSamples.*;
import static br.com.lisetech.cocoverde.domain.FechamentoCaixaDetalhesTestSamples.*;
import static br.com.lisetech.cocoverde.domain.FechamentoCaixaTestSamples.*;
import static br.com.lisetech.cocoverde.domain.SaidaFinanceiraTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FechamentoCaixaDetalhesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FechamentoCaixaDetalhes.class);
        FechamentoCaixaDetalhes fechamentoCaixaDetalhes1 = getFechamentoCaixaDetalhesSample1();
        FechamentoCaixaDetalhes fechamentoCaixaDetalhes2 = new FechamentoCaixaDetalhes();
        assertThat(fechamentoCaixaDetalhes1).isNotEqualTo(fechamentoCaixaDetalhes2);

        fechamentoCaixaDetalhes2.setId(fechamentoCaixaDetalhes1.getId());
        assertThat(fechamentoCaixaDetalhes1).isEqualTo(fechamentoCaixaDetalhes2);

        fechamentoCaixaDetalhes2 = getFechamentoCaixaDetalhesSample2();
        assertThat(fechamentoCaixaDetalhes1).isNotEqualTo(fechamentoCaixaDetalhes2);
    }

    @Test
    void fechamentoCaixaTest() throws Exception {
        FechamentoCaixaDetalhes fechamentoCaixaDetalhes = getFechamentoCaixaDetalhesRandomSampleGenerator();
        FechamentoCaixa fechamentoCaixaBack = getFechamentoCaixaRandomSampleGenerator();

        fechamentoCaixaDetalhes.setFechamentoCaixa(fechamentoCaixaBack);
        assertThat(fechamentoCaixaDetalhes.getFechamentoCaixa()).isEqualTo(fechamentoCaixaBack);

        fechamentoCaixaDetalhes.fechamentoCaixa(null);
        assertThat(fechamentoCaixaDetalhes.getFechamentoCaixa()).isNull();
    }

    @Test
    void entradaFinanceiraTest() throws Exception {
        FechamentoCaixaDetalhes fechamentoCaixaDetalhes = getFechamentoCaixaDetalhesRandomSampleGenerator();
        EntradaFinanceira entradaFinanceiraBack = getEntradaFinanceiraRandomSampleGenerator();

        fechamentoCaixaDetalhes.addEntradaFinanceira(entradaFinanceiraBack);
        assertThat(fechamentoCaixaDetalhes.getEntradaFinanceiras()).containsOnly(entradaFinanceiraBack);
        assertThat(entradaFinanceiraBack.getFechamentoCaixaDetalhes()).isEqualTo(fechamentoCaixaDetalhes);

        fechamentoCaixaDetalhes.removeEntradaFinanceira(entradaFinanceiraBack);
        assertThat(fechamentoCaixaDetalhes.getEntradaFinanceiras()).doesNotContain(entradaFinanceiraBack);
        assertThat(entradaFinanceiraBack.getFechamentoCaixaDetalhes()).isNull();

        fechamentoCaixaDetalhes.entradaFinanceiras(new HashSet<>(Set.of(entradaFinanceiraBack)));
        assertThat(fechamentoCaixaDetalhes.getEntradaFinanceiras()).containsOnly(entradaFinanceiraBack);
        assertThat(entradaFinanceiraBack.getFechamentoCaixaDetalhes()).isEqualTo(fechamentoCaixaDetalhes);

        fechamentoCaixaDetalhes.setEntradaFinanceiras(new HashSet<>());
        assertThat(fechamentoCaixaDetalhes.getEntradaFinanceiras()).doesNotContain(entradaFinanceiraBack);
        assertThat(entradaFinanceiraBack.getFechamentoCaixaDetalhes()).isNull();
    }

    @Test
    void saidaFinanceiraTest() throws Exception {
        FechamentoCaixaDetalhes fechamentoCaixaDetalhes = getFechamentoCaixaDetalhesRandomSampleGenerator();
        SaidaFinanceira saidaFinanceiraBack = getSaidaFinanceiraRandomSampleGenerator();

        fechamentoCaixaDetalhes.addSaidaFinanceira(saidaFinanceiraBack);
        assertThat(fechamentoCaixaDetalhes.getSaidaFinanceiras()).containsOnly(saidaFinanceiraBack);
        assertThat(saidaFinanceiraBack.getFechamentoCaixaDetalhes()).isEqualTo(fechamentoCaixaDetalhes);

        fechamentoCaixaDetalhes.removeSaidaFinanceira(saidaFinanceiraBack);
        assertThat(fechamentoCaixaDetalhes.getSaidaFinanceiras()).doesNotContain(saidaFinanceiraBack);
        assertThat(saidaFinanceiraBack.getFechamentoCaixaDetalhes()).isNull();

        fechamentoCaixaDetalhes.saidaFinanceiras(new HashSet<>(Set.of(saidaFinanceiraBack)));
        assertThat(fechamentoCaixaDetalhes.getSaidaFinanceiras()).containsOnly(saidaFinanceiraBack);
        assertThat(saidaFinanceiraBack.getFechamentoCaixaDetalhes()).isEqualTo(fechamentoCaixaDetalhes);

        fechamentoCaixaDetalhes.setSaidaFinanceiras(new HashSet<>());
        assertThat(fechamentoCaixaDetalhes.getSaidaFinanceiras()).doesNotContain(saidaFinanceiraBack);
        assertThat(saidaFinanceiraBack.getFechamentoCaixaDetalhes()).isNull();
    }
}
