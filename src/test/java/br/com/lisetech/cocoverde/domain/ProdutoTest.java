package br.com.lisetech.cocoverde.domain;

import static br.com.lisetech.cocoverde.domain.DetalhesEntradaFinanceiraTestSamples.*;
import static br.com.lisetech.cocoverde.domain.EstoqueTestSamples.*;
import static br.com.lisetech.cocoverde.domain.FornecedorTestSamples.*;
import static br.com.lisetech.cocoverde.domain.FrenteTestSamples.*;
import static br.com.lisetech.cocoverde.domain.ProdutoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProdutoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Produto.class);
        Produto produto1 = getProdutoSample1();
        Produto produto2 = new Produto();
        assertThat(produto1).isNotEqualTo(produto2);

        produto2.setId(produto1.getId());
        assertThat(produto1).isEqualTo(produto2);

        produto2 = getProdutoSample2();
        assertThat(produto1).isNotEqualTo(produto2);
    }

    @Test
    void estoqueTest() throws Exception {
        Produto produto = getProdutoRandomSampleGenerator();
        Estoque estoqueBack = getEstoqueRandomSampleGenerator();

        produto.setEstoque(estoqueBack);
        assertThat(produto.getEstoque()).isEqualTo(estoqueBack);

        produto.estoque(null);
        assertThat(produto.getEstoque()).isNull();
    }

    @Test
    void frenteTest() throws Exception {
        Produto produto = getProdutoRandomSampleGenerator();
        Frente frenteBack = getFrenteRandomSampleGenerator();

        produto.setFrente(frenteBack);
        assertThat(produto.getFrente()).isEqualTo(frenteBack);

        produto.frente(null);
        assertThat(produto.getFrente()).isNull();
    }

    @Test
    void detalhesEntradaFinanceiraTest() throws Exception {
        Produto produto = getProdutoRandomSampleGenerator();
        DetalhesEntradaFinanceira detalhesEntradaFinanceiraBack = getDetalhesEntradaFinanceiraRandomSampleGenerator();

        produto.addDetalhesEntradaFinanceira(detalhesEntradaFinanceiraBack);
        assertThat(produto.getDetalhesEntradaFinanceiras()).containsOnly(detalhesEntradaFinanceiraBack);
        assertThat(detalhesEntradaFinanceiraBack.getProduto()).isEqualTo(produto);

        produto.removeDetalhesEntradaFinanceira(detalhesEntradaFinanceiraBack);
        assertThat(produto.getDetalhesEntradaFinanceiras()).doesNotContain(detalhesEntradaFinanceiraBack);
        assertThat(detalhesEntradaFinanceiraBack.getProduto()).isNull();

        produto.detalhesEntradaFinanceiras(new HashSet<>(Set.of(detalhesEntradaFinanceiraBack)));
        assertThat(produto.getDetalhesEntradaFinanceiras()).containsOnly(detalhesEntradaFinanceiraBack);
        assertThat(detalhesEntradaFinanceiraBack.getProduto()).isEqualTo(produto);

        produto.setDetalhesEntradaFinanceiras(new HashSet<>());
        assertThat(produto.getDetalhesEntradaFinanceiras()).doesNotContain(detalhesEntradaFinanceiraBack);
        assertThat(detalhesEntradaFinanceiraBack.getProduto()).isNull();
    }

    @Test
    void fornecedorTest() throws Exception {
        Produto produto = getProdutoRandomSampleGenerator();
        Fornecedor fornecedorBack = getFornecedorRandomSampleGenerator();

        produto.addFornecedor(fornecedorBack);
        assertThat(produto.getFornecedors()).containsOnly(fornecedorBack);
        assertThat(fornecedorBack.getProdutos()).containsOnly(produto);

        produto.removeFornecedor(fornecedorBack);
        assertThat(produto.getFornecedors()).doesNotContain(fornecedorBack);
        assertThat(fornecedorBack.getProdutos()).doesNotContain(produto);

        produto.fornecedors(new HashSet<>(Set.of(fornecedorBack)));
        assertThat(produto.getFornecedors()).containsOnly(fornecedorBack);
        assertThat(fornecedorBack.getProdutos()).containsOnly(produto);

        produto.setFornecedors(new HashSet<>());
        assertThat(produto.getFornecedors()).doesNotContain(fornecedorBack);
        assertThat(fornecedorBack.getProdutos()).doesNotContain(produto);
    }
}
