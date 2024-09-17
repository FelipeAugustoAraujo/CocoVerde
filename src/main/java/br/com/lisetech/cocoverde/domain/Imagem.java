package br.com.lisetech.cocoverde.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Imagem.
 */
@Entity
@Table(name = "imagem")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Imagem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "content")
    private byte[] content;

    @Column(name = "content_content_type")
    private String contentContentType;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "estoque", "frente", "fechamentoCaixaDetalhes", "imagems" }, allowSetters = true)
    private SaidaFinanceira saidaFinanceira;

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

    public Imagem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Imagem name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return this.content;
    }

    public Imagem content(byte[] content) {
        this.setContent(content);
        return this;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return this.contentContentType;
    }

    public Imagem contentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
        return this;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }

    public String getContentType() {
        return this.contentType;
    }

    public Imagem contentType(String contentType) {
        this.setContentType(contentType);
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getDescription() {
        return this.description;
    }

    public Imagem description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SaidaFinanceira getSaidaFinanceira() {
        return this.saidaFinanceira;
    }

    public void setSaidaFinanceira(SaidaFinanceira saidaFinanceira) {
        this.saidaFinanceira = saidaFinanceira;
    }

    public Imagem saidaFinanceira(SaidaFinanceira saidaFinanceira) {
        this.setSaidaFinanceira(saidaFinanceira);
        return this;
    }

    public EntradaFinanceira getEntradaFinanceira() {
        return this.entradaFinanceira;
    }

    public void setEntradaFinanceira(EntradaFinanceira entradaFinanceira) {
        this.entradaFinanceira = entradaFinanceira;
    }

    public Imagem entradaFinanceira(EntradaFinanceira entradaFinanceira) {
        this.setEntradaFinanceira(entradaFinanceira);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Imagem)) {
            return false;
        }
        return getId() != null && getId().equals(((Imagem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Imagem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", content='" + getContent() + "'" +
            ", contentContentType='" + getContentContentType() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
