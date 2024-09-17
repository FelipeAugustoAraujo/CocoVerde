package br.com.lisetech.cocoverde.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.lisetech.cocoverde.domain.DiaTrabalho} entity. This class is used
 * in {@link br.com.lisetech.cocoverde.web.rest.DiaTrabalhoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dia-trabalhos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DiaTrabalhoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter data;

    private Boolean distinct;

    public DiaTrabalhoCriteria() {}

    public DiaTrabalhoCriteria(DiaTrabalhoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DiaTrabalhoCriteria copy() {
        return new DiaTrabalhoCriteria(this);
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

    public InstantFilter getData() {
        return data;
    }

    public InstantFilter data() {
        if (data == null) {
            data = new InstantFilter();
        }
        return data;
    }

    public void setData(InstantFilter data) {
        this.data = data;
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
        final DiaTrabalhoCriteria that = (DiaTrabalhoCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(data, that.data) && Objects.equals(distinct, that.distinct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiaTrabalhoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (data != null ? "data=" + data + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
