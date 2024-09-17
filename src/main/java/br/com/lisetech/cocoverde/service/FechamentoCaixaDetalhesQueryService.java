package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.*; // for static metamodels
import br.com.lisetech.cocoverde.domain.FechamentoCaixaDetalhes;
import br.com.lisetech.cocoverde.repository.FechamentoCaixaDetalhesRepository;
import br.com.lisetech.cocoverde.service.criteria.FechamentoCaixaDetalhesCriteria;
import br.com.lisetech.cocoverde.service.dto.FechamentoCaixaDetalhesDTO;
import br.com.lisetech.cocoverde.service.mapper.FechamentoCaixaDetalhesMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link FechamentoCaixaDetalhes} entities in the database.
 * The main input is a {@link FechamentoCaixaDetalhesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FechamentoCaixaDetalhesDTO} or a {@link Page} of {@link FechamentoCaixaDetalhesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FechamentoCaixaDetalhesQueryService extends QueryService<FechamentoCaixaDetalhes> {

    private final Logger log = LoggerFactory.getLogger(FechamentoCaixaDetalhesQueryService.class);

    private final FechamentoCaixaDetalhesRepository fechamentoCaixaDetalhesRepository;

    private final FechamentoCaixaDetalhesMapper fechamentoCaixaDetalhesMapper;

    public FechamentoCaixaDetalhesQueryService(
        FechamentoCaixaDetalhesRepository fechamentoCaixaDetalhesRepository,
        FechamentoCaixaDetalhesMapper fechamentoCaixaDetalhesMapper
    ) {
        this.fechamentoCaixaDetalhesRepository = fechamentoCaixaDetalhesRepository;
        this.fechamentoCaixaDetalhesMapper = fechamentoCaixaDetalhesMapper;
    }

    /**
     * Return a {@link List} of {@link FechamentoCaixaDetalhesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FechamentoCaixaDetalhesDTO> findByCriteria(FechamentoCaixaDetalhesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FechamentoCaixaDetalhes> specification = createSpecification(criteria);
        return fechamentoCaixaDetalhesMapper.toDto(fechamentoCaixaDetalhesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FechamentoCaixaDetalhesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FechamentoCaixaDetalhesDTO> findByCriteria(FechamentoCaixaDetalhesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FechamentoCaixaDetalhes> specification = createSpecification(criteria);
        return fechamentoCaixaDetalhesRepository.findAll(specification, page).map(fechamentoCaixaDetalhesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FechamentoCaixaDetalhesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FechamentoCaixaDetalhes> specification = createSpecification(criteria);
        return fechamentoCaixaDetalhesRepository.count(specification);
    }

    /**
     * Function to convert {@link FechamentoCaixaDetalhesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FechamentoCaixaDetalhes> createSpecification(FechamentoCaixaDetalhesCriteria criteria) {
        Specification<FechamentoCaixaDetalhes> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FechamentoCaixaDetalhes_.id));
            }
            if (criteria.getFechamentoCaixaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFechamentoCaixaId(),
                            root -> root.join(FechamentoCaixaDetalhes_.fechamentoCaixa, JoinType.LEFT).get(FechamentoCaixa_.id)
                        )
                    );
            }
            if (criteria.getEntradaFinanceiraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEntradaFinanceiraId(),
                            root -> root.join(FechamentoCaixaDetalhes_.entradaFinanceiras, JoinType.LEFT).get(EntradaFinanceira_.id)
                        )
                    );
            }
            if (criteria.getSaidaFinanceiraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSaidaFinanceiraId(),
                            root -> root.join(FechamentoCaixaDetalhes_.saidaFinanceiras, JoinType.LEFT).get(SaidaFinanceira_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
