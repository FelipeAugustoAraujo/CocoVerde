package br.com.lisetech.cocoverde.domain;

import static br.com.lisetech.cocoverde.domain.EnderecoTestSamples.*;
import static br.com.lisetech.cocoverde.domain.FuncionarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FuncionarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Funcionario.class);
        Funcionario funcionario1 = getFuncionarioSample1();
        Funcionario funcionario2 = new Funcionario();
        assertThat(funcionario1).isNotEqualTo(funcionario2);

        funcionario2.setId(funcionario1.getId());
        assertThat(funcionario1).isEqualTo(funcionario2);

        funcionario2 = getFuncionarioSample2();
        assertThat(funcionario1).isNotEqualTo(funcionario2);
    }

    @Test
    void enderecoTest() throws Exception {
        Funcionario funcionario = getFuncionarioRandomSampleGenerator();
        Endereco enderecoBack = getEnderecoRandomSampleGenerator();

        funcionario.setEndereco(enderecoBack);
        assertThat(funcionario.getEndereco()).isEqualTo(enderecoBack);
        assertThat(enderecoBack.getFuncionario()).isEqualTo(funcionario);

        funcionario.endereco(null);
        assertThat(funcionario.getEndereco()).isNull();
        assertThat(enderecoBack.getFuncionario()).isNull();
    }
}
