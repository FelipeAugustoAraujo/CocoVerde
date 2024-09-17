package br.com.lisetech.cocoverde.service.dto;

import br.com.lisetech.cocoverde.domain.enumeration.MetodoPagamento;
import br.com.lisetech.cocoverde.domain.enumeration.ResponsavelPagamento;
import br.com.lisetech.cocoverde.domain.enumeration.StatusPagamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.lisetech.cocoverde.domain.SaidaFinanceira} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaidaFinanceiraDTO implements Serializable {

    private Long id;

    private ZonedDateTime data;

    private BigDecimal valorTotal;

    private String descricao;

    private MetodoPagamento metodoPagamento;

    private StatusPagamento statusPagamento;

    private ResponsavelPagamento responsavelPagamento;

    private EstoqueDTO estoque;

    private FrenteDTO frente;

    private FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public ResponsavelPagamento getResponsavelPagamento() {
        return responsavelPagamento;
    }

    public void setResponsavelPagamento(ResponsavelPagamento responsavelPagamento) {
        this.responsavelPagamento = responsavelPagamento;
    }

    public EstoqueDTO getEstoque() {
        return estoque;
    }

    public void setEstoque(EstoqueDTO estoque) {
        this.estoque = estoque;
    }

    public FrenteDTO getFrente() {
        return frente;
    }

    public void setFrente(FrenteDTO frente) {
        this.frente = frente;
    }

    public FechamentoCaixaDetalhesDTO getFechamentoCaixaDetalhes() {
        return fechamentoCaixaDetalhes;
    }

    public void setFechamentoCaixaDetalhes(FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhes) {
        this.fechamentoCaixaDetalhes = fechamentoCaixaDetalhes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaidaFinanceiraDTO)) {
            return false;
        }

        SaidaFinanceiraDTO saidaFinanceiraDTO = (SaidaFinanceiraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, saidaFinanceiraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaidaFinanceiraDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", valorTotal=" + getValorTotal() +
            ", descricao='" + getDescricao() + "'" +
            ", metodoPagamento='" + getMetodoPagamento() + "'" +
            ", statusPagamento='" + getStatusPagamento() + "'" +
            ", responsavelPagamento='" + getResponsavelPagamento() + "'" +
            ", estoque=" + getEstoque() +
            ", frente=" + getFrente() +
            ", fechamentoCaixaDetalhes=" + getFechamentoCaixaDetalhes() +
            "}";
    }
}
