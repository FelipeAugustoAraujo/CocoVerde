package br.com.lisetech.cocoverde.domain;

import static br.com.lisetech.cocoverde.domain.DetalhesEntradaFinanceiraTestSamples.*;
import static br.com.lisetech.cocoverde.domain.EntradaFinanceiraTestSamples.*;
import static br.com.lisetech.cocoverde.domain.EstoqueTestSamples.*;
import static br.com.lisetech.cocoverde.domain.FechamentoCaixaDetalhesTestSamples.*;
import static br.com.lisetech.cocoverde.domain.FornecedorTestSamples.*;
import static br.com.lisetech.cocoverde.domain.FrenteTestSamples.*;
import static br.com.lisetech.cocoverde.domain.ImagemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EntradaFinanceiraTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntradaFinanceira.class);
        EntradaFinanceira entradaFinanceira1 = getEntradaFinanceiraSample1();
        EntradaFinanceira entradaFinanceira2 = new EntradaFinanceira();
        assertThat(entradaFinanceira1).isNotEqualTo(entradaFinanceira2);

        entradaFinanceira2.setId(entradaFinanceira1.getId());
        assertThat(entradaFinanceira1).isEqualTo(entradaFinanceira2);

        entradaFinanceira2 = getEntradaFinanceiraSample2();
        assertThat(entradaFinanceira1).isNotEqualTo(entradaFinanceira2);
    }

    @Test
    void fornecedorTest() throws Exception {
        EntradaFinanceira entradaFinanceira = getEntradaFinanceiraRandomSampleGenerator();
        Fornecedor fornecedorBack = getFornecedorRandomSampleGenerator();

        entradaFinanceira.setFornecedor(fornecedorBack);
        assertThat(entradaFinanceira.getFornecedor()).isEqualTo(fornecedorBack);

        entradaFinanceira.fornecedor(null);
        assertThat(entradaFinanceira.getFornecedor()).isNull();
    }

    @Test
    void estoqueTest() throws Exception {
        EntradaFinanceira entradaFinanceira = getEntradaFinanceiraRandomSampleGenerator();
        Estoque estoqueBack = getEstoqueRandomSampleGenerator();

        entradaFinanceira.setEstoque(estoqueBack);
        assertThat(entradaFinanceira.getEstoque()).isEqualTo(estoqueBack);

        entradaFinanceira.estoque(null);
        assertThat(entradaFinanceira.getEstoque()).isNull();
    }

    @Test
    void frenteTest() throws Exception {
        EntradaFinanceira entradaFinanceira = getEntradaFinanceiraRandomSampleGenerator();
        Frente frenteBack = getFrenteRandomSampleGenerator();

        entradaFinanceira.setFrente(frenteBack);
        assertThat(entradaFinanceira.getFrente()).isEqualTo(frenteBack);

        entradaFinanceira.frente(null);
        assertThat(entradaFinanceira.getFrente()).isNull();
    }

    @Test
    void fechamentoCaixaDetalhesTest() throws Exception {
        EntradaFinanceira entradaFinanceira = getEntradaFinanceiraRandomSampleGenerator();
        FechamentoCaixaDetalhes fechamentoCaixaDetalhesBack = getFechamentoCaixaDetalhesRandomSampleGenerator();

        entradaFinanceira.setFechamentoCaixaDetalhes(fechamentoCaixaDetalhesBack);
        assertThat(entradaFinanceira.getFechamentoCaixaDetalhes()).isEqualTo(fechamentoCaixaDetalhesBack);

        entradaFinanceira.fechamentoCaixaDetalhes(null);
        assertThat(entradaFinanceira.getFechamentoCaixaDetalhes()).isNull();
    }

    @Test
    void detalhesEntradaFinanceiraTest() throws Exception {
        EntradaFinanceira entradaFinanceira = getEntradaFinanceiraRandomSampleGenerator();
        DetalhesEntradaFinanceira detalhesEntradaFinanceiraBack = getDetalhesEntradaFinanceiraRandomSampleGenerator();

        entradaFinanceira.addDetalhesEntradaFinanceira(detalhesEntradaFinanceiraBack);
        assertThat(entradaFinanceira.getDetalhesEntradaFinanceiras()).containsOnly(detalhesEntradaFinanceiraBack);
        assertThat(detalhesEntradaFinanceiraBack.getEntradaFinanceira()).isEqualTo(entradaFinanceira);

        entradaFinanceira.removeDetalhesEntradaFinanceira(detalhesEntradaFinanceiraBack);
        assertThat(entradaFinanceira.getDetalhesEntradaFinanceiras()).doesNotContain(detalhesEntradaFinanceiraBack);
        assertThat(detalhesEntradaFinanceiraBack.getEntradaFinanceira()).isNull();

        entradaFinanceira.detalhesEntradaFinanceiras(new HashSet<>(Set.of(detalhesEntradaFinanceiraBack)));
        assertThat(entradaFinanceira.getDetalhesEntradaFinanceiras()).containsOnly(detalhesEntradaFinanceiraBack);
        assertThat(detalhesEntradaFinanceiraBack.getEntradaFinanceira()).isEqualTo(entradaFinanceira);

        entradaFinanceira.setDetalhesEntradaFinanceiras(new HashSet<>());
        assertThat(entradaFinanceira.getDetalhesEntradaFinanceiras()).doesNotContain(detalhesEntradaFinanceiraBack);
        assertThat(detalhesEntradaFinanceiraBack.getEntradaFinanceira()).isNull();
    }

    @Test
    void imagemTest() throws Exception {
        EntradaFinanceira entradaFinanceira = getEntradaFinanceiraRandomSampleGenerator();
        Imagem imagemBack = getImagemRandomSampleGenerator();

        entradaFinanceira.addImagem(imagemBack);
        assertThat(entradaFinanceira.getImagems()).containsOnly(imagemBack);
        assertThat(imagemBack.getEntradaFinanceira()).isEqualTo(entradaFinanceira);

        entradaFinanceira.removeImagem(imagemBack);
        assertThat(entradaFinanceira.getImagems()).doesNotContain(imagemBack);
        assertThat(imagemBack.getEntradaFinanceira()).isNull();

        entradaFinanceira.imagems(new HashSet<>(Set.of(imagemBack)));
        assertThat(entradaFinanceira.getImagems()).containsOnly(imagemBack);
        assertThat(imagemBack.getEntradaFinanceira()).isEqualTo(entradaFinanceira);

        entradaFinanceira.setImagems(new HashSet<>());
        assertThat(entradaFinanceira.getImagems()).doesNotContain(imagemBack);
        assertThat(imagemBack.getEntradaFinanceira()).isNull();
    }
}
