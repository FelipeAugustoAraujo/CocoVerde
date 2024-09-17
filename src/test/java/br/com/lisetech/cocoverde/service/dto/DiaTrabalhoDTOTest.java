package br.com.lisetech.cocoverde.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DiaTrabalhoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiaTrabalhoDTO.class);
        DiaTrabalhoDTO diaTrabalhoDTO1 = new DiaTrabalhoDTO();
        diaTrabalhoDTO1.setId(1L);
        DiaTrabalhoDTO diaTrabalhoDTO2 = new DiaTrabalhoDTO();
        assertThat(diaTrabalhoDTO1).isNotEqualTo(diaTrabalhoDTO2);
        diaTrabalhoDTO2.setId(diaTrabalhoDTO1.getId());
        assertThat(diaTrabalhoDTO1).isEqualTo(diaTrabalhoDTO2);
        diaTrabalhoDTO2.setId(2L);
        assertThat(diaTrabalhoDTO1).isNotEqualTo(diaTrabalhoDTO2);
        diaTrabalhoDTO1.setId(null);
        assertThat(diaTrabalhoDTO1).isNotEqualTo(diaTrabalhoDTO2);
    }
}
