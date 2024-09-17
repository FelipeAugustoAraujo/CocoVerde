package br.com.lisetech.cocoverde.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Produto.
 */
@Entity
@Table(name = "produto")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "valor_base")
    private String valorBase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "produtos", "entradaFinanceiras", "saidaFinanceiras" }, allowSetters = true)
    private Estoque estoque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "produtos", "entradaFinanceiras", "saidaFinanceiras" }, allowSetters = true)
    private Frente frente;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "produto")
    @JsonIgnoreProperties(value = { "produto", "entradaFinanceira" }, allowSetters = true)
    private Set<DetalhesEntradaFinanceira> detalhesEntradaFinanceiras = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "produtos")
    @JsonIgnoreProperties(value = { "produtos", "endereco", "entradaFinanceiras" }, allowSetters = true)
    private Set<Fornecedor> fornecedors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Produto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Produto nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Produto descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValorBase() {
        return this.valorBase;
    }

    public Produto valorBase(String valorBase) {
        this.setValorBase(valorBase);
        return this;
    }

    public void setValorBase(String valorBase) {
        this.valorBase = valorBase;
    }

    public Estoque getEstoque() {
        return this.estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public Produto estoque(Estoque estoque) {
        this.setEstoque(estoque);
        return this;
    }

    public Frente getFrente() {
        return this.frente;
    }

    public void setFrente(Frente frente) {
        this.frente = frente;
    }

    public Produto frente(Frente frente) {
        this.setFrente(frente);
        return this;
    }

    public Set<DetalhesEntradaFinanceira> getDetalhesEntradaFinanceiras() {
        return this.detalhesEntradaFinanceiras;
    }

    public void setDetalhesEntradaFinanceiras(Set<DetalhesEntradaFinanceira> detalhesEntradaFinanceiras) {
        if (this.detalhesEntradaFinanceiras != null) {
            this.detalhesEntradaFinanceiras.forEach(i -> i.setProduto(null));
        }
        if (detalhesEntradaFinanceiras != null) {
            detalhesEntradaFinanceiras.forEach(i -> i.setProduto(this));
        }
        this.detalhesEntradaFinanceiras = detalhesEntradaFinanceiras;
    }

    public Produto detalhesEntradaFinanceiras(Set<DetalhesEntradaFinanceira> detalhesEntradaFinanceiras) {
        this.setDetalhesEntradaFinanceiras(detalhesEntradaFinanceiras);
        return this;
    }

    public Produto addDetalhesEntradaFinanceira(DetalhesEntradaFinanceira detalhesEntradaFinanceira) {
        this.detalhesEntradaFinanceiras.add(detalhesEntradaFinanceira);
        detalhesEntradaFinanceira.setProduto(this);
        return this;
    }

    public Produto removeDetalhesEntradaFinanceira(DetalhesEntradaFinanceira detalhesEntradaFinanceira) {
        this.detalhesEntradaFinanceiras.remove(detalhesEntradaFinanceira);
        detalhesEntradaFinanceira.setProduto(null);
        return this;
    }

    public Set<Fornecedor> getFornecedors() {
        return this.fornecedors;
    }

    public void setFornecedors(Set<Fornecedor> fornecedors) {
        if (this.fornecedors != null) {
            this.fornecedors.forEach(i -> i.removeProduto(this));
        }
        if (fornecedors != null) {
            fornecedors.forEach(i -> i.addProduto(this));
        }
        this.fornecedors = fornecedors;
    }

    public Produto fornecedors(Set<Fornecedor> fornecedors) {
        this.setFornecedors(fornecedors);
        return this;
    }

    public Produto addFornecedor(Fornecedor fornecedor) {
        this.fornecedors.add(fornecedor);
        fornecedor.getProdutos().add(this);
        return this;
    }

    public Produto removeFornecedor(Fornecedor fornecedor) {
        this.fornecedors.remove(fornecedor);
        fornecedor.getProdutos().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produto)) {
            return false;
        }
        return getId() != null && getId().equals(((Produto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", valorBase='" + getValorBase() + "'" +
            "}";
    }
}
