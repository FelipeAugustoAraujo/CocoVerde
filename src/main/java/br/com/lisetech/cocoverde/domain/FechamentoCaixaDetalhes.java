package br.com.lisetech.cocoverde.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A FechamentoCaixaDetalhes.
 */
@Entity
@Table(name = "fechamento_caixa_detalhes")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FechamentoCaixaDetalhes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnoreProperties(value = { "fechamentoCaixaDetalhes" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private FechamentoCaixa fechamentoCaixa;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fechamentoCaixaDetalhes")
    @JsonIgnoreProperties(
        value = { "fornecedor", "estoque", "frente", "fechamentoCaixaDetalhes", "detalhesEntradaFinanceiras", "imagems" },
        allowSetters = true
    )
    private Set<EntradaFinanceira> entradaFinanceiras = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fechamentoCaixaDetalhes")
    @JsonIgnoreProperties(value = { "estoque", "frente", "fechamentoCaixaDetalhes", "imagems" }, allowSetters = true)
    private Set<SaidaFinanceira> saidaFinanceiras = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FechamentoCaixaDetalhes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FechamentoCaixa getFechamentoCaixa() {
        return this.fechamentoCaixa;
    }

    public void setFechamentoCaixa(FechamentoCaixa fechamentoCaixa) {
        this.fechamentoCaixa = fechamentoCaixa;
    }

    public FechamentoCaixaDetalhes fechamentoCaixa(FechamentoCaixa fechamentoCaixa) {
        this.setFechamentoCaixa(fechamentoCaixa);
        return this;
    }

    public Set<EntradaFinanceira> getEntradaFinanceiras() {
        return this.entradaFinanceiras;
    }

    public void setEntradaFinanceiras(Set<EntradaFinanceira> entradaFinanceiras) {
        if (this.entradaFinanceiras != null) {
            this.entradaFinanceiras.forEach(i -> i.setFechamentoCaixaDetalhes(null));
        }
        if (entradaFinanceiras != null) {
            entradaFinanceiras.forEach(i -> i.setFechamentoCaixaDetalhes(this));
        }
        this.entradaFinanceiras = entradaFinanceiras;
    }

    public FechamentoCaixaDetalhes entradaFinanceiras(Set<EntradaFinanceira> entradaFinanceiras) {
        this.setEntradaFinanceiras(entradaFinanceiras);
        return this;
    }

    public FechamentoCaixaDetalhes addEntradaFinanceira(EntradaFinanceira entradaFinanceira) {
        this.entradaFinanceiras.add(entradaFinanceira);
        entradaFinanceira.setFechamentoCaixaDetalhes(this);
        return this;
    }

    public FechamentoCaixaDetalhes removeEntradaFinanceira(EntradaFinanceira entradaFinanceira) {
        this.entradaFinanceiras.remove(entradaFinanceira);
        entradaFinanceira.setFechamentoCaixaDetalhes(null);
        return this;
    }

    public Set<SaidaFinanceira> getSaidaFinanceiras() {
        return this.saidaFinanceiras;
    }

    public void setSaidaFinanceiras(Set<SaidaFinanceira> saidaFinanceiras) {
        if (this.saidaFinanceiras != null) {
            this.saidaFinanceiras.forEach(i -> i.setFechamentoCaixaDetalhes(null));
        }
        if (saidaFinanceiras != null) {
            saidaFinanceiras.forEach(i -> i.setFechamentoCaixaDetalhes(this));
        }
        this.saidaFinanceiras = saidaFinanceiras;
    }

    public FechamentoCaixaDetalhes saidaFinanceiras(Set<SaidaFinanceira> saidaFinanceiras) {
        this.setSaidaFinanceiras(saidaFinanceiras);
        return this;
    }

    public FechamentoCaixaDetalhes addSaidaFinanceira(SaidaFinanceira saidaFinanceira) {
        this.saidaFinanceiras.add(saidaFinanceira);
        saidaFinanceira.setFechamentoCaixaDetalhes(this);
        return this;
    }

    public FechamentoCaixaDetalhes removeSaidaFinanceira(SaidaFinanceira saidaFinanceira) {
        this.saidaFinanceiras.remove(saidaFinanceira);
        saidaFinanceira.setFechamentoCaixaDetalhes(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FechamentoCaixaDetalhes)) {
            return false;
        }
        return getId() != null && getId().equals(((FechamentoCaixaDetalhes) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FechamentoCaixaDetalhes{" +
            "id=" + getId() +
            "}";
    }
}
