package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.*; // for static metamodels
import br.com.lisetech.cocoverde.domain.Estoque;
import br.com.lisetech.cocoverde.repository.EstoqueRepository;
import br.com.lisetech.cocoverde.service.criteria.EstoqueCriteria;
import br.com.lisetech.cocoverde.service.dto.EstoqueDTO;
import br.com.lisetech.cocoverde.service.mapper.EstoqueMapper;
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
 * Service for executing complex queries for {@link Estoque} entities in the database.
 * The main input is a {@link EstoqueCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EstoqueDTO} or a {@link Page} of {@link EstoqueDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EstoqueQueryService extends QueryService<Estoque> {

    private final Logger log = LoggerFactory.getLogger(EstoqueQueryService.class);

    private final EstoqueRepository estoqueRepository;

    private final EstoqueMapper estoqueMapper;

    public EstoqueQueryService(EstoqueRepository estoqueRepository, EstoqueMapper estoqueMapper) {
        this.estoqueRepository = estoqueRepository;
        this.estoqueMapper = estoqueMapper;
    }

    /**
     * Return a {@link List} of {@link EstoqueDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EstoqueDTO> findByCriteria(EstoqueCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Estoque> specification = createSpecification(criteria);
        return estoqueMapper.toDto(estoqueRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EstoqueDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EstoqueDTO> findByCriteria(EstoqueCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Estoque> specification = createSpecification(criteria);
        return estoqueRepository.findAll(specification, page).map(estoqueMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EstoqueCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Estoque> specification = createSpecification(criteria);
        return estoqueRepository.count(specification);
    }

    /**
     * Function to convert {@link EstoqueCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Estoque> createSpecification(EstoqueCriteria criteria) {
        Specification<Estoque> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Estoque_.id));
            }
            if (criteria.getQuantidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantidade(), Estoque_.quantidade));
            }
            if (criteria.getCriadoEm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCriadoEm(), Estoque_.criadoEm));
            }
            if (criteria.getModificadoEm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModificadoEm(), Estoque_.modificadoEm));
            }
            if (criteria.getProdutoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProdutoId(), root -> root.join(Estoque_.produtos, JoinType.LEFT).get(Produto_.id))
                    );
            }
            if (criteria.getEntradaFinanceiraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEntradaFinanceiraId(),
                            root -> root.join(Estoque_.entradaFinanceiras, JoinType.LEFT).get(EntradaFinanceira_.id)
                        )
                    );
            }
            if (criteria.getSaidaFinanceiraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSaidaFinanceiraId(),
                            root -> root.join(Estoque_.saidaFinanceiras, JoinType.LEFT).get(SaidaFinanceira_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
