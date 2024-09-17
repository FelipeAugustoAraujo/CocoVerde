package br.com.lisetech.cocoverde.domain;

import br.com.lisetech.cocoverde.domain.enumeration.MetodoPagamento;
import br.com.lisetech.cocoverde.domain.enumeration.ResponsavelPagamento;
import br.com.lisetech.cocoverde.domain.enumeration.StatusPagamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A SaidaFinanceira.
 */
@Entity
@Table(name = "saida_financeira")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaidaFinanceira implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "data")
    private ZonedDateTime data;

    @Column(name = "valor_total", precision = 21, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento")
    private MetodoPagamento metodoPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento")
    private StatusPagamento statusPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "responsavel_pagamento")
    private ResponsavelPagamento responsavelPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "produtos", "entradaFinanceiras", "saidaFinanceiras" }, allowSetters = true)
    private Estoque estoque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "produtos", "entradaFinanceiras", "saidaFinanceiras" }, allowSetters = true)
    private Frente frente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "fechamentoCaixa", "entradaFinanceiras", "saidaFinanceiras" }, allowSetters = true)
    private FechamentoCaixaDetalhes fechamentoCaixaDetalhes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "saidaFinanceira")
    @JsonIgnoreProperties(value = { "saidaFinanceira", "entradaFinanceira" }, allowSetters = true)
    private Set<Imagem> imagems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SaidaFinanceira id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getData() {
        return this.data;
    }

    public SaidaFinanceira data(ZonedDateTime data) {
        this.setData(data);
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public BigDecimal getValorTotal() {
        return this.valorTotal;
    }

    public SaidaFinanceira valorTotal(BigDecimal valorTotal) {
        this.setValorTotal(valorTotal);
        return this;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public SaidaFinanceira descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public MetodoPagamento getMetodoPagamento() {
        return this.metodoPagamento;
    }

    public SaidaFinanceira metodoPagamento(MetodoPagamento metodoPagamento) {
        this.setMetodoPagamento(metodoPagamento);
        return this;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public StatusPagamento getStatusPagamento() {
        return this.statusPagamento;
    }

    public SaidaFinanceira statusPagamento(StatusPagamento statusPagamento) {
        this.setStatusPagamento(statusPagamento);
        return this;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public ResponsavelPagamento getResponsavelPagamento() {
        return this.responsavelPagamento;
    }

    public SaidaFinanceira responsavelPagamento(ResponsavelPagamento responsavelPagamento) {
        this.setResponsavelPagamento(responsavelPagamento);
        return this;
    }

    public void setResponsavelPagamento(ResponsavelPagamento responsavelPagamento) {
        this.responsavelPagamento = responsavelPagamento;
    }

    public Estoque getEstoque() {
        return this.estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public SaidaFinanceira estoque(Estoque estoque) {
        this.setEstoque(estoque);
        return this;
    }

    public Frente getFrente() {
        return this.frente;
    }

    public void setFrente(Frente frente) {
        this.frente = frente;
    }

    public SaidaFinanceira frente(Frente frente) {
        this.setFrente(frente);
        return this;
    }

    public FechamentoCaixaDetalhes getFechamentoCaixaDetalhes() {
        return this.fechamentoCaixaDetalhes;
    }

    public void setFechamentoCaixaDetalhes(FechamentoCaixaDetalhes fechamentoCaixaDetalhes) {
        this.fechamentoCaixaDetalhes = fechamentoCaixaDetalhes;
    }

    public SaidaFinanceira fechamentoCaixaDetalhes(FechamentoCaixaDetalhes fechamentoCaixaDetalhes) {
        this.setFechamentoCaixaDetalhes(fechamentoCaixaDetalhes);
        return this;
    }

    public Set<Imagem> getImagems() {
        return this.imagems;
    }

    public void setImagems(Set<Imagem> imagems) {
        if (this.imagems != null) {
            this.imagems.forEach(i -> i.setSaidaFinanceira(null));
        }
        if (imagems != null) {
            imagems.forEach(i -> i.setSaidaFinanceira(this));
        }
        this.imagems = imagems;
    }

    public SaidaFinanceira imagems(Set<Imagem> imagems) {
        this.setImagems(imagems);
        return this;
    }

    public SaidaFinanceira addImagem(Imagem imagem) {
        this.imagems.add(imagem);
        imagem.setSaidaFinanceira(this);
        return this;
    }

    public SaidaFinanceira removeImagem(Imagem imagem) {
        this.imagems.remove(imagem);
        imagem.setSaidaFinanceira(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaidaFinanceira)) {
            return false;
        }
        return getId() != null && getId().equals(((SaidaFinanceira) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaidaFinanceira{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", valorTotal=" + getValorTotal() +
            ", descricao='" + getDescricao() + "'" +
            ", metodoPagamento='" + getMetodoPagamento() + "'" +
            ", statusPagamento='" + getStatusPagamento() + "'" +
            ", responsavelPagamento='" + getResponsavelPagamento() + "'" +
            "}";
    }
}
