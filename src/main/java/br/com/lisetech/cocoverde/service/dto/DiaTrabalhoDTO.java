package br.com.lisetech.cocoverde.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.lisetech.cocoverde.domain.DiaTrabalho} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DiaTrabalhoDTO implements Serializable {

    private Long id;

    private Instant data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getData() {
        return data;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiaTrabalhoDTO)) {
            return false;
        }

        DiaTrabalhoDTO diaTrabalhoDTO = (DiaTrabalhoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, diaTrabalhoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiaTrabalhoDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            "}";
    }
}
