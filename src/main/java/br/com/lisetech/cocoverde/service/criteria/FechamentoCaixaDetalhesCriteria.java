package br.com.lisetech.cocoverde.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.lisetech.cocoverde.domain.FechamentoCaixaDetalhes} entity. This class is used
 * in {@link br.com.lisetech.cocoverde.web.rest.FechamentoCaixaDetalhesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fechamento-caixa-detalhes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FechamentoCaixaDetalhesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter fechamentoCaixaId;

    private LongFilter entradaFinanceiraId;

    private LongFilter saidaFinanceiraId;

    private Boolean distinct;

    public FechamentoCaixaDetalhesCriteria() {}

    public FechamentoCaixaDetalhesCriteria(FechamentoCaixaDetalhesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fechamentoCaixaId = other.fechamentoCaixaId == null ? null : other.fechamentoCaixaId.copy();
        this.entradaFinanceiraId = other.entradaFinanceiraId == null ? null : other.entradaFinanceiraId.copy();
        this.saidaFinanceiraId = other.saidaFinanceiraId == null ? null : other.saidaFinanceiraId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FechamentoCaixaDetalhesCriteria copy() {
        return new FechamentoCaixaDetalhesCriteria(this);
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

    public LongFilter getFechamentoCaixaId() {
        return fechamentoCaixaId;
    }

    public LongFilter fechamentoCaixaId() {
        if (fechamentoCaixaId == null) {
            fechamentoCaixaId = new LongFilter();
        }
        return fechamentoCaixaId;
    }

    public void setFechamentoCaixaId(LongFilter fechamentoCaixaId) {
        this.fechamentoCaixaId = fechamentoCaixaId;
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
        final FechamentoCaixaDetalhesCriteria that = (FechamentoCaixaDetalhesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(fechamentoCaixaId, that.fechamentoCaixaId) &&
            Objects.equals(entradaFinanceiraId, that.entradaFinanceiraId) &&
            Objects.equals(saidaFinanceiraId, that.saidaFinanceiraId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fechamentoCaixaId, entradaFinanceiraId, saidaFinanceiraId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FechamentoCaixaDetalhesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (fechamentoCaixaId != null ? "fechamentoCaixaId=" + fechamentoCaixaId + ", " : "") +
            (entradaFinanceiraId != null ? "entradaFinanceiraId=" + entradaFinanceiraId + ", " : "") +
            (saidaFinanceiraId != null ? "saidaFinanceiraId=" + saidaFinanceiraId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
