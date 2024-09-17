package br.com.lisetech.cocoverde.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A Funcionario.
 */
@Entity
@Table(name = "funcionario")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_nascimento")
    private String dataNascimento;

    @Column(name = "identificador")
    private String identificador;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "data_cadastro")
    private Instant dataCadastro;

    @Column(name = "valor_base", precision = 21, scale = 2)
    private BigDecimal valorBase;

    @JsonIgnoreProperties(value = { "fornecedor", "funcionario", "cliente", "cidade" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "funcionario")
    private Endereco endereco;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Funcionario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Funcionario nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return this.dataNascimento;
    }

    public Funcionario dataNascimento(String dataNascimento) {
        this.setDataNascimento(dataNascimento);
        return this;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getIdentificador() {
        return this.identificador;
    }

    public Funcionario identificador(String identificador) {
        this.setIdentificador(identificador);
        return this;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Funcionario telefone(String telefone) {
        this.setTelefone(telefone);
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Instant getDataCadastro() {
        return this.dataCadastro;
    }

    public Funcionario dataCadastro(Instant dataCadastro) {
        this.setDataCadastro(dataCadastro);
        return this;
    }

    public void setDataCadastro(Instant dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public BigDecimal getValorBase() {
        return this.valorBase;
    }

    public Funcionario valorBase(BigDecimal valorBase) {
        this.setValorBase(valorBase);
        return this;
    }

    public void setValorBase(BigDecimal valorBase) {
        this.valorBase = valorBase;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(Endereco endereco) {
        if (this.endereco != null) {
            this.endereco.setFuncionario(null);
        }
        if (endereco != null) {
            endereco.setFuncionario(this);
        }
        this.endereco = endereco;
    }

    public Funcionario endereco(Endereco endereco) {
        this.setEndereco(endereco);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Funcionario)) {
            return false;
        }
        return getId() != null && getId().equals(((Funcionario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Funcionario{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", identificador='" + getIdentificador() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", dataCadastro='" + getDataCadastro() + "'" +
            ", valorBase=" + getValorBase() +
            "}";
    }
}
