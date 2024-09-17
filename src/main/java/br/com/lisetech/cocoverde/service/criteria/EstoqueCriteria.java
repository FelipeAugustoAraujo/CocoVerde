package br.com.lisetech.cocoverde.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.lisetech.cocoverde.domain.Estoque} entity. This class is used
 * in {@link br.com.lisetech.cocoverde.web.rest.EstoqueResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /estoques?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstoqueCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter quantidade;

    private InstantFilter criadoEm;

    private InstantFilter modificadoEm;

    private LongFilter produtoId;

    private LongFilter entradaFinanceiraId;

    private LongFilter saidaFinanceiraId;

    private Boolean distinct;

    public EstoqueCriteria() {}

    public EstoqueCriteria(EstoqueCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.quantidade = other.quantidade == null ? null : other.quantidade.copy();
        this.criadoEm = other.criadoEm == null ? null : other.criadoEm.copy();
        this.modificadoEm = other.modificadoEm == null ? null : other.modificadoEm.copy();
        this.produtoId = other.produtoId == null ? null : other.produtoId.copy();
        this.entradaFinanceiraId = other.entradaFinanceiraId == null ? null : other.entradaFinanceiraId.copy();
        this.saidaFinanceiraId = other.saidaFinanceiraId == null ? null : other.saidaFinanceiraId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EstoqueCriteria copy() {
        return new EstoqueCriteria(this);
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

    public IntegerFilter getQuantidade() {
        return quantidade;
    }

    public IntegerFilter quantidade() {
        if (quantidade == null) {
            quantidade = new IntegerFilter();
        }
        return quantidade;
    }

    public void setQuantidade(IntegerFilter quantidade) {
        this.quantidade = quantidade;
    }

    public InstantFilter getCriadoEm() {
        return criadoEm;
    }

    public InstantFilter criadoEm() {
        if (criadoEm == null) {
            criadoEm = new InstantFilter();
        }
        return criadoEm;
    }

    public void setCriadoEm(InstantFilter criadoEm) {
        this.criadoEm = criadoEm;
    }

    public InstantFilter getModificadoEm() {
        return modificadoEm;
    }

    public InstantFilter modificadoEm() {
        if (modificadoEm == null) {
            modificadoEm = new InstantFilter();
        }
        return modificadoEm;
    }

    public void setModificadoEm(InstantFilter modificadoEm) {
        this.modificadoEm = modificadoEm;
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

    public LongFilter getSaidaFinanceiraId() {
        return saidaFinanceiraId;
    }

    public LongFilter saidaFinanceiraId() {
        if (saidaFinanceiraId == null) {
            saidaFinanceiraId = new LongFilter();
        }
        return saidaFinanceiraId;
    }

    public void setSaidaFinanceiraId(LongFilter saidaFinanceiraId) {
        this.saidaFinanceiraId = saidaFinanceiraId;
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
        final EstoqueCriteria that = (EstoqueCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(quantidade, that.quantidade) &&
            Objects.equals(criadoEm, that.criadoEm) &&
            Objects.equals(modificadoEm, that.modificadoEm) &&
            Objects.equals(produtoId, that.produtoId) &&
            Objects.equals(entradaFinanceiraId, that.entradaFinanceiraId) &&
            Objects.equals(saidaFinanceiraId, that.saidaFinanceiraId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantidade, criadoEm, modificadoEm, produtoId, entradaFinanceiraId, saidaFinanceiraId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstoqueCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (quantidade != null ? "quantidade=" + quantidade + ", " : "") +
            (criadoEm != null ? "criadoEm=" + criadoEm + ", " : "") +
            (modificadoEm != null ? "modificadoEm=" + modificadoEm + ", " : "") +
            (produtoId != null ? "produtoId=" + produtoId + ", " : "") +
            (entradaFinanceiraId != null ? "entradaFinanceiraId=" + entradaFinanceiraId + ", " : "") +
            (saidaFinanceiraId != null ? "saidaFinanceiraId=" + saidaFinanceiraId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
