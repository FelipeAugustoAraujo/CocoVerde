package br.com.lisetech.cocoverde.domain;

import static br.com.lisetech.cocoverde.domain.DetalhesEntradaFinanceiraTestSamples.*;
import static br.com.lisetech.cocoverde.domain.EntradaFinanceiraTestSamples.*;
import static br.com.lisetech.cocoverde.domain.ProdutoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetalhesEntradaFinanceiraTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalhesEntradaFinanceira.class);
        DetalhesEntradaFinanceira detalhesEntradaFinanceira1 = getDetalhesEntradaFinanceiraSample1();
        DetalhesEntradaFinanceira detalhesEntradaFinanceira2 = new DetalhesEntradaFinanceira();
        assertThat(detalhesEntradaFinanceira1).isNotEqualTo(detalhesEntradaFinanceira2);

        detalhesEntradaFinanceira2.setId(detalhesEntradaFinanceira1.getId());
        assertThat(detalhesEntradaFinanceira1).isEqualTo(detalhesEntradaFinanceira2);

        detalhesEntradaFinanceira2 = getDetalhesEntradaFinanceiraSample2();
        assertThat(detalhesEntradaFinanceira1).isNotEqualTo(detalhesEntradaFinanceira2);
    }

    @Test
    void produtoTest() throws Exception {
        DetalhesEntradaFinanceira detalhesEntradaFinanceira = getDetalhesEntradaFinanceiraRandomSampleGenerator();
        Produto produtoBack = getProdutoRandomSampleGenerator();

        detalhesEntradaFinanceira.setProduto(produtoBack);
        assertThat(detalhesEntradaFinanceira.getProduto()).isEqualTo(produtoBack);

        detalhesEntradaFinanceira.produto(null);
        assertThat(detalhesEntradaFinanceira.getProduto()).isNull();
    }

    @Test
    void entradaFinanceiraTest() throws Exception {
        DetalhesEntradaFinanceira detalhesEntradaFinanceira = getDetalhesEntradaFinanceiraRandomSampleGenerator();
        EntradaFinanceira entradaFinanceiraBack = getEntradaFinanceiraRandomSampleGenerator();

        detalhesEntradaFinanceira.setEntradaFinanceira(entradaFinanceiraBack);
        assertThat(detalhesEntradaFinanceira.getEntradaFinanceira()).isEqualTo(entradaFinanceiraBack);

        detalhesEntradaFinanceira.entradaFinanceira(null);
        assertThat(detalhesEntradaFinanceira.getEntradaFinanceira()).isNull();
    }
}
