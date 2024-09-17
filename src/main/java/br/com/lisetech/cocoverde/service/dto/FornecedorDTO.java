package br.com.lisetech.cocoverde.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link br.com.lisetech.cocoverde.domain.Fornecedor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FornecedorDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private String identificador;

    @NotNull
    private String telefone;

    @NotNull
    private Instant dataCadastro;

    private Set<ProdutoDTO> produtos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Instant getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Instant dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Set<ProdutoDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(Set<ProdutoDTO> produtos) {
        this.produtos = produtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FornecedorDTO)) {
            return false;
        }

        FornecedorDTO fornecedorDTO = (FornecedorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fornecedorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FornecedorDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", identificador='" + getIdentificador() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", dataCadastro='" + getDataCadastro() + "'" +
            ", produtos=" + getProdutos() +
            "}";
    }
}
