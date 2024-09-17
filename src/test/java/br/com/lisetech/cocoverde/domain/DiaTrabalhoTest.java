package br.com.lisetech.cocoverde.domain;

import static br.com.lisetech.cocoverde.domain.DiaTrabalhoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DiaTrabalhoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiaTrabalho.class);
        DiaTrabalho diaTrabalho1 = getDiaTrabalhoSample1();
        DiaTrabalho diaTrabalho2 = new DiaTrabalho();
        assertThat(diaTrabalho1).isNotEqualTo(diaTrabalho2);

        diaTrabalho2.setId(diaTrabalho1.getId());
        assertThat(diaTrabalho1).isEqualTo(diaTrabalho2);

        diaTrabalho2 = getDiaTrabalhoSample2();
        assertThat(diaTrabalho1).isNotEqualTo(diaTrabalho2);
    }
}
