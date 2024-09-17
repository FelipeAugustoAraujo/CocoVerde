package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.*; // for static metamodels
import br.com.lisetech.cocoverde.domain.DetalhesEntradaFinanceira;
import br.com.lisetech.cocoverde.repository.DetalhesEntradaFinanceiraRepository;
import br.com.lisetech.cocoverde.service.criteria.DetalhesEntradaFinanceiraCriteria;
import br.com.lisetech.cocoverde.service.dto.DetalhesEntradaFinanceiraDTO;
import br.com.lisetech.cocoverde.service.mapper.DetalhesEntradaFinanceiraMapper;
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
 * Service for executing complex queries for {@link DetalhesEntradaFinanceira} entities in the database.
 * The main input is a {@link DetalhesEntradaFinanceiraCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DetalhesEntradaFinanceiraDTO} or a {@link Page} of {@link DetalhesEntradaFinanceiraDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DetalhesEntradaFinanceiraQueryService extends QueryService<DetalhesEntradaFinanceira> {

    private final Logger log = LoggerFactory.getLogger(DetalhesEntradaFinanceiraQueryService.class);

    private final DetalhesEntradaFinanceiraRepository detalhesEntradaFinanceiraRepository;

    private final DetalhesEntradaFinanceiraMapper detalhesEntradaFinanceiraMapper;

    public DetalhesEntradaFinanceiraQueryService(
        DetalhesEntradaFinanceiraRepository detalhesEntradaFinanceiraRepository,
        DetalhesEntradaFinanceiraMapper detalhesEntradaFinanceiraMapper
    ) {
        this.detalhesEntradaFinanceiraRepository = detalhesEntradaFinanceiraRepository;
        this.detalhesEntradaFinanceiraMapper = detalhesEntradaFinanceiraMapper;
    }

    /**
     * Return a {@link List} of {@link DetalhesEntradaFinanceiraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DetalhesEntradaFinanceiraDTO> findByCriteria(DetalhesEntradaFinanceiraCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DetalhesEntradaFinanceira> specification = createSpecification(criteria);
        return detalhesEntradaFinanceiraMapper.toDto(detalhesEntradaFinanceiraRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DetalhesEntradaFinanceiraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DetalhesEntradaFinanceiraDTO> findByCriteria(DetalhesEntradaFinanceiraCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DetalhesEntradaFinanceira> specification = createSpecification(criteria);
        return detalhesEntradaFinanceiraRepository.findAll(specification, page).map(detalhesEntradaFinanceiraMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DetalhesEntradaFinanceiraCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DetalhesEntradaFinanceira> specification = createSpecification(criteria);
        return detalhesEntradaFinanceiraRepository.count(specification);
    }

    /**
     * Function to convert {@link DetalhesEntradaFinanceiraCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DetalhesEntradaFinanceira> createSpecification(DetalhesEntradaFinanceiraCriteria criteria) {
        Specification<DetalhesEntradaFinanceira> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DetalhesEntradaFinanceira_.id));
            }
            if (criteria.getQuantidadeItem() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getQuantidadeItem(), DetalhesEntradaFinanceira_.quantidadeItem));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), DetalhesEntradaFinanceira_.valor));
            }
            if (criteria.getProdutoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProdutoId(),
                            root -> root.join(DetalhesEntradaFinanceira_.produto, JoinType.LEFT).get(Produto_.id)
                        )
                    );
            }
            if (criteria.getEntradaFinanceiraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEntradaFinanceiraId(),
                            root -> root.join(DetalhesEntradaFinanceira_.entradaFinanceira, JoinType.LEFT).get(EntradaFinanceira_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
