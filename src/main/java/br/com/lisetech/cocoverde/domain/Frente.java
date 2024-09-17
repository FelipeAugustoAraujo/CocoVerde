package br.com.lisetech.cocoverde.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Frente.
 */
@Entity
@Table(name = "frente")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Frente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "criado_em")
    private Instant criadoEm;

    @Column(name = "modificado_em")
    private Instant modificadoEm;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frente")
    @JsonIgnoreProperties(value = { "estoque", "frente", "detalhesEntradaFinanceiras", "fornecedors" }, allowSetters = true)
    private Set<Produto> produtos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frente")
    @JsonIgnoreProperties(
        value = { "fornecedor", "estoque", "frente", "fechamentoCaixaDetalhes", "detalhesEntradaFinanceiras", "imagems" },
        allowSetters = true
    )
    private Set<EntradaFinanceira> entradaFinanceiras = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frente")
    @JsonIgnoreProperties(value = { "estoque", "frente", "fechamentoCaixaDetalhes", "imagems" }, allowSetters = true)
    private Set<SaidaFinanceira> saidaFinanceiras = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Frente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return this.quantidade;
    }

    public Frente quantidade(Integer quantidade) {
        this.setQuantidade(quantidade);
        return this;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Instant getCriadoEm() {
        return this.criadoEm;
    }

    public Frente criadoEm(Instant criadoEm) {
        this.setCriadoEm(criadoEm);
        return this;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Instant getModificadoEm() {
        return this.modificadoEm;
    }

    public Frente modificadoEm(Instant modificadoEm) {
        this.setModificadoEm(modificadoEm);
        return this;
    }

    public void setModificadoEm(Instant modificadoEm) {
        this.modificadoEm = modificadoEm;
    }

    public Set<Produto> getProdutos() {
        return this.produtos;
    }

    public void setProdutos(Set<Produto> produtos) {
        if (this.produtos != null) {
            this.produtos.forEach(i -> i.setFrente(null));
        }
        if (produtos != null) {
            produtos.forEach(i -> i.setFrente(this));
        }
        this.produtos = produtos;
    }

    public Frente produtos(Set<Produto> produtos) {
        this.setProdutos(produtos);
        return this;
    }

    public Frente addProduto(Produto produto) {
        this.produtos.add(produto);
        produto.setFrente(this);
        return this;
    }

    public Frente removeProduto(Produto produto) {
        this.produtos.remove(produto);
        produto.setFrente(null);
        return this;
    }

    public Set<EntradaFinanceira> getEntradaFinanceiras() {
        return this.entradaFinanceiras;
    }

    public void setEntradaFinanceiras(Set<EntradaFinanceira> entradaFinanceiras) {
        if (this.entradaFinanceiras != null) {
            this.entradaFinanceiras.forEach(i -> i.setFrente(null));
        }
        if (entradaFinanceiras != null) {
            entradaFinanceiras.forEach(i -> i.setFrente(this));
        }
        this.entradaFinanceiras = entradaFinanceiras;
    }

    public Frente entradaFinanceiras(Set<EntradaFinanceira> entradaFinanceiras) {
        this.setEntradaFinanceiras(entradaFinanceiras);
        return this;
    }

    public Frente addEntradaFinanceira(EntradaFinanceira entradaFinanceira) {
        this.entradaFinanceiras.add(entradaFinanceira);
        entradaFinanceira.setFrente(this);
        return this;
    }

    public Frente removeEntradaFinanceira(EntradaFinanceira entradaFinanceira) {
        this.entradaFinanceiras.remove(entradaFinanceira);
        entradaFinanceira.setFrente(null);
        return this;
    }

    public Set<SaidaFinanceira> getSaidaFinanceiras() {
        return this.saidaFinanceiras;
    }

    public void setSaidaFinanceiras(Set<SaidaFinanceira> saidaFinanceiras) {
        if (this.saidaFinanceiras != null) {
            this.saidaFinanceiras.forEach(i -> i.setFrente(null));
        }
        if (saidaFinanceiras != null) {
            saidaFinanceiras.forEach(i -> i.setFrente(this));
        }
        this.saidaFinanceiras = saidaFinanceiras;
    }

    public Frente saidaFinanceiras(Set<SaidaFinanceira> saidaFinanceiras) {
        this.setSaidaFinanceiras(saidaFinanceiras);
        return this;
    }

    public Frente addSaidaFinanceira(SaidaFinanceira saidaFinanceira) {
        this.saidaFinanceiras.add(saidaFinanceira);
        saidaFinanceira.setFrente(this);
        return this;
    }

    public Frente removeSaidaFinanceira(SaidaFinanceira saidaFinanceira) {
        this.saidaFinanceiras.remove(saidaFinanceira);
        saidaFinanceira.setFrente(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Frente)) {
            return false;
        }
        return getId() != null && getId().equals(((Frente) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Frente{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", criadoEm='" + getCriadoEm() + "'" +
            ", modificadoEm='" + getModificadoEm() + "'" +
            "}";
    }
}
