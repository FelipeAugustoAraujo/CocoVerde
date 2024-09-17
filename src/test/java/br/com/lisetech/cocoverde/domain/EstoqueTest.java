package br.com.lisetech.cocoverde.domain;

import static br.com.lisetech.cocoverde.domain.EntradaFinanceiraTestSamples.*;
import static br.com.lisetech.cocoverde.domain.EstoqueTestSamples.*;
import static br.com.lisetech.cocoverde.domain.ProdutoTestSamples.*;
import static br.com.lisetech.cocoverde.domain.SaidaFinanceiraTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EstoqueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estoque.class);
        Estoque estoque1 = getEstoqueSample1();
        Estoque estoque2 = new Estoque();
        assertThat(estoque1).isNotEqualTo(estoque2);

        estoque2.setId(estoque1.getId());
        assertThat(estoque1).isEqualTo(estoque2);

        estoque2 = getEstoqueSample2();
        assertThat(estoque1).isNotEqualTo(estoque2);
    }

    @Test
    void produtoTest() throws Exception {
        Estoque estoque = getEstoqueRandomSampleGenerator();
        Produto produtoBack = getProdutoRandomSampleGenerator();

        estoque.addProduto(produtoBack);
        assertThat(estoque.getProdutos()).containsOnly(produtoBack);
        assertThat(produtoBack.getEstoque()).isEqualTo(estoque);

        estoque.removeProduto(produtoBack);
        assertThat(estoque.getProdutos()).doesNotContain(produtoBack);
        assertThat(produtoBack.getEstoque()).isNull();

        estoque.produtos(new HashSet<>(Set.of(produtoBack)));
        assertThat(estoque.getProdutos()).containsOnly(produtoBack);
        assertThat(produtoBack.getEstoque()).isEqualTo(estoque);

        estoque.setProdutos(new HashSet<>());
        assertThat(estoque.getProdutos()).doesNotContain(produtoBack);
        assertThat(produtoBack.getEstoque()).isNull();
    }

    @Test
    void entradaFinanceiraTest() throws Exception {
        Estoque estoque = getEstoqueRandomSampleGenerator();
        EntradaFinanceira entradaFinanceiraBack = getEntradaFinanceiraRandomSampleGenerator();

        estoque.addEntradaFinanceira(entradaFinanceiraBack);
        assertThat(estoque.getEntradaFinanceiras()).containsOnly(entradaFinanceiraBack);
        assertThat(entradaFinanceiraBack.getEstoque()).isEqualTo(estoque);

        estoque.removeEntradaFinanceira(entradaFinanceiraBack);
        assertThat(estoque.getEntradaFinanceiras()).doesNotContain(entradaFinanceiraBack);
        assertThat(entradaFinanceiraBack.getEstoque()).isNull();

        estoque.entradaFinanceiras(new HashSet<>(Set.of(entradaFinanceiraBack)));
        assertThat(estoque.getEntradaFinanceiras()).containsOnly(entradaFinanceiraBack);
        assertThat(entradaFinanceiraBack.getEstoque()).isEqualTo(estoque);

        estoque.setEntradaFinanceiras(new HashSet<>());
        assertThat(estoque.getEntradaFinanceiras()).doesNotContain(entradaFinanceiraBack);
        assertThat(entradaFinanceiraBack.getEstoque()).isNull();
    }

    @Test
    void saidaFinanceiraTest() throws Exception {
        Estoque estoque = getEstoqueRandomSampleGenerator();
        SaidaFinanceira saidaFinanceiraBack = getSaidaFinanceiraRandomSampleGenerator();

        estoque.addSaidaFinanceira(saidaFinanceiraBack);
        assertThat(estoque.getSaidaFinanceiras()).containsOnly(saidaFinanceiraBack);
        assertThat(saidaFinanceiraBack.getEstoque()).isEqualTo(estoque);

        estoque.removeSaidaFinanceira(saidaFinanceiraBack);
        assertThat(estoque.getSaidaFinanceiras()).doesNotContain(saidaFinanceiraBack);
        assertThat(saidaFinanceiraBack.getEstoque()).isNull();

        estoque.saidaFinanceiras(new HashSet<>(Set.of(saidaFinanceiraBack)));
        assertThat(estoque.getSaidaFinanceiras()).containsOnly(saidaFinanceiraBack);
        assertThat(saidaFinanceiraBack.getEstoque()).isEqualTo(estoque);

        estoque.setSaidaFinanceiras(new HashSet<>());
        assertThat(estoque.getSaidaFinanceiras()).doesNotContain(saidaFinanceiraBack);
        assertThat(saidaFinanceiraBack.getEstoque()).isNull();
    }
}
