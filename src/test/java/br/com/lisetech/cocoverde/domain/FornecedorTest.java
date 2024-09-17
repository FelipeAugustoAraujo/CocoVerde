package br.com.lisetech.cocoverde.domain;

import static br.com.lisetech.cocoverde.domain.EnderecoTestSamples.*;
import static br.com.lisetech.cocoverde.domain.EntradaFinanceiraTestSamples.*;
import static br.com.lisetech.cocoverde.domain.FornecedorTestSamples.*;
import static br.com.lisetech.cocoverde.domain.ProdutoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FornecedorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fornecedor.class);
        Fornecedor fornecedor1 = getFornecedorSample1();
        Fornecedor fornecedor2 = new Fornecedor();
        assertThat(fornecedor1).isNotEqualTo(fornecedor2);

        fornecedor2.setId(fornecedor1.getId());
        assertThat(fornecedor1).isEqualTo(fornecedor2);

        fornecedor2 = getFornecedorSample2();
        assertThat(fornecedor1).isNotEqualTo(fornecedor2);
    }

    @Test
    void produtoTest() throws Exception {
        Fornecedor fornecedor = getFornecedorRandomSampleGenerator();
        Produto produtoBack = getProdutoRandomSampleGenerator();

        fornecedor.addProduto(produtoBack);
        assertThat(fornecedor.getProdutos()).containsOnly(produtoBack);

        fornecedor.removeProduto(produtoBack);
        assertThat(fornecedor.getProdutos()).doesNotContain(produtoBack);

        fornecedor.produtos(new HashSet<>(Set.of(produtoBack)));
        assertThat(fornecedor.getProdutos()).containsOnly(produtoBack);

        fornecedor.setProdutos(new HashSet<>());
        assertThat(fornecedor.getProdutos()).doesNotContain(produtoBack);
    }

    @Test
    void enderecoTest() throws Exception {
        Fornecedor fornecedor = getFornecedorRandomSampleGenerator();
        Endereco enderecoBack = getEnderecoRandomSampleGenerator();

        fornecedor.setEndereco(enderecoBack);
        assertThat(fornecedor.getEndereco()).isEqualTo(enderecoBack);
        assertThat(enderecoBack.getFornecedor()).isEqualTo(fornecedor);

        fornecedor.endereco(null);
        assertThat(fornecedor.getEndereco()).isNull();
        assertThat(enderecoBack.getFornecedor()).isNull();
    }

    @Test
    void entradaFinanceiraTest() throws Exception {
        Fornecedor fornecedor = getFornecedorRandomSampleGenerator();
        EntradaFinanceira entradaFinanceiraBack = getEntradaFinanceiraRandomSampleGenerator();

        fornecedor.addEntradaFinanceira(entradaFinanceiraBack);
        assertThat(fornecedor.getEntradaFinanceiras()).containsOnly(entradaFinanceiraBack);
        assertThat(entradaFinanceiraBack.getFornecedor()).isEqualTo(fornecedor);

        fornecedor.removeEntradaFinanceira(entradaFinanceiraBack);
        assertThat(fornecedor.getEntradaFinanceiras()).doesNotContain(entradaFinanceiraBack);
        assertThat(entradaFinanceiraBack.getFornecedor()).isNull();

        fornecedor.entradaFinanceiras(new HashSet<>(Set.of(entradaFinanceiraBack)));
        assertThat(fornecedor.getEntradaFinanceiras()).containsOnly(entradaFinanceiraBack);
        assertThat(entradaFinanceiraBack.getFornecedor()).isEqualTo(fornecedor);

        fornecedor.setEntradaFinanceiras(new HashSet<>());
        assertThat(fornecedor.getEntradaFinanceiras()).doesNotContain(entradaFinanceiraBack);
        assertThat(entradaFinanceiraBack.getFornecedor()).isNull();
    }
}
