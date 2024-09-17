package br.com.lisetech.cocoverde.service.criteria;

import br.com.lisetech.cocoverde.domain.enumeration.MetodoPagamento;
import br.com.lisetech.cocoverde.domain.enumeration.ResponsavelPagamento;
import br.com.lisetech.cocoverde.domain.enumeration.StatusPagamento;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.lisetech.cocoverde.domain.SaidaFinanceira} entity. This class is used
 * in {@link br.com.lisetech.cocoverde.web.rest.SaidaFinanceiraResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /saida-financeiras?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaidaFinanceiraCriteria implements Serializable, Criteria {

    /**
     * Class for filtering MetodoPagamento
     */
    public static class MetodoPagamentoFilter extends Filter<MetodoPagamento> {

        public MetodoPagamentoFilter() {}

        public MetodoPagamentoFilter(MetodoPagamentoFilter filter) {
            super(filter);
        }

        @Override
        public MetodoPagamentoFilter copy() {
            return new MetodoPagamentoFilter(this);
        }
    }

    /**
     * Class for filtering StatusPagamento
     */
    public static class StatusPagamentoFilter extends Filter<StatusPagamento> {

        public StatusPagamentoFilter() {}

        public StatusPagamentoFilter(StatusPagamentoFilter filter) {
            super(filter);
        }

        @Override
        public StatusPagamentoFilter copy() {
            return new StatusPagamentoFilter(this);
        }
    }

    /**
     * Class for filtering ResponsavelPagamento
     */
    public static class ResponsavelPagamentoFilter extends Filter<ResponsavelPagamento> {

        public ResponsavelPagamentoFilter() {}

        public ResponsavelPagamentoFilter(ResponsavelPagamentoFilter filter) {
            super(filter);
        }

        @Override
        public ResponsavelPagamentoFilter copy() {
            return new ResponsavelPagamentoFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter data;

    private BigDecimalFilter valorTotal;

    private StringFilter descricao;

    private MetodoPagamentoFilter metodoPagamento;

    private StatusPagamentoFilter statusPagamento;

    private ResponsavelPagamentoFilter responsavelPagamento;

    private LongFilter estoqueId;

    private LongFilter frenteId;

    private LongFilter fechamentoCaixaDetalhesId;

    private LongFilter imagemId;

    private Boolean distinct;

    public SaidaFinanceiraCriteria() {}

    public SaidaFinanceiraCriteria(SaidaFinanceiraCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.valorTotal = other.valorTotal == null ? null : other.valorTotal.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.metodoPagamento = other.metodoPagamento == null ? null : other.metodoPagamento.copy();
        this.statusPagamento = other.statusPagamento == null ? null : other.statusPagamento.copy();
        this.responsavelPagamento = other.responsavelPagamento == null ? null : other.responsavelPagamento.copy();
        this.estoqueId = other.estoqueId == null ? null : other.estoqueId.copy();
        this.frenteId = other.frenteId == null ? null : other.frenteId.copy();
        this.fechamentoCaixaDetalhesId = other.fechamentoCaixaDetalhesId == null ? null : other.fechamentoCaixaDetalhesId.copy();
        this.imagemId = other.imagemId == null ? null : other.imagemId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SaidaFinanceiraCriteria copy() {
        return new SaidaFinanceiraCriteria(this);
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

    public ZonedDateTimeFilter getData() {
        return data;
    }

    public ZonedDateTimeFilter data() {
        if (data == null) {
            data = new ZonedDateTimeFilter();
        }
        return data;
    }

    public void setData(ZonedDateTimeFilter data) {
        this.data = data;
    }

    public BigDecimalFilter getValorTotal() {
        return valorTotal;
    }

    public BigDecimalFilter valorTotal() {
        if (valorTotal == null) {
            valorTotal = new BigDecimalFilter();
        }
        return valorTotal;
    }

    public void setValorTotal(BigDecimalFilter valorTotal) {
        this.valorTotal = valorTotal;
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

    public MetodoPagamentoFilter getMetodoPagamento() {
        return metodoPagamento;
    }

    public MetodoPagamentoFilter metodoPagamento() {
        if (metodoPagamento == null) {
            metodoPagamento = new MetodoPagamentoFilter();
        }
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamentoFilter metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public StatusPagamentoFilter getStatusPagamento() {
        return statusPagamento;
    }

    public StatusPagamentoFilter statusPagamento() {
        if (statusPagamento == null) {
            statusPagamento = new StatusPagamentoFilter();
        }
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamentoFilter statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public ResponsavelPagamentoFilter getResponsavelPagamento() {
        return responsavelPagamento;
    }

    public ResponsavelPagamentoFilter responsavelPagamento() {
        if (responsavelPagamento == null) {
            responsavelPagamento = new ResponsavelPagamentoFilter();
        }
        return responsavelPagamento;
    }

    public void setResponsavelPagamento(ResponsavelPagamentoFilter responsavelPagamento) {
        this.responsavelPagamento = responsavelPagamento;
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

    public LongFilter getFechamentoCaixaDetalhesId() {
        return fechamentoCaixaDetalhesId;
    }

    public LongFilter fechamentoCaixaDetalhesId() {
        if (fechamentoCaixaDetalhesId == null) {
            fechamentoCaixaDetalhesId = new LongFilter();
        }
        return fechamentoCaixaDetalhesId;
    }

    public void setFechamentoCaixaDetalhesId(LongFilter fechamentoCaixaDetalhesId) {
        this.fechamentoCaixaDetalhesId = fechamentoCaixaDetalhesId;
    }

    public LongFilter getImagemId() {
        return imagemId;
    }

    public LongFilter imagemId() {
        if (imagemId == null) {
            imagemId = new LongFilter();
        }
        return imagemId;
    }

    public void setImagemId(LongFilter imagemId) {
        this.imagemId = imagemId;
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
        final SaidaFinanceiraCriteria that = (SaidaFinanceiraCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(data, that.data) &&
            Objects.equals(valorTotal, that.valorTotal) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(metodoPagamento, that.metodoPagamento) &&
            Objects.equals(statusPagamento, that.statusPagamento) &&
            Objects.equals(responsavelPagamento, that.responsavelPagamento) &&
            Objects.equals(estoqueId, that.estoqueId) &&
            Objects.equals(frenteId, that.frenteId) &&
            Objects.equals(fechamentoCaixaDetalhesId, that.fechamentoCaixaDetalhesId) &&
            Objects.equals(imagemId, that.imagemId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            data,
            valorTotal,
            descricao,
            metodoPagamento,
            statusPagamento,
            responsavelPagamento,
            estoqueId,
            frenteId,
            fechamentoCaixaDetalhesId,
            imagemId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaidaFinanceiraCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (data != null ? "data=" + data + ", " : "") +
            (valorTotal != null ? "valorTotal=" + valorTotal + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (metodoPagamento != null ? "metodoPagamento=" + metodoPagamento + ", " : "") +
            (statusPagamento != null ? "statusPagamento=" + statusPagamento + ", " : "") +
            (responsavelPagamento != null ? "responsavelPagamento=" + responsavelPagamento + ", " : "") +
            (estoqueId != null ? "estoqueId=" + estoqueId + ", " : "") +
            (frenteId != null ? "frenteId=" + frenteId + ", " : "") +
            (fechamentoCaixaDetalhesId != null ? "fechamentoCaixaDetalhesId=" + fechamentoCaixaDetalhesId + ", " : "") +
            (imagemId != null ? "imagemId=" + imagemId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
