package br.com.lisetech.cocoverde.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.lisetech.cocoverde.domain.FechamentoCaixa} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FechamentoCaixaDTO implements Serializable {

    private Long id;

    private ZonedDateTime dataInicial;

    private ZonedDateTime dataFinal;

    private Integer quantidadeCocosPerdidos;

    private Integer quantidadeCocosVendidos;

    private Integer quantidadeCocoSobrou;

    private Integer divididoPor;

    private BigDecimal valorTotalCoco;

    private BigDecimal valorTotalCocoPerdido;

    private BigDecimal valorPorPessoa;

    private BigDecimal valorDespesas;

    private BigDecimal valorDinheiro;

    private BigDecimal valorCartao;

    private BigDecimal valorTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(ZonedDateTime dataInicial) {
        this.dataInicial = dataInicial;
    }

    public ZonedDateTime getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(ZonedDateTime dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Integer getQuantidadeCocosPerdidos() {
        return quantidadeCocosPerdidos;
    }

    public void setQuantidadeCocosPerdidos(Integer quantidadeCocosPerdidos) {
        this.quantidadeCocosPerdidos = quantidadeCocosPerdidos;
    }

    public Integer getQuantidadeCocosVendidos() {
        return quantidadeCocosVendidos;
    }

    public void setQuantidadeCocosVendidos(Integer quantidadeCocosVendidos) {
        this.quantidadeCocosVendidos = quantidadeCocosVendidos;
    }

    public Integer getQuantidadeCocoSobrou() {
        return quantidadeCocoSobrou;
    }

    public void setQuantidadeCocoSobrou(Integer quantidadeCocoSobrou) {
        this.quantidadeCocoSobrou = quantidadeCocoSobrou;
    }

    public Integer getDivididoPor() {
        return divididoPor;
    }

    public void setDivididoPor(Integer divididoPor) {
        this.divididoPor = divididoPor;
    }

    public BigDecimal getValorTotalCoco() {
        return valorTotalCoco;
    }

    public void setValorTotalCoco(BigDecimal valorTotalCoco) {
        this.valorTotalCoco = valorTotalCoco;
    }

    public BigDecimal getValorTotalCocoPerdido() {
        return valorTotalCocoPerdido;
    }

    public void setValorTotalCocoPerdido(BigDecimal valorTotalCocoPerdido) {
        this.valorTotalCocoPerdido = valorTotalCocoPerdido;
    }

    public BigDecimal getValorPorPessoa() {
        return valorPorPessoa;
    }

    public void setValorPorPessoa(BigDecimal valorPorPessoa) {
        this.valorPorPessoa = valorPorPessoa;
    }

    public BigDecimal getValorDespesas() {
        return valorDespesas;
    }

    public void setValorDespesas(BigDecimal valorDespesas) {
        this.valorDespesas = valorDespesas;
    }

    public BigDecimal getValorDinheiro() {
        return valorDinheiro;
    }

    public void setValorDinheiro(BigDecimal valorDinheiro) {
        this.valorDinheiro = valorDinheiro;
    }

    public BigDecimal getValorCartao() {
        return valorCartao;
    }

    public void setValorCartao(BigDecimal valorCartao) {
        this.valorCartao = valorCartao;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FechamentoCaixaDTO)) {
            return false;
        }

        FechamentoCaixaDTO fechamentoCaixaDTO = (FechamentoCaixaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fechamentoCaixaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FechamentoCaixaDTO{" +
            "id=" + getId() +
            ", dataInicial='" + getDataInicial() + "'" +
            ", dataFinal='" + getDataFinal() + "'" +
            ", quantidadeCocosPerdidos=" + getQuantidadeCocosPerdidos() +
            ", quantidadeCocosVendidos=" + getQuantidadeCocosVendidos() +
            ", quantidadeCocoSobrou=" + getQuantidadeCocoSobrou() +
            ", divididoPor=" + getDivididoPor() +
            ", valorTotalCoco=" + getValorTotalCoco() +
            ", valorTotalCocoPerdido=" + getValorTotalCocoPerdido() +
            ", valorPorPessoa=" + getValorPorPessoa() +
            ", valorDespesas=" + getValorDespesas() +
            ", valorDinheiro=" + getValorDinheiro() +
            ", valorCartao=" + getValorCartao() +
            ", valorTotal=" + getValorTotal() +
            "}";
    }
}
