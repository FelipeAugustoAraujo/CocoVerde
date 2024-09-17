package br.com.lisetech.cocoverde.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConfiguracaoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfiguracaoDTO.class);
        ConfiguracaoDTO configuracaoDTO1 = new ConfiguracaoDTO();
        configuracaoDTO1.setId(1L);
        ConfiguracaoDTO configuracaoDTO2 = new ConfiguracaoDTO();
        assertThat(configuracaoDTO1).isNotEqualTo(configuracaoDTO2);
        configuracaoDTO2.setId(configuracaoDTO1.getId());
        assertThat(configuracaoDTO1).isEqualTo(configuracaoDTO2);
        configuracaoDTO2.setId(2L);
        assertThat(configuracaoDTO1).isNotEqualTo(configuracaoDTO2);
        configuracaoDTO1.setId(null);
        assertThat(configuracaoDTO1).isNotEqualTo(configuracaoDTO2);
    }
}
