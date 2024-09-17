package br.com.lisetech.cocoverde.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DetalhesEntradaFinanceira.
 */
@Entity
@Table(name = "detalhes_entrada_financeira")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetalhesEntradaFinanceira implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantidade_item")
    private Integer quantidadeItem;

    @Column(name = "valor", precision = 21, scale = 2)
    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "estoque", "frente", "detalhesEntradaFinanceiras", "fornecedors" }, allowSetters = true)
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "fornecedor", "estoque", "frente", "fechamentoCaixaDetalhes", "detalhesEntradaFinanceiras", "imagems" },
        allowSetters = true
    )
    private EntradaFinanceira entradaFinanceira;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DetalhesEntradaFinanceira id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidadeItem() {
        return this.quantidadeItem;
    }

    public DetalhesEntradaFinanceira quantidadeItem(Integer quantidadeItem) {
        this.setQuantidadeItem(quantidadeItem);
        return this;
    }

    public void setQuantidadeItem(Integer quantidadeItem) {
        this.quantidadeItem = quantidadeItem;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public DetalhesEntradaFinanceira valor(BigDecimal valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Produto getProduto() {
        return this.produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public DetalhesEntradaFinanceira produto(Produto produto) {
        this.setProduto(produto);
        return this;
    }

    public EntradaFinanceira getEntradaFinanceira() {
        return this.entradaFinanceira;
    }

    public void setEntradaFinanceira(EntradaFinanceira entradaFinanceira) {
        this.entradaFinanceira = entradaFinanceira;
    }

    public DetalhesEntradaFinanceira entradaFinanceira(EntradaFinanceira entradaFinanceira) {
        this.setEntradaFinanceira(entradaFinanceira);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetalhesEntradaFinanceira)) {
            return false;
        }
        return getId() != null && getId().equals(((DetalhesEntradaFinanceira) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetalhesEntradaFinanceira{" +
            "id=" + getId() +
            ", quantidadeItem=" + getQuantidadeItem() +
            ", valor=" + getValor() +
            "}";
    }
}
