package br.com.lisetech.cocoverde.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.lisetech.cocoverde.domain.Configuracao} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConfiguracaoDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfiguracaoDTO)) {
            return false;
        }

        ConfiguracaoDTO configuracaoDTO = (ConfiguracaoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, configuracaoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfiguracaoDTO{" +
            "id=" + getId() +
            "}";
    }
}
