package br.com.lisetech.cocoverde.domain;

import static br.com.lisetech.cocoverde.domain.ClienteTestSamples.*;
import static br.com.lisetech.cocoverde.domain.EnderecoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClienteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cliente.class);
        Cliente cliente1 = getClienteSample1();
        Cliente cliente2 = new Cliente();
        assertThat(cliente1).isNotEqualTo(cliente2);

        cliente2.setId(cliente1.getId());
        assertThat(cliente1).isEqualTo(cliente2);

        cliente2 = getClienteSample2();
        assertThat(cliente1).isNotEqualTo(cliente2);
    }

    @Test
    void enderecoTest() throws Exception {
        Cliente cliente = getClienteRandomSampleGenerator();
        Endereco enderecoBack = getEnderecoRandomSampleGenerator();

        cliente.setEndereco(enderecoBack);
        assertThat(cliente.getEndereco()).isEqualTo(enderecoBack);
        assertThat(enderecoBack.getCliente()).isEqualTo(cliente);

        cliente.endereco(null);
        assertThat(cliente.getEndereco()).isNull();
        assertThat(enderecoBack.getCliente()).isNull();
    }
}
