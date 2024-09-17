package br.com.lisetech.cocoverde.domain;

import static br.com.lisetech.cocoverde.domain.EstoqueTestSamples.*;
import static br.com.lisetech.cocoverde.domain.FechamentoCaixaDetalhesTestSamples.*;
import static br.com.lisetech.cocoverde.domain.FrenteTestSamples.*;
import static br.com.lisetech.cocoverde.domain.ImagemTestSamples.*;
import static br.com.lisetech.cocoverde.domain.SaidaFinanceiraTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SaidaFinanceiraTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaidaFinanceira.class);
        SaidaFinanceira saidaFinanceira1 = getSaidaFinanceiraSample1();
        SaidaFinanceira saidaFinanceira2 = new SaidaFinanceira();
        assertThat(saidaFinanceira1).isNotEqualTo(saidaFinanceira2);

        saidaFinanceira2.setId(saidaFinanceira1.getId());
        assertThat(saidaFinanceira1).isEqualTo(saidaFinanceira2);

        saidaFinanceira2 = getSaidaFinanceiraSample2();
        assertThat(saidaFinanceira1).isNotEqualTo(saidaFinanceira2);
    }

    @Test
    void estoqueTest() throws Exception {
        SaidaFinanceira saidaFinanceira = getSaidaFinanceiraRandomSampleGenerator();
        Estoque estoqueBack = getEstoqueRandomSampleGenerator();

        saidaFinanceira.setEstoque(estoqueBack);
        assertThat(saidaFinanceira.getEstoque()).isEqualTo(estoqueBack);

        saidaFinanceira.estoque(null);
        assertThat(saidaFinanceira.getEstoque()).isNull();
    }

    @Test
    void frenteTest() throws Exception {
        SaidaFinanceira saidaFinanceira = getSaidaFinanceiraRandomSampleGenerator();
        Frente frenteBack = getFrenteRandomSampleGenerator();

        saidaFinanceira.setFrente(frenteBack);
        assertThat(saidaFinanceira.getFrente()).isEqualTo(frenteBack);

        saidaFinanceira.frente(null);
        assertThat(saidaFinanceira.getFrente()).isNull();
    }

    @Test
    void fechamentoCaixaDetalhesTest() throws Exception {
        SaidaFinanceira saidaFinanceira = getSaidaFinanceiraRandomSampleGenerator();
        FechamentoCaixaDetalhes fechamentoCaixaDetalhesBack = getFechamentoCaixaDetalhesRandomSampleGenerator();

        saidaFinanceira.setFechamentoCaixaDetalhes(fechamentoCaixaDetalhesBack);
        assertThat(saidaFinanceira.getFechamentoCaixaDetalhes()).isEqualTo(fechamentoCaixaDetalhesBack);

        saidaFinanceira.fechamentoCaixaDetalhes(null);
        assertThat(saidaFinanceira.getFechamentoCaixaDetalhes()).isNull();
    }

    @Test
    void imagemTest() throws Exception {
        SaidaFinanceira saidaFinanceira = getSaidaFinanceiraRandomSampleGenerator();
        Imagem imagemBack = getImagemRandomSampleGenerator();

        saidaFinanceira.addImagem(imagemBack);
        assertThat(saidaFinanceira.getImagems()).containsOnly(imagemBack);
        assertThat(imagemBack.getSaidaFinanceira()).isEqualTo(saidaFinanceira);

        saidaFinanceira.removeImagem(imagemBack);
        assertThat(saidaFinanceira.getImagems()).doesNotContain(imagemBack);
        assertThat(imagemBack.getSaidaFinanceira()).isNull();

        saidaFinanceira.imagems(new HashSet<>(Set.of(imagemBack)));
        assertThat(saidaFinanceira.getImagems()).containsOnly(imagemBack);
        assertThat(imagemBack.getSaidaFinanceira()).isEqualTo(saidaFinanceira);

        saidaFinanceira.setImagems(new HashSet<>());
        assertThat(saidaFinanceira.getImagems()).doesNotContain(imagemBack);
        assertThat(imagemBack.getSaidaFinanceira()).isNull();
    }
}
