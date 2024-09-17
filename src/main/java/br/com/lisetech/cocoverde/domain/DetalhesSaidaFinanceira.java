package br.com.lisetech.cocoverde.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DetalhesSaidaFinanceira.
 */
@Entity
@Table(name = "detalhes_saida_financeira")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetalhesSaidaFinanceira implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantidade_item")
    private Integer quantidadeItem;

    @Column(name = "valor", precision = 21, scale = 2)
    private BigDecimal valor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DetalhesSaidaFinanceira id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidadeItem() {
        return this.quantidadeItem;
    }

    public DetalhesSaidaFinanceira quantidadeItem(Integer quantidadeItem) {
        this.setQuantidadeItem(quantidadeItem);
        return this;
    }

    public void setQuantidadeItem(Integer quantidadeItem) {
        this.quantidadeItem = quantidadeItem;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public DetalhesSaidaFinanceira valor(BigDecimal valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetalhesSaidaFinanceira)) {
            return false;
        }
        return getId() != null && getId().equals(((DetalhesSaidaFinanceira) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetalhesSaidaFinanceira{" +
            "id=" + getId() +
            ", quantidadeItem=" + getQuantidadeItem() +
            ", valor=" + getValor() +
            "}";
    }
}
