package br.com.lisetech.cocoverde.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.lisetech.cocoverde.domain.FechamentoCaixa} entity. This class is used
 * in {@link br.com.lisetech.cocoverde.web.rest.FechamentoCaixaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fechamento-caixas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FechamentoCaixaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter dataInicial;

    private ZonedDateTimeFilter dataFinal;

    private IntegerFilter quantidadeCocosPerdidos;

    private IntegerFilter quantidadeCocosVendidos;

    private IntegerFilter quantidadeCocoSobrou;

    private IntegerFilter divididoPor;

    private BigDecimalFilter valorTotalCoco;

    private BigDecimalFilter valorTotalCocoPerdido;

    private BigDecimalFilter valorPorPessoa;

    private BigDecimalFilter valorDespesas;

    private BigDecimalFilter valorDinheiro;

    private BigDecimalFilter valorCartao;

    private BigDecimalFilter valorTotal;

    private LongFilter fechamentoCaixaDetalhesId;

    private Boolean distinct;

    public FechamentoCaixaCriteria() {}

    public FechamentoCaixaCriteria(FechamentoCaixaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dataInicial = other.dataInicial == null ? null : other.dataInicial.copy();
        this.dataFinal = other.dataFinal == null ? null : other.dataFinal.copy();
        this.quantidadeCocosPerdidos = other.quantidadeCocosPerdidos == null ? null : other.quantidadeCocosPerdidos.copy();
        this.quantidadeCocosVendidos = other.quantidadeCocosVendidos == null ? null : other.quantidadeCocosVendidos.copy();
        this.quantidadeCocoSobrou = other.quantidadeCocoSobrou == null ? null : other.quantidadeCocoSobrou.copy();
        this.divididoPor = other.divididoPor == null ? null : other.divididoPor.copy();
        this.valorTotalCoco = other.valorTotalCoco == null ? null : other.valorTotalCoco.copy();
        this.valorTotalCocoPerdido = other.valorTotalCocoPerdido == null ? null : other.valorTotalCocoPerdido.copy();
        this.valorPorPessoa = other.valorPorPessoa == null ? null : other.valorPorPessoa.copy();
        this.valorDespesas = other.valorDespesas == null ? null : other.valorDespesas.copy();
        this.valorDinheiro = other.valorDinheiro == null ? null : other.valorDinheiro.copy();
        this.valorCartao = other.valorCartao == null ? null : other.valorCartao.copy();
        this.valorTotal = other.valorTotal == null ? null : other.valorTotal.copy();
        this.fechamentoCaixaDetalhesId = other.fechamentoCaixaDetalhesId == null ? null : other.fechamentoCaixaDetalhesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FechamentoCaixaCriteria copy() {
        return new FechamentoCaixaCriteria(this);
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

    public ZonedDateTimeFilter getDataInicial() {
        return dataInicial;
    }

    public ZonedDateTimeFilter dataInicial() {
        if (dataInicial == null) {
            dataInicial = new ZonedDateTimeFilter();
        }
        return dataInicial;
    }

    public void setDataInicial(ZonedDateTimeFilter dataInicial) {
        this.dataInicial = dataInicial;
    }

    public ZonedDateTimeFilter getDataFinal() {
        return dataFinal;
    }

    public ZonedDateTimeFilter dataFinal() {
        if (dataFinal == null) {
            dataFinal = new ZonedDateTimeFilter();
        }
        return dataFinal;
    }

    public void setDataFinal(ZonedDateTimeFilter dataFinal) {
        this.dataFinal = dataFinal;
    }

    public IntegerFilter getQuantidadeCocosPerdidos() {
        return quantidadeCocosPerdidos;
    }

    public IntegerFilter quantidadeCocosPerdidos() {
        if (quantidadeCocosPerdidos == null) {
            quantidadeCocosPerdidos = new IntegerFilter();
        }
        return quantidadeCocosPerdidos;
    }

    public void setQuantidadeCocosPerdidos(IntegerFilter quantidadeCocosPerdidos) {
        this.quantidadeCocosPerdidos = quantidadeCocosPerdidos;
    }

    public IntegerFilter getQuantidadeCocosVendidos() {
        return quantidadeCocosVendidos;
    }

    public IntegerFilter quantidadeCocosVendidos() {
        if (quantidadeCocosVendidos == null) {
            quantidadeCocosVendidos = new IntegerFilter();
        }
        return quantidadeCocosVendidos;
    }

    public void setQuantidadeCocosVendidos(IntegerFilter quantidadeCocosVendidos) {
        this.quantidadeCocosVendidos = quantidadeCocosVendidos;
    }

    public IntegerFilter getQuantidadeCocoSobrou() {
        return quantidadeCocoSobrou;
    }

    public IntegerFilter quantidadeCocoSobrou() {
        if (quantidadeCocoSobrou == null) {
            quantidadeCocoSobrou = new IntegerFilter();
        }
        return quantidadeCocoSobrou;
    }

    public void setQuantidadeCocoSobrou(IntegerFilter quantidadeCocoSobrou) {
        this.quantidadeCocoSobrou = quantidadeCocoSobrou;
    }

    public IntegerFilter getDivididoPor() {
        return divididoPor;
    }

    public IntegerFilter divididoPor() {
        if (divididoPor == null) {
            divididoPor = new IntegerFilter();
        }
        return divididoPor;
    }

    public void setDivididoPor(IntegerFilter divididoPor) {
        this.divididoPor = divididoPor;
    }

    public BigDecimalFilter getValorTotalCoco() {
        return valorTotalCoco;
    }

    public BigDecimalFilter valorTotalCoco() {
        if (valorTotalCoco == null) {
            valorTotalCoco = new BigDecimalFilter();
        }
        return valorTotalCoco;
    }

    public void setValorTotalCoco(BigDecimalFilter valorTotalCoco) {
        this.valorTotalCoco = valorTotalCoco;
    }

    public BigDecimalFilter getValorTotalCocoPerdido() {
        return valorTotalCocoPerdido;
    }

    public BigDecimalFilter valorTotalCocoPerdido() {
        if (valorTotalCocoPerdido == null) {
            valorTotalCocoPerdido = new BigDecimalFilter();
        }
        return valorTotalCocoPerdido;
    }

    public void setValorTotalCocoPerdido(BigDecimalFilter valorTotalCocoPerdido) {
        this.valorTotalCocoPerdido = valorTotalCocoPerdido;
    }

    public BigDecimalFilter getValorPorPessoa() {
        return valorPorPessoa;
    }

    public BigDecimalFilter valorPorPessoa() {
        if (valorPorPessoa == null) {
            valorPorPessoa = new BigDecimalFilter();
        }
        return valorPorPessoa;
    }

    public void setValorPorPessoa(BigDecimalFilter valorPorPessoa) {
        this.valorPorPessoa = valorPorPessoa;
    }

    public BigDecimalFilter getValorDespesas() {
        return valorDespesas;
    }

    public BigDecimalFilter valorDespesas() {
        if (valorDespesas == null) {
            valorDespesas = new BigDecimalFilter();
        }
        return valorDespesas;
    }

    public void setValorDespesas(BigDecimalFilter valorDespesas) {
        this.valorDespesas = valorDespesas;
    }

    public BigDecimalFilter getValorDinheiro() {
        return valorDinheiro;
    }

    public BigDecimalFilter valorDinheiro() {
        if (valorDinheiro == null) {
            valorDinheiro = new BigDecimalFilter();
        }
        return valorDinheiro;
    }

    public void setValorDinheiro(BigDecimalFilter valorDinheiro) {
        this.valorDinheiro = valorDinheiro;
    }

    public BigDecimalFilter getValorCartao() {
        return valorCartao;
    }

    public BigDecimalFilter valorCartao() {
        if (valorCartao == null) {
            valorCartao = new BigDecimalFilter();
        }
        return valorCartao;
    }

    public void setValorCartao(BigDecimalFilter valorCartao) {
        this.valorCartao = valorCartao;
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
        final FechamentoCaixaCriteria that = (FechamentoCaixaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dataInicial, that.dataInicial) &&
            Objects.equals(dataFinal, that.dataFinal) &&
            Objects.equals(quantidadeCocosPerdidos, that.quantidadeCocosPerdidos) &&
            Objects.equals(quantidadeCocosVendidos, that.quantidadeCocosVendidos) &&
            Objects.equals(quantidadeCocoSobrou, that.quantidadeCocoSobrou) &&
            Objects.equals(divididoPor, that.divididoPor) &&
            Objects.equals(valorTotalCoco, that.valorTotalCoco) &&
            Objects.equals(valorTotalCocoPerdido, that.valorTotalCocoPerdido) &&
            Objects.equals(valorPorPessoa, that.valorPorPessoa) &&
            Objects.equals(valorDespesas, that.valorDespesas) &&
            Objects.equals(valorDinheiro, that.valorDinheiro) &&
            Objects.equals(valorCartao, that.valorCartao) &&
            Objects.equals(valorTotal, that.valorTotal) &&
            Objects.equals(fechamentoCaixaDetalhesId, that.fechamentoCaixaDetalhesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            dataInicial,
            dataFinal,
            quantidadeCocosPerdidos,
            quantidadeCocosVendidos,
            quantidadeCocoSobrou,
            divididoPor,
            valorTotalCoco,
            valorTotalCocoPerdido,
            valorPorPessoa,
            valorDespesas,
            valorDinheiro,
            valorCartao,
            valorTotal,
            fechamentoCaixaDetalhesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FechamentoCaixaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dataInicial != null ? "dataInicial=" + dataInicial + ", " : "") +
            (dataFinal != null ? "dataFinal=" + dataFinal + ", " : "") +
            (quantidadeCocosPerdidos != null ? "quantidadeCocosPerdidos=" + quantidadeCocosPerdidos + ", " : "") +
            (quantidadeCocosVendidos != null ? "quantidadeCocosVendidos=" + quantidadeCocosVendidos + ", " : "") +
            (quantidadeCocoSobrou != null ? "quantidadeCocoSobrou=" + quantidadeCocoSobrou + ", " : "") +
            (divididoPor != null ? "divididoPor=" + divididoPor + ", " : "") +
            (valorTotalCoco != null ? "valorTotalCoco=" + valorTotalCoco + ", " : "") +
            (valorTotalCocoPerdido != null ? "valorTotalCocoPerdido=" + valorTotalCocoPerdido + ", " : "") +
            (valorPorPessoa != null ? "valorPorPessoa=" + valorPorPessoa + ", " : "") +
            (valorDespesas != null ? "valorDespesas=" + valorDespesas + ", " : "") +
            (valorDinheiro != null ? "valorDinheiro=" + valorDinheiro + ", " : "") +
            (valorCartao != null ? "valorCartao=" + valorCartao + ", " : "") +
            (valorTotal != null ? "valorTotal=" + valorTotal + ", " : "") +
            (fechamentoCaixaDetalhesId != null ? "fechamentoCaixaDetalhesId=" + fechamentoCaixaDetalhesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
