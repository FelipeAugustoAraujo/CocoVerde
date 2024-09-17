package br.com.lisetech.cocoverde.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FechamentoCaixaDetalhesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FechamentoCaixaDetalhesDTO.class);
        FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhesDTO1 = new FechamentoCaixaDetalhesDTO();
        fechamentoCaixaDetalhesDTO1.setId(1L);
        FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhesDTO2 = new FechamentoCaixaDetalhesDTO();
        assertThat(fechamentoCaixaDetalhesDTO1).isNotEqualTo(fechamentoCaixaDetalhesDTO2);
        fechamentoCaixaDetalhesDTO2.setId(fechamentoCaixaDetalhesDTO1.getId());
        assertThat(fechamentoCaixaDetalhesDTO1).isEqualTo(fechamentoCaixaDetalhesDTO2);
        fechamentoCaixaDetalhesDTO2.setId(2L);
        assertThat(fechamentoCaixaDetalhesDTO1).isNotEqualTo(fechamentoCaixaDetalhesDTO2);
        fechamentoCaixaDetalhesDTO1.setId(null);
        assertThat(fechamentoCaixaDetalhesDTO1).isNotEqualTo(fechamentoCaixaDetalhesDTO2);
    }
}
