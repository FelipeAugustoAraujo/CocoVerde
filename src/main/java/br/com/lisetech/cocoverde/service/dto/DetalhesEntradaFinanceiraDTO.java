package br.com.lisetech.cocoverde.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.lisetech.cocoverde.domain.DetalhesEntradaFinanceira} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetalhesEntradaFinanceiraDTO implements Serializable {

    private Long id;

    private Integer quantidadeItem;

    private BigDecimal valor;

    private ProdutoDTO produto;

    private EntradaFinanceiraDTO entradaFinanceira;

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

    public ProdutoDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoDTO produto) {
        this.produto = produto;
    }

    public EntradaFinanceiraDTO getEntradaFinanceira() {
        return entradaFinanceira;
    }

    public void setEntradaFinanceira(EntradaFinanceiraDTO entradaFinanceira) {
        this.entradaFinanceira = entradaFinanceira;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetalhesEntradaFinanceiraDTO)) {
            return false;
        }

        DetalhesEntradaFinanceiraDTO detalhesEntradaFinanceiraDTO = (DetalhesEntradaFinanceiraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, detalhesEntradaFinanceiraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetalhesEntradaFinanceiraDTO{" +
            "id=" + getId() +
            ", quantidadeItem=" + getQuantidadeItem() +
            ", valor=" + getValor() +
            ", produto=" + getProduto() +
            ", entradaFinanceira=" + getEntradaFinanceira() +
            "}";
    }
}
