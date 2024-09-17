package br.com.lisetech.cocoverde.domain;

import static br.com.lisetech.cocoverde.domain.EntradaFinanceiraTestSamples.*;
import static br.com.lisetech.cocoverde.domain.FrenteTestSamples.*;
import static br.com.lisetech.cocoverde.domain.ProdutoTestSamples.*;
import static br.com.lisetech.cocoverde.domain.SaidaFinanceiraTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FrenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Frente.class);
        Frente frente1 = getFrenteSample1();
        Frente frente2 = new Frente();
        assertThat(frente1).isNotEqualTo(frente2);

        frente2.setId(frente1.getId());
        assertThat(frente1).isEqualTo(frente2);

        frente2 = getFrenteSample2();
        assertThat(frente1).isNotEqualTo(frente2);
    }

    @Test
    void produtoTest() throws Exception {
        Frente frente = getFrenteRandomSampleGenerator();
        Produto produtoBack = getProdutoRandomSampleGenerator();

        frente.addProduto(produtoBack);
        assertThat(frente.getProdutos()).containsOnly(produtoBack);
        assertThat(produtoBack.getFrente()).isEqualTo(frente);

        frente.removeProduto(produtoBack);
        assertThat(frente.getProdutos()).doesNotContain(produtoBack);
        assertThat(produtoBack.getFrente()).isNull();

        frente.produtos(new HashSet<>(Set.of(produtoBack)));
        assertThat(frente.getProdutos()).containsOnly(produtoBack);
        assertThat(produtoBack.getFrente()).isEqualTo(frente);

        frente.setProdutos(new HashSet<>());
        assertThat(frente.getProdutos()).doesNotContain(produtoBack);
        assertThat(produtoBack.getFrente()).isNull();
    }

    @Test
    void entradaFinanceiraTest() throws Exception {
        Frente frente = getFrenteRandomSampleGenerator();
        EntradaFinanceira entradaFinanceiraBack = getEntradaFinanceiraRandomSampleGenerator();

        frente.addEntradaFinanceira(entradaFinanceiraBack);
        assertThat(frente.getEntradaFinanceiras()).containsOnly(entradaFinanceiraBack);
        assertThat(entradaFinanceiraBack.getFrente()).isEqualTo(frente);

        frente.removeEntradaFinanceira(entradaFinanceiraBack);
        assertThat(frente.getEntradaFinanceiras()).doesNotContain(entradaFinanceiraBack);
        assertThat(entradaFinanceiraBack.getFrente()).isNull();

        frente.entradaFinanceiras(new HashSet<>(Set.of(entradaFinanceiraBack)));
        assertThat(frente.getEntradaFinanceiras()).containsOnly(entradaFinanceiraBack);
        assertThat(entradaFinanceiraBack.getFrente()).isEqualTo(frente);

        frente.setEntradaFinanceiras(new HashSet<>());
        assertThat(frente.getEntradaFinanceiras()).doesNotContain(entradaFinanceiraBack);
        assertThat(entradaFinanceiraBack.getFrente()).isNull();
    }

    @Test
    void saidaFinanceiraTest() throws Exception {
        Frente frente = getFrenteRandomSampleGenerator();
        SaidaFinanceira saidaFinanceiraBack = getSaidaFinanceiraRandomSampleGenerator();

        frente.addSaidaFinanceira(saidaFinanceiraBack);
        assertThat(frente.getSaidaFinanceiras()).containsOnly(saidaFinanceiraBack);
        assertThat(saidaFinanceiraBack.getFrente()).isEqualTo(frente);

        frente.removeSaidaFinanceira(saidaFinanceiraBack);
        assertThat(frente.getSaidaFinanceiras()).doesNotContain(saidaFinanceiraBack);
        assertThat(saidaFinanceiraBack.getFrente()).isNull();

        frente.saidaFinanceiras(new HashSet<>(Set.of(saidaFinanceiraBack)));
        assertThat(frente.getSaidaFinanceiras()).containsOnly(saidaFinanceiraBack);
        assertThat(saidaFinanceiraBack.getFrente()).isEqualTo(frente);

        frente.setSaidaFinanceiras(new HashSet<>());
        assertThat(frente.getSaidaFinanceiras()).doesNotContain(saidaFinanceiraBack);
        assertThat(saidaFinanceiraBack.getFrente()).isNull();
    }
}
