package br.com.lisetech.cocoverde.domain;

import static br.com.lisetech.cocoverde.domain.CidadeTestSamples.*;
import static br.com.lisetech.cocoverde.domain.EnderecoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CidadeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cidade.class);
        Cidade cidade1 = getCidadeSample1();
        Cidade cidade2 = new Cidade();
        assertThat(cidade1).isNotEqualTo(cidade2);

        cidade2.setId(cidade1.getId());
        assertThat(cidade1).isEqualTo(cidade2);

        cidade2 = getCidadeSample2();
        assertThat(cidade1).isNotEqualTo(cidade2);
    }

    @Test
    void enderecoTest() throws Exception {
        Cidade cidade = getCidadeRandomSampleGenerator();
        Endereco enderecoBack = getEnderecoRandomSampleGenerator();

        cidade.addEndereco(enderecoBack);
        assertThat(cidade.getEnderecos()).containsOnly(enderecoBack);
        assertThat(enderecoBack.getCidade()).isEqualTo(cidade);

        cidade.removeEndereco(enderecoBack);
        assertThat(cidade.getEnderecos()).doesNotContain(enderecoBack);
        assertThat(enderecoBack.getCidade()).isNull();

        cidade.enderecos(new HashSet<>(Set.of(enderecoBack)));
        assertThat(cidade.getEnderecos()).containsOnly(enderecoBack);
        assertThat(enderecoBack.getCidade()).isEqualTo(cidade);

        cidade.setEnderecos(new HashSet<>());
        assertThat(cidade.getEnderecos()).doesNotContain(enderecoBack);
        assertThat(enderecoBack.getCidade()).isNull();
    }
}
