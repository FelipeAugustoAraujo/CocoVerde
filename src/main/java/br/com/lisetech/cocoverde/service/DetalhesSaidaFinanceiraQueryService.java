package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.*; // for static metamodels
import br.com.lisetech.cocoverde.domain.DetalhesSaidaFinanceira;
import br.com.lisetech.cocoverde.repository.DetalhesSaidaFinanceiraRepository;
import br.com.lisetech.cocoverde.service.criteria.DetalhesSaidaFinanceiraCriteria;
import br.com.lisetech.cocoverde.service.dto.DetalhesSaidaFinanceiraDTO;
import br.com.lisetech.cocoverde.service.mapper.DetalhesSaidaFinanceiraMapper;
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
 * Service for executing complex queries for {@link DetalhesSaidaFinanceira} entities in the database.
 * The main input is a {@link DetalhesSaidaFinanceiraCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DetalhesSaidaFinanceiraDTO} or a {@link Page} of {@link DetalhesSaidaFinanceiraDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DetalhesSaidaFinanceiraQueryService extends QueryService<DetalhesSaidaFinanceira> {

    private final Logger log = LoggerFactory.getLogger(DetalhesSaidaFinanceiraQueryService.class);

    private final DetalhesSaidaFinanceiraRepository detalhesSaidaFinanceiraRepository;

    private final DetalhesSaidaFinanceiraMapper detalhesSaidaFinanceiraMapper;

    public DetalhesSaidaFinanceiraQueryService(
        DetalhesSaidaFinanceiraRepository detalhesSaidaFinanceiraRepository,
        DetalhesSaidaFinanceiraMapper detalhesSaidaFinanceiraMapper
    ) {
        this.detalhesSaidaFinanceiraRepository = detalhesSaidaFinanceiraRepository;
        this.detalhesSaidaFinanceiraMapper = detalhesSaidaFinanceiraMapper;
    }

    /**
     * Return a {@link List} of {@link DetalhesSaidaFinanceiraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DetalhesSaidaFinanceiraDTO> findByCriteria(DetalhesSaidaFinanceiraCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DetalhesSaidaFinanceira> specification = createSpecification(criteria);
        return detalhesSaidaFinanceiraMapper.toDto(detalhesSaidaFinanceiraRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DetalhesSaidaFinanceiraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DetalhesSaidaFinanceiraDTO> findByCriteria(DetalhesSaidaFinanceiraCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DetalhesSaidaFinanceira> specification = createSpecification(criteria);
        return detalhesSaidaFinanceiraRepository.findAll(specification, page).map(detalhesSaidaFinanceiraMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DetalhesSaidaFinanceiraCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DetalhesSaidaFinanceira> specification = createSpecification(criteria);
        return detalhesSaidaFinanceiraRepository.count(specification);
    }

    /**
     * Function to convert {@link DetalhesSaidaFinanceiraCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DetalhesSaidaFinanceira> createSpecification(DetalhesSaidaFinanceiraCriteria criteria) {
        Specification<DetalhesSaidaFinanceira> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DetalhesSaidaFinanceira_.id));
            }
            if (criteria.getQuantidadeItem() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getQuantidadeItem(), DetalhesSaidaFinanceira_.quantidadeItem));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), DetalhesSaidaFinanceira_.valor));
            }
        }
        return specification;
    }
}
