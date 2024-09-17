package br.com.lisetech.cocoverde.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.lisetech.cocoverde.domain.FechamentoCaixaDetalhes} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FechamentoCaixaDetalhesDTO implements Serializable {

    private Long id;

    private FechamentoCaixaDTO fechamentoCaixa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FechamentoCaixaDTO getFechamentoCaixa() {
        return fechamentoCaixa;
    }

    public void setFechamentoCaixa(FechamentoCaixaDTO fechamentoCaixa) {
        this.fechamentoCaixa = fechamentoCaixa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FechamentoCaixaDetalhesDTO)) {
            return false;
        }

        FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhesDTO = (FechamentoCaixaDetalhesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fechamentoCaixaDetalhesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FechamentoCaixaDetalhesDTO{" +
            "id=" + getId() +
            ", fechamentoCaixa=" + getFechamentoCaixa() +
            "}";
    }
}
