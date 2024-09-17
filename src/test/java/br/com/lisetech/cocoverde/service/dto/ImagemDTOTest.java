package br.com.lisetech.cocoverde.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.lisetech.cocoverde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ImagemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImagemDTO.class);
        ImagemDTO imagemDTO1 = new ImagemDTO();
        imagemDTO1.setId(1L);
        ImagemDTO imagemDTO2 = new ImagemDTO();
        assertThat(imagemDTO1).isNotEqualTo(imagemDTO2);
        imagemDTO2.setId(imagemDTO1.getId());
        assertThat(imagemDTO1).isEqualTo(imagemDTO2);
        imagemDTO2.setId(2L);
        assertThat(imagemDTO1).isNotEqualTo(imagemDTO2);
        imagemDTO1.setId(null);
        assertThat(imagemDTO1).isNotEqualTo(imagemDTO2);
    }
}
