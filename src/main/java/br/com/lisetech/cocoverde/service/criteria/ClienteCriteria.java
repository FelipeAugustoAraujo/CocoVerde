package br.com.lisetech.cocoverde.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.lisetech.cocoverde.domain.Cliente} entity. This class is used
 * in {@link br.com.lisetech.cocoverde.web.rest.ClienteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clientes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClienteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter dataNascimento;

    private StringFilter identificador;

    private InstantFilter dataCadastro;

    private StringFilter telefone;

    private LongFilter enderecoId;

    private Boolean distinct;

    public ClienteCriteria() {}

    public ClienteCriteria(ClienteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.dataNascimento = other.dataNascimento == null ? null : other.dataNascimento.copy();
        this.identificador = other.identificador == null ? null : other.identificador.copy();
        this.dataCadastro = other.dataCadastro == null ? null : other.dataCadastro.copy();
        this.telefone = other.telefone == null ? null : other.telefone.copy();
        this.enderecoId = other.enderecoId == null ? null : other.enderecoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ClienteCriteria copy() {
        return new ClienteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNome() {
        return nome;
    }

    public StringFilter nome() {
        if (nome == null) {
            nome = new StringFilter();
        }
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getDataNascimento() {
        return dataNascimento;
    }

    public StringFilter dataNascimento() {
        if (dataNascimento == null) {
            dataNascimento = new StringFilter();
        }
        return dataNascimento;
    }

    public void setDataNascimento(StringFilter dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public StringFilter getIdentificador() {
        return identificador;
    }

    public StringFilter identificador() {
        if (identificador == null) {
            identificador = new StringFilter();
        }
        return identificador;
    }

    public void setIdentificador(StringFilter identificador) {
        this.identificador = identificador;
    }

    public InstantFilter getDataCadastro() {
        return dataCadastro;
    }

    public InstantFilter dataCadastro() {
        if (dataCadastro == null) {
            dataCadastro = new InstantFilter();
        }
        return dataCadastro;
    }

    public void setDataCadastro(InstantFilter dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public StringFilter getTelefone() {
        return telefone;
    }

    public StringFilter telefone() {
        if (telefone == null) {
            telefone = new StringFilter();
        }
        return telefone;
    }

    public void setTelefone(StringFilter telefone) {
        this.telefone = telefone;
    }

    public LongFilter getEnderecoId() {
        return enderecoId;
    }

    public LongFilter enderecoId() {
        if (enderecoId == null) {
            enderecoId = new LongFilter();
        }
        return enderecoId;
    }

    public void setEnderecoId(LongFilter enderecoId) {
        this.enderecoId = enderecoId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClienteCriteria that = (ClienteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(dataNascimento, that.dataNascimento) &&
            Objects.equals(identificador, that.identificador) &&
            Objects.equals(dataCadastro, that.dataCadastro) &&
            Objects.equals(telefone, that.telefone) &&
            Objects.equals(enderecoId, that.enderecoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, dataNascimento, identificador, dataCadastro, telefone, enderecoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClienteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (dataNascimento != null ? "dataNascimento=" + dataNascimento + ", " : "") +
            (identificador != null ? "identificador=" + identificador + ", " : "") +
            (dataCadastro != null ? "dataCadastro=" + dataCadastro + ", " : "") +
            (telefone != null ? "telefone=" + telefone + ", " : "") +
            (enderecoId != null ? "enderecoId=" + enderecoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
