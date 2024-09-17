package br.com.lisetech.cocoverde.service.dto;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.lisetech.cocoverde.domain.Imagem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImagemDTO implements Serializable {

    private Long id;

    private String name;

    @Lob
    private byte[] content;

    private String contentContentType;
    private String contentType;

    private String description;

    private SaidaFinanceiraDTO saidaFinanceira;

    private EntradaFinanceiraDTO entradaFinanceira;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SaidaFinanceiraDTO getSaidaFinanceira() {
        return saidaFinanceira;
    }

    public void setSaidaFinanceira(SaidaFinanceiraDTO saidaFinanceira) {
        this.saidaFinanceira = saidaFinanceira;
    }

    public EntradaFinanceiraDTO getEntradaFinanceira() {
        return entradaFinanceira;
    }

    public void setEntradaFinanceira(EntradaFinanceiraDTO entradaFinanceira) {
        this.entradaFinanceira = entradaFinanceira;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImagemDTO)) {
            return false;
        }

        ImagemDTO imagemDTO = (ImagemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, imagemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImagemDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", content='" + getContent() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", description='" + getDescription() + "'" +
            ", saidaFinanceira=" + getSaidaFinanceira() +
            ", entradaFinanceira=" + getEntradaFinanceira() +
            "}";
    }
}
