package br.com.lisetech.cocoverde.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.lisetech.cocoverde.domain.DetalhesSaidaFinanceira} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetalhesSaidaFinanceiraDTO implements Serializable {

    private Long id;

    private Integer quantidadeItem;

    private BigDecimal valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidadeItem() {
        return quantidadeItem;
    }

    public void setQuantidadeItem(Integer quantidadeItem) {
        this.quantidadeItem = quantidadeItem;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetalhesSaidaFinanceiraDTO)) {
            return false;
        }

        DetalhesSaidaFinanceiraDTO detalhesSaidaFinanceiraDTO = (DetalhesSaidaFinanceiraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, detalhesSaidaFinanceiraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetalhesSaidaFinanceiraDTO{" +
            "id=" + getId() +
            ", quantidadeItem=" + getQuantidadeItem() +
            ", valor=" + getValor() +
            "}";
    }
}
