package br.com.lisetech.cocoverde.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.lisetech.cocoverde.domain.Imagem} entity. This class is used
 * in {@link br.com.lisetech.cocoverde.web.rest.ImagemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /imagems?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImagemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter contentType;

    private StringFilter description;

    private LongFilter saidaFinanceiraId;

    private LongFilter entradaFinanceiraId;

    private Boolean distinct;

    public ImagemCriteria() {}

    public ImagemCriteria(ImagemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.contentType = other.contentType == null ? null : other.contentType.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.saidaFinanceiraId = other.saidaFinanceiraId == null ? null : other.saidaFinanceiraId.copy();
        this.entradaFinanceiraId = other.entradaFinanceiraId == null ? null : other.entradaFinanceiraId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ImagemCriteria copy() {
        return new ImagemCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getContentType() {
        return contentType;
    }

    public StringFilter contentType() {
        if (contentType == null) {
            contentType = new StringFilter();
        }
        return contentType;
    }

    public void setContentType(StringFilter contentType) {
        this.contentType = contentType;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getSaidaFinanceiraId() {
        return saidaFinanceiraId;
    }

    public LongFilter saidaFinanceiraId() {
        if (saidaFinanceiraId == null) {
            saidaFinanceiraId = new LongFilter();
        }
        return saidaFinanceiraId;
    }

    public void setSaidaFinanceiraId(LongFilter saidaFinanceiraId) {
        this.saidaFinanceiraId = saidaFinanceiraId;
    }

    public LongFilter getEntradaFinanceiraId() {
        return entradaFinanceiraId;
    }

    public LongFilter entradaFinanceiraId() {
        if (entradaFinanceiraId == null) {
            entradaFinanceiraId = new LongFilter();
        }
        return entradaFinanceiraId;
    }

    public void setEntradaFinanceiraId(LongFilter entradaFinanceiraId) {
        this.entradaFinanceiraId = entradaFinanceiraId;
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
        final ImagemCriteria that = (ImagemCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(contentType, that.contentType) &&
            Objects.equals(description, that.description) &&
            Objects.equals(saidaFinanceiraId, that.saidaFinanceiraId) &&
            Objects.equals(entradaFinanceiraId, that.entradaFinanceiraId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, contentType, description, saidaFinanceiraId, entradaFinanceiraId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImagemCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (contentType != null ? "contentType=" + contentType + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (saidaFinanceiraId != null ? "saidaFinanceiraId=" + saidaFinanceiraId + ", " : "") +
            (entradaFinanceiraId != null ? "entradaFinanceiraId=" + entradaFinanceiraId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
