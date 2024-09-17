package br.com.lisetech.cocoverde.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.lisetech.cocoverde.domain.DetalhesSaidaFinanceira} entity. This class is used
 * in {@link br.com.lisetech.cocoverde.web.rest.DetalhesSaidaFinanceiraResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /detalhes-saida-financeiras?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetalhesSaidaFinanceiraCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter quantidadeItem;

    private BigDecimalFilter valor;

    private Boolean distinct;

    public DetalhesSaidaFinanceiraCriteria() {}

    public DetalhesSaidaFinanceiraCriteria(DetalhesSaidaFinanceiraCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.quantidadeItem = other.quantidadeItem == null ? null : other.quantidadeItem.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DetalhesSaidaFinanceiraCriteria copy() {
        return new DetalhesSaidaFinanceiraCriteria(this);
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
        final DetalhesSaidaFinanceiraCriteria that = (DetalhesSaidaFinanceiraCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(quantidadeItem, that.quantidadeItem) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantidadeItem, valor, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetalhesSaidaFinanceiraCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (quantidadeItem != null ? "quantidadeItem=" + quantidadeItem + ", " : "") +
            (valor != null ? "valor=" + valor + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
