package br.com.lisetech.cocoverde.domain;

import static br.com.lisetech.cocoverde.domain.EntradaFinanceiraTestSamples.*;
import static br.com.lisetech.cocoverde.domain.ImagemTestSamples.*;
import static br.com.lisetech.cocoverde.domain.SaidaFinanceiraTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ImagemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Imagem.class);
        Imagem imagem1 = getImagemSample1();
        Imagem imagem2 = new Imagem();
        assertThat(imagem1).isNotEqualTo(imagem2);

        imagem2.setId(imagem1.getId());
        assertThat(imagem1).isEqualTo(imagem2);

        imagem2 = getImagemSample2();
        assertThat(imagem1).isNotEqualTo(imagem2);
    }

    @Test
    void saidaFinanceiraTest() throws Exception {
        Imagem imagem = getImagemRandomSampleGenerator();
        SaidaFinanceira saidaFinanceiraBack = getSaidaFinanceiraRandomSampleGenerator();

        imagem.setSaidaFinanceira(saidaFinanceiraBack);
        assertThat(imagem.getSaidaFinanceira()).isEqualTo(saidaFinanceiraBack);

        imagem.saidaFinanceira(null);
        assertThat(imagem.getSaidaFinanceira()).isNull();
    }

    @Test
    void entradaFinanceiraTest() throws Exception {
        Imagem imagem = getImagemRandomSampleGenerator();
        EntradaFinanceira entradaFinanceiraBack = getEntradaFinanceiraRandomSampleGenerator();

        imagem.setEntradaFinanceira(entradaFinanceiraBack);
        assertThat(imagem.getEntradaFinanceira()).isEqualTo(entradaFinanceiraBack);

        imagem.entradaFinanceira(null);
        assertThat(imagem.getEntradaFinanceira()).isNull();
    }
}
