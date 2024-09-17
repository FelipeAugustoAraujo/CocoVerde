package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.*; // for static metamodels
import br.com.lisetech.cocoverde.domain.SaidaFinanceira;
import br.com.lisetech.cocoverde.repository.SaidaFinanceiraRepository;
import br.com.lisetech.cocoverde.service.criteria.SaidaFinanceiraCriteria;
import br.com.lisetech.cocoverde.service.dto.SaidaFinanceiraDTO;
import br.com.lisetech.cocoverde.service.mapper.SaidaFinanceiraMapper;
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
 * Service for executing complex queries for {@link SaidaFinanceira} entities in the database.
 * The main input is a {@link SaidaFinanceiraCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SaidaFinanceiraDTO} or a {@link Page} of {@link SaidaFinanceiraDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SaidaFinanceiraQueryService extends QueryService<SaidaFinanceira> {

    private final Logger log = LoggerFactory.getLogger(SaidaFinanceiraQueryService.class);

    private final SaidaFinanceiraRepository saidaFinanceiraRepository;

    private final SaidaFinanceiraMapper saidaFinanceiraMapper;

    public SaidaFinanceiraQueryService(SaidaFinanceiraRepository saidaFinanceiraRepository, SaidaFinanceiraMapper saidaFinanceiraMapper) {
        this.saidaFinanceiraRepository = saidaFinanceiraRepository;
        this.saidaFinanceiraMapper = saidaFinanceiraMapper;
    }

    /**
     * Return a {@link List} of {@link SaidaFinanceiraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SaidaFinanceiraDTO> findByCriteria(SaidaFinanceiraCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SaidaFinanceira> specification = createSpecification(criteria);
        return saidaFinanceiraMapper.toDto(saidaFinanceiraRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SaidaFinanceiraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SaidaFinanceiraDTO> findByCriteria(SaidaFinanceiraCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SaidaFinanceira> specification = createSpecification(criteria);
        return saidaFinanceiraRepository.findAll(specification, page).map(saidaFinanceiraMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SaidaFinanceiraCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SaidaFinanceira> specification = createSpecification(criteria);
        return saidaFinanceiraRepository.count(specification);
    }

    /**
     * Function to convert {@link SaidaFinanceiraCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SaidaFinanceira> createSpecification(SaidaFinanceiraCriteria criteria) {
        Specification<SaidaFinanceira> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SaidaFinanceira_.id));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), SaidaFinanceira_.data));
            }
            if (criteria.getValorTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorTotal(), SaidaFinanceira_.valorTotal));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), SaidaFinanceira_.descricao));
            }
            if (criteria.getMetodoPagamento() != null) {
                specification = specification.and(buildSpecification(criteria.getMetodoPagamento(), SaidaFinanceira_.metodoPagamento));
            }
            if (criteria.getStatusPagamento() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusPagamento(), SaidaFinanceira_.statusPagamento));
            }
            if (criteria.getResponsavelPagamento() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getResponsavelPagamento(), SaidaFinanceira_.responsavelPagamento));
            }
            if (criteria.getEstoqueId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEstoqueId(),
                            root -> root.join(SaidaFinanceira_.estoque, JoinType.LEFT).get(Estoque_.id)
                        )
                    );
            }
            if (criteria.getFrenteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFrenteId(),
                            root -> root.join(SaidaFinanceira_.frente, JoinType.LEFT).get(Frente_.id)
                        )
                    );
            }
            if (criteria.getFechamentoCaixaDetalhesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFechamentoCaixaDetalhesId(),
                            root -> root.join(SaidaFinanceira_.fechamentoCaixaDetalhes, JoinType.LEFT).get(FechamentoCaixaDetalhes_.id)
                        )
                    );
            }
            if (criteria.getImagemId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getImagemId(),
                            root -> root.join(SaidaFinanceira_.imagems, JoinType.LEFT).get(Imagem_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
