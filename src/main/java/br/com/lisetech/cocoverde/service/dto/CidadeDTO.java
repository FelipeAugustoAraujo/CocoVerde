package br.com.lisetech.cocoverde.service.dto;

import br.com.lisetech.cocoverde.domain.enumeration.Estado;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.lisetech.cocoverde.domain.Cidade} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CidadeDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private Estado estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CidadeDTO)) {
            return false;
        }

        CidadeDTO cidadeDTO = (CidadeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cidadeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CidadeDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
