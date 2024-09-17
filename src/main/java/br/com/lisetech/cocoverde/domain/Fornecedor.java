package br.com.lisetech.cocoverde.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Fornecedor.
 */
@Entity
@Table(name = "fornecedor")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Fornecedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "identificador")
    private String identificador;

    @NotNull
    @Column(name = "telefone", nullable = false)
    private String telefone;

    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    private Instant dataCadastro;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_fornecedor__produto",
        joinColumns = @JoinColumn(name = "fornecedor_id"),
        inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    @JsonIgnoreProperties(value = { "estoque", "frente", "detalhesEntradaFinanceiras", "fornecedors" }, allowSetters = true)
    private Set<Produto> produtos = new HashSet<>();

    @JsonIgnoreProperties(value = { "fornecedor", "funcionario", "cliente", "cidade" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "fornecedor")
    private Endereco endereco;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fornecedor")
    @JsonIgnoreProperties(
        value = { "fornecedor", "estoque", "frente", "fechamentoCaixaDetalhes", "detalhesEntradaFinanceiras", "imagems" },
        allowSetters = true
    )
    private Set<EntradaFinanceira> entradaFinanceiras = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Fornecedor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Fornecedor nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdentificador() {
        return this.identificador;
    }

    public Fornecedor identificador(String identificador) {
        this.setIdentificador(identificador);
        return this;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Fornecedor telefone(String telefone) {
        this.setTelefone(telefone);
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Instant getDataCadastro() {
        return this.dataCadastro;
    }

    public Fornecedor dataCadastro(Instant dataCadastro) {
        this.setDataCadastro(dataCadastro);
        return this;
    }

    public void setDataCadastro(Instant dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Set<Produto> getProdutos() {
        return this.produtos;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    public Fornecedor produtos(Set<Produto> produtos) {
        this.setProdutos(produtos);
        return this;
    }

    public Fornecedor addProduto(Produto produto) {
        this.produtos.add(produto);
        return this;
    }

    public Fornecedor removeProduto(Produto produto) {
        this.produtos.remove(produto);
        return this;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(Endereco endereco) {
        if (this.endereco != null) {
            this.endereco.setFornecedor(null);
        }
        if (endereco != null) {
            endereco.setFornecedor(this);
        }
        this.endereco = endereco;
    }

    public Fornecedor endereco(Endereco endereco) {
        this.setEndereco(endereco);
        return this;
    }

    public Set<EntradaFinanceira> getEntradaFinanceiras() {
        return this.entradaFinanceiras;
    }

    public void setEntradaFinanceiras(Set<EntradaFinanceira> entradaFinanceiras) {
        if (this.entradaFinanceiras != null) {
            this.entradaFinanceiras.forEach(i -> i.setFornecedor(null));
        }
        if (entradaFinanceiras != null) {
            entradaFinanceiras.forEach(i -> i.setFornecedor(this));
        }
        this.entradaFinanceiras = entradaFinanceiras;
    }

    public Fornecedor entradaFinanceiras(Set<EntradaFinanceira> entradaFinanceiras) {
        this.setEntradaFinanceiras(entradaFinanceiras);
        return this;
    }

    public Fornecedor addEntradaFinanceira(EntradaFinanceira entradaFinanceira) {
        this.entradaFinanceiras.add(entradaFinanceira);
        entradaFinanceira.setFornecedor(this);
        return this;
    }

    public Fornecedor removeEntradaFinanceira(EntradaFinanceira entradaFinanceira) {
        this.entradaFinanceiras.remove(entradaFinanceira);
        entradaFinanceira.setFornecedor(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fornecedor)) {
            return false;
        }
        return getId() != null && getId().equals(((Fornecedor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fornecedor{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", identificador='" + getIdentificador() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", dataCadastro='" + getDataCadastro() + "'" +
            "}";
    }
}
