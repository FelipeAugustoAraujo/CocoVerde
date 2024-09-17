package br.com.lisetech.cocoverde.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.lisetech.cocoverde.domain.DetalhesEntradaFinanceira} entity. This class is used
 * in {@link br.com.lisetech.cocoverde.web.rest.DetalhesEntradaFinanceiraResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /detalhes-entrada-financeiras?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetalhesEntradaFinanceiraCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter quantidadeItem;

    private BigDecimalFilter valor;

    private LongFilter produtoId;

    private LongFilter entradaFinanceiraId;

    private Boolean distinct;

    public DetalhesEntradaFinanceiraCriteria() {}

    public DetalhesEntradaFinanceiraCriteria(DetalhesEntradaFinanceiraCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.quantidadeItem = other.quantidadeItem == null ? null : other.quantidadeItem.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.produtoId = other.produtoId == null ? null : other.produtoId.copy();
        this.entradaFinanceiraId = other.entradaFinanceiraId == null ? null : other.entradaFinanceiraId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DetalhesEntradaFinanceiraCriteria copy() {
        return new DetalhesEntradaFinanceiraCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getQuantidadeItem() {
        return quantidadeItem;
    }

    public IntegerFilter quantidadeItem() {
        if (quantidadeItem == null) {
            quantidadeItem = new IntegerFilter();
        }
        return quantidadeItem;
    }

    public void setQuantidadeItem(IntegerFilter quantidadeItem) {
        this.quantidadeItem = quantidadeItem;
    }

    public BigDecimalFilter getValor() {
        return valor;
    }

    public BigDecimalFilter valor() {
        if (valor == null) {
            valor = new BigDecimalFilter();
        }
        return valor;
    }

    public void setValor(BigDecimalFilter valor) {
        this.valor = valor;
    }

    public LongFilter getProdutoId() {
        return produtoId;
    }

    public LongFilter produtoId() {
        if (produtoId == null) {
            produtoId = new LongFilter();
        }
        return produtoId;
    }

    public void setProdutoId(LongFilter produtoId) {
        this.produtoId = produtoId;
    }

    public LongFilter getEntradaFinanceiraId() {
        return entradaFinanceiraId;
    }

    public LongFilter entradaFinanceiraId() {
        if (entradaFinanceiraId == null) {
            entradaFinanceiraId = new LongFilter();
        }
        return entradaFinanceiraId;
    }

    public void setEntradaFinanceiraId(LongFilter entradaFinanceiraId) {
        this.entradaFinanceiraId = entradaFinanceiraId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DetalhesEntradaFinanceiraCriteria that = (DetalhesEntradaFinanceiraCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(quantidadeItem, that.quantidadeItem) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(produtoId, that.produtoId) &&
            Objects.equals(entradaFinanceiraId, that.entradaFinanceiraId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantidadeItem, valor, produtoId, entradaFinanceiraId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetalhesEntradaFinanceiraCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (quantidadeItem != null ? "quantidadeItem=" + quantidadeItem + ", " : "") +
            (valor != null ? "valor=" + valor + ", " : "") +
            (produtoId != null ? "produtoId=" + produtoId + ", " : "") +
            (entradaFinanceiraId != null ? "entradaFinanceiraId=" + entradaFinanceiraId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
