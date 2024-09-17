package br.com.lisetech.cocoverde.domain;

import static br.com.lisetech.cocoverde.domain.CidadeTestSamples.*;
import static br.com.lisetech.cocoverde.domain.ClienteTestSamples.*;
import static br.com.lisetech.cocoverde.domain.EnderecoTestSamples.*;
import static br.com.lisetech.cocoverde.domain.FornecedorTestSamples.*;
import static br.com.lisetech.cocoverde.domain.FuncionarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnderecoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Endereco.class);
        Endereco endereco1 = getEnderecoSample1();
        Endereco endereco2 = new Endereco();
        assertThat(endereco1).isNotEqualTo(endereco2);

        endereco2.setId(endereco1.getId());
        assertThat(endereco1).isEqualTo(endereco2);

        endereco2 = getEnderecoSample2();
        assertThat(endereco1).isNotEqualTo(endereco2);
    }

    @Test
    void fornecedorTest() throws Exception {
        Endereco endereco = getEnderecoRandomSampleGenerator();
        Fornecedor fornecedorBack = getFornecedorRandomSampleGenerator();

        endereco.setFornecedor(fornecedorBack);
        assertThat(endereco.getFornecedor()).isEqualTo(fornecedorBack);

        endereco.fornecedor(null);
        assertThat(endereco.getFornecedor()).isNull();
    }

    @Test
    void funcionarioTest() throws Exception {
        Endereco endereco = getEnderecoRandomSampleGenerator();
        Funcionario funcionarioBack = getFuncionarioRandomSampleGenerator();

        endereco.setFuncionario(funcionarioBack);
        assertThat(endereco.getFuncionario()).isEqualTo(funcionarioBack);

        endereco.funcionario(null);
        assertThat(endereco.getFuncionario()).isNull();
    }

    @Test
    void clienteTest() throws Exception {
        Endereco endereco = getEnderecoRandomSampleGenerator();
        Cliente clienteBack = getClienteRandomSampleGenerator();

        endereco.setCliente(clienteBack);
        assertThat(endereco.getCliente()).isEqualTo(clienteBack);

        endereco.cliente(null);
        assertThat(endereco.getCliente()).isNull();
    }

    @Test
    void cidadeTest() throws Exception {
        Endereco endereco = getEnderecoRandomSampleGenerator();
        Cidade cidadeBack = getCidadeRandomSampleGenerator();

        endereco.setCidade(cidadeBack);
        assertThat(endereco.getCidade()).isEqualTo(cidadeBack);

        endereco.cidade(null);
        assertThat(endereco.getCidade()).isNull();
    }
}
