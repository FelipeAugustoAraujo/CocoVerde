package br.com.lisetech.cocoverde.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * A FechamentoCaixa.
 */
@Entity
@Table(name = "fechamento_caixa")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FechamentoCaixa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "data_inicial")
    private ZonedDateTime dataInicial;

    @Column(name = "data_final")
    private ZonedDateTime dataFinal;

    @Column(name = "quantidade_cocos_perdidos")
    private Integer quantidadeCocosPerdidos;

    @Column(name = "quantidade_cocos_vendidos")
    private Integer quantidadeCocosVendidos;

    @Column(name = "quantidade_coco_sobrou")
    private Integer quantidadeCocoSobrou;

    @Column(name = "dividido_por")
    private Integer divididoPor;

    @Column(name = "valor_total_coco", precision = 21, scale = 2)
    private BigDecimal valorTotalCoco;

    @Column(name = "valor_total_coco_perdido", precision = 21, scale = 2)
    private BigDecimal valorTotalCocoPerdido;

    @Column(name = "valor_por_pessoa", precision = 21, scale = 2)
    private BigDecimal valorPorPessoa;

    @Column(name = "valor_despesas", precision = 21, scale = 2)
    private BigDecimal valorDespesas;

    @Column(name = "valor_dinheiro", precision = 21, scale = 2)
    private BigDecimal valorDinheiro;

    @Column(name = "valor_cartao", precision = 21, scale = 2)
    private BigDecimal valorCartao;

    @Column(name = "valor_total", precision = 21, scale = 2)
    private BigDecimal valorTotal;

    @JsonIgnoreProperties(value = { "fechamentoCaixa", "entradaFinanceiras", "saidaFinanceiras" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "fechamentoCaixa")
    private FechamentoCaixaDetalhes fechamentoCaixaDetalhes;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FechamentoCaixa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDataInicial() {
        return this.dataInicial;
    }

    public FechamentoCaixa dataInicial(ZonedDateTime dataInicial) {
        this.setDataInicial(dataInicial);
        return this;
    }

    public void setDataInicial(ZonedDateTime dataInicial) {
        this.dataInicial = dataInicial;
    }

    public ZonedDateTime getDataFinal() {
        return this.dataFinal;
    }

    public FechamentoCaixa dataFinal(ZonedDateTime dataFinal) {
        this.setDataFinal(dataFinal);
        return this;
    }

    public void setDataFinal(ZonedDateTime dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Integer getQuantidadeCocosPerdidos() {
        return this.quantidadeCocosPerdidos;
    }

    public FechamentoCaixa quantidadeCocosPerdidos(Integer quantidadeCocosPerdidos) {
        this.setQuantidadeCocosPerdidos(quantidadeCocosPerdidos);
        return this;
    }

    public void setQuantidadeCocosPerdidos(Integer quantidadeCocosPerdidos) {
        this.quantidadeCocosPerdidos = quantidadeCocosPerdidos;
    }

    public Integer getQuantidadeCocosVendidos() {
        return this.quantidadeCocosVendidos;
    }

    public FechamentoCaixa quantidadeCocosVendidos(Integer quantidadeCocosVendidos) {
        this.setQuantidadeCocosVendidos(quantidadeCocosVendidos);
        return this;
    }

    public void setQuantidadeCocosVendidos(Integer quantidadeCocosVendidos) {
        this.quantidadeCocosVendidos = quantidadeCocosVendidos;
    }

    public Integer getQuantidadeCocoSobrou() {
        return this.quantidadeCocoSobrou;
    }

    public FechamentoCaixa quantidadeCocoSobrou(Integer quantidadeCocoSobrou) {
        this.setQuantidadeCocoSobrou(quantidadeCocoSobrou);
        return this;
    }

    public void setQuantidadeCocoSobrou(Integer quantidadeCocoSobrou) {
        this.quantidadeCocoSobrou = quantidadeCocoSobrou;
    }

    public Integer getDivididoPor() {
        return this.divididoPor;
    }

    public FechamentoCaixa divididoPor(Integer divididoPor) {
        this.setDivididoPor(divididoPor);
        return this;
    }

    public void setDivididoPor(Integer divididoPor) {
        this.divididoPor = divididoPor;
    }

    public BigDecimal getValorTotalCoco() {
        return this.valorTotalCoco;
    }

    public FechamentoCaixa valorTotalCoco(BigDecimal valorTotalCoco) {
        this.setValorTotalCoco(valorTotalCoco);
        return this;
    }

    public void setValorTotalCoco(BigDecimal valorTotalCoco) {
        this.valorTotalCoco = valorTotalCoco;
    }

    public BigDecimal getValorTotalCocoPerdido() {
        return this.valorTotalCocoPerdido;
    }

    public FechamentoCaixa valorTotalCocoPerdido(BigDecimal valorTotalCocoPerdido) {
        this.setValorTotalCocoPerdido(valorTotalCocoPerdido);
        return this;
    }

    public void setValorTotalCocoPerdido(BigDecimal valorTotalCocoPerdido) {
        this.valorTotalCocoPerdido = valorTotalCocoPerdido;
    }

    public BigDecimal getValorPorPessoa() {
        return this.valorPorPessoa;
    }

    public FechamentoCaixa valorPorPessoa(BigDecimal valorPorPessoa) {
        this.setValorPorPessoa(valorPorPessoa);
        return this;
    }

    public void setValorPorPessoa(BigDecimal valorPorPessoa) {
        this.valorPorPessoa = valorPorPessoa;
    }

    public BigDecimal getValorDespesas() {
        return this.valorDespesas;
    }

    public FechamentoCaixa valorDespesas(BigDecimal valorDespesas) {
        this.setValorDespesas(valorDespesas);
        return this;
    }

    public void setValorDespesas(BigDecimal valorDespesas) {
        this.valorDespesas = valorDespesas;
    }

    public BigDecimal getValorDinheiro() {
        return this.valorDinheiro;
    }

    public FechamentoCaixa valorDinheiro(BigDecimal valorDinheiro) {
        this.setValorDinheiro(valorDinheiro);
        return this;
    }

    public void setValorDinheiro(BigDecimal valorDinheiro) {
        this.valorDinheiro = valorDinheiro;
    }

    public BigDecimal getValorCartao() {
        return this.valorCartao;
    }

    public FechamentoCaixa valorCartao(BigDecimal valorCartao) {
        this.setValorCartao(valorCartao);
        return this;
    }

    public void setValorCartao(BigDecimal valorCartao) {
        this.valorCartao = valorCartao;
    }

    public BigDecimal getValorTotal() {
        return this.valorTotal;
    }

    public FechamentoCaixa valorTotal(BigDecimal valorTotal) {
        this.setValorTotal(valorTotal);
        return this;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public FechamentoCaixaDetalhes getFechamentoCaixaDetalhes() {
        return this.fechamentoCaixaDetalhes;
    }

    public void setFechamentoCaixaDetalhes(FechamentoCaixaDetalhes fechamentoCaixaDetalhes) {
        if (this.fechamentoCaixaDetalhes != null) {
            this.fechamentoCaixaDetalhes.setFechamentoCaixa(null);
        }
        if (fechamentoCaixaDetalhes != null) {
            fechamentoCaixaDetalhes.setFechamentoCaixa(this);
        }
        this.fechamentoCaixaDetalhes = fechamentoCaixaDetalhes;
    }

    public FechamentoCaixa fechamentoCaixaDetalhes(FechamentoCaixaDetalhes fechamentoCaixaDetalhes) {
        this.setFechamentoCaixaDetalhes(fechamentoCaixaDetalhes);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FechamentoCaixa)) {
            return false;
        }
        return getId() != null && getId().equals(((FechamentoCaixa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FechamentoCaixa{" +
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
