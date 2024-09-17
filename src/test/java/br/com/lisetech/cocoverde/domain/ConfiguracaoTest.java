package br.com.lisetech.cocoverde.domain;

import static br.com.lisetech.cocoverde.domain.ConfiguracaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConfiguracaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Configuracao.class);
        Configuracao configuracao1 = getConfiguracaoSample1();
        Configuracao configuracao2 = new Configuracao();
        assertThat(configuracao1).isNotEqualTo(configuracao2);

        configuracao2.setId(configuracao1.getId());
        assertThat(configuracao1).isEqualTo(configuracao2);

        configuracao2 = getConfiguracaoSample2();
        assertThat(configuracao1).isNotEqualTo(configuracao2);
    }
}
