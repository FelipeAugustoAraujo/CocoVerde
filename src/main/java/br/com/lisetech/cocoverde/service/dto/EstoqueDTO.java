package br.com.lisetech.cocoverde.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.lisetech.cocoverde.domain.Estoque} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstoqueDTO implements Serializable {

    private Long id;

    private Integer quantidade;

    private Instant criadoEm;

    private Instant modificadoEm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Instant getModificadoEm() {
        return modificadoEm;
    }

    public void setModificadoEm(Instant modificadoEm) {
        this.modificadoEm = modificadoEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstoqueDTO)) {
            return false;
        }

        EstoqueDTO estoqueDTO = (EstoqueDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, estoqueDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstoqueDTO{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", criadoEm='" + getCriadoEm() + "'" +
            ", modificadoEm='" + getModificadoEm() + "'" +
            "}";
    }
}
