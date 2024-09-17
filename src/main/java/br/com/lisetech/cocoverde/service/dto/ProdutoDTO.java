package br.com.lisetech.cocoverde.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.lisetech.cocoverde.domain.Produto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProdutoDTO implements Serializable {

    private Long id;

    private String nome;

    private String descricao;

    private String valorBase;

    private EstoqueDTO estoque;

    private FrenteDTO frente;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValorBase() {
        return valorBase;
    }

    public void setValorBase(String valorBase) {
        this.valorBase = valorBase;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProdutoDTO)) {
            return false;
        }

        ProdutoDTO produtoDTO = (ProdutoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, produtoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProdutoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", valorBase='" + getValorBase() + "'" +
            ", estoque=" + getEstoque() +
            ", frente=" + getFrente() +
            "}";
    }
}
