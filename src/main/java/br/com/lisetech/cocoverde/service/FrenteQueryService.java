package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.*; // for static metamodels
import br.com.lisetech.cocoverde.domain.Frente;
import br.com.lisetech.cocoverde.repository.FrenteRepository;
import br.com.lisetech.cocoverde.service.criteria.FrenteCriteria;
import br.com.lisetech.cocoverde.service.dto.FrenteDTO;
import br.com.lisetech.cocoverde.service.mapper.FrenteMapper;
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
 * Service for executing complex queries for {@link Frente} entities in the database.
 * The main input is a {@link FrenteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FrenteDTO} or a {@link Page} of {@link FrenteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FrenteQueryService extends QueryService<Frente> {

    private final Logger log = LoggerFactory.getLogger(FrenteQueryService.class);

    private final FrenteRepository frenteRepository;

    private final FrenteMapper frenteMapper;

    public FrenteQueryService(FrenteRepository frenteRepository, FrenteMapper frenteMapper) {
        this.frenteRepository = frenteRepository;
        this.frenteMapper = frenteMapper;
    }

    /**
     * Return a {@link List} of {@link FrenteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FrenteDTO> findByCriteria(FrenteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Frente> specification = createSpecification(criteria);
        return frenteMapper.toDto(frenteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FrenteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FrenteDTO> findByCriteria(FrenteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Frente> specification = createSpecification(criteria);
        return frenteRepository.findAll(specification, page).map(frenteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FrenteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Frente> specification = createSpecification(criteria);
        return frenteRepository.count(specification);
    }

    /**
     * Function to convert {@link FrenteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Frente> createSpecification(FrenteCriteria criteria) {
        Specification<Frente> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Frente_.id));
            }
            if (criteria.getQuantidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantidade(), Frente_.quantidade));
            }
            if (criteria.getCriadoEm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCriadoEm(), Frente_.criadoEm));
            }
            if (criteria.getModificadoEm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModificadoEm(), Frente_.modificadoEm));
            }
            if (criteria.getProdutoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProdutoId(), root -> root.join(Frente_.produtos, JoinType.LEFT).get(Produto_.id))
                    );
            }
            if (criteria.getEntradaFinanceiraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEntradaFinanceiraId(),
                            root -> root.join(Frente_.entradaFinanceiras, JoinType.LEFT).get(EntradaFinanceira_.id)
                        )
                    );
            }
            if (criteria.getSaidaFinanceiraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSaidaFinanceiraId(),
                            root -> root.join(Frente_.saidaFinanceiras, JoinType.LEFT).get(SaidaFinanceira_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
