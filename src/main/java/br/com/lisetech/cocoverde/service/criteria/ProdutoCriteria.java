package br.com.lisetech.cocoverde.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.lisetech.cocoverde.domain.Produto} entity. This class is used
 * in {@link br.com.lisetech.cocoverde.web.rest.ProdutoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /produtos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProdutoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter descricao;

    private StringFilter valorBase;

    private LongFilter estoqueId;

    private LongFilter frenteId;

    private LongFilter detalhesEntradaFinanceiraId;

    private LongFilter fornecedorId;

    private Boolean distinct;

    public ProdutoCriteria() {}

    public ProdutoCriteria(ProdutoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.valorBase = other.valorBase == null ? null : other.valorBase.copy();
        this.estoqueId = other.estoqueId == null ? null : other.estoqueId.copy();
        this.frenteId = other.frenteId == null ? null : other.frenteId.copy();
        this.detalhesEntradaFinanceiraId = other.detalhesEntradaFinanceiraId == null ? null : other.detalhesEntradaFinanceiraId.copy();
        this.fornecedorId = other.fornecedorId == null ? null : other.fornecedorId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProdutoCriteria copy() {
        return new ProdutoCriteria(this);
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

    public StringFilter getNome() {
        return nome;
    }

    public StringFilter nome() {
        if (nome == null) {
            nome = new StringFilter();
        }
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public StringFilter descricao() {
        if (descricao == null) {
            descricao = new StringFilter();
        }
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public StringFilter getValorBase() {
        return valorBase;
    }

    public StringFilter valorBase() {
        if (valorBase == null) {
            valorBase = new StringFilter();
        }
        return valorBase;
    }

    public void setValorBase(StringFilter valorBase) {
        this.valorBase = valorBase;
    }

    public LongFilter getEstoqueId() {
        return estoqueId;
    }

    public LongFilter estoqueId() {
        if (estoqueId == null) {
            estoqueId = new LongFilter();
        }
        return estoqueId;
    }

    public void setEstoqueId(LongFilter estoqueId) {
        this.estoqueId = estoqueId;
    }

    public LongFilter getFrenteId() {
        return frenteId;
    }

    public LongFilter frenteId() {
        if (frenteId == null) {
            frenteId = new LongFilter();
        }
        return frenteId;
    }

    public void setFrenteId(LongFilter frenteId) {
        this.frenteId = frenteId;
    }

    public LongFilter getDetalhesEntradaFinanceiraId() {
        return detalhesEntradaFinanceiraId;
    }

    public LongFilter detalhesEntradaFinanceiraId() {
        if (detalhesEntradaFinanceiraId == null) {
            detalhesEntradaFinanceiraId = new LongFilter();
        }
        return detalhesEntradaFinanceiraId;
    }

    public void setDetalhesEntradaFinanceiraId(LongFilter detalhesEntradaFinanceiraId) {
        this.detalhesEntradaFinanceiraId = detalhesEntradaFinanceiraId;
    }

    public LongFilter getFornecedorId() {
        return fornecedorId;
    }

    public LongFilter fornecedorId() {
        if (fornecedorId == null) {
            fornecedorId = new LongFilter();
        }
        return fornecedorId;
    }

    public void setFornecedorId(LongFilter fornecedorId) {
        this.fornecedorId = fornecedorId;
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
        final ProdutoCriteria that = (ProdutoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(valorBase, that.valorBase) &&
            Objects.equals(estoqueId, that.estoqueId) &&
            Objects.equals(frenteId, that.frenteId) &&
            Objects.equals(detalhesEntradaFinanceiraId, that.detalhesEntradaFinanceiraId) &&
            Objects.equals(fornecedorId, that.fornecedorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descricao, valorBase, estoqueId, frenteId, detalhesEntradaFinanceiraId, fornecedorId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProdutoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (valorBase != null ? "valorBase=" + valorBase + ", " : "") +
            (estoqueId != null ? "estoqueId=" + estoqueId + ", " : "") +
            (frenteId != null ? "frenteId=" + frenteId + ", " : "") +
            (detalhesEntradaFinanceiraId != null ? "detalhesEntradaFinanceiraId=" + detalhesEntradaFinanceiraId + ", " : "") +
            (fornecedorId != null ? "fornecedorId=" + fornecedorId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
