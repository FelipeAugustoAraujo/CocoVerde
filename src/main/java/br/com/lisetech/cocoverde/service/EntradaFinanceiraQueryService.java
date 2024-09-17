package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.*; // for static metamodels
import br.com.lisetech.cocoverde.domain.EntradaFinanceira;
import br.com.lisetech.cocoverde.repository.EntradaFinanceiraRepository;
import br.com.lisetech.cocoverde.service.criteria.EntradaFinanceiraCriteria;
import br.com.lisetech.cocoverde.service.dto.EntradaFinanceiraDTO;
import br.com.lisetech.cocoverde.service.mapper.EntradaFinanceiraMapper;
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
 * Service for executing complex queries for {@link EntradaFinanceira} entities in the database.
 * The main input is a {@link EntradaFinanceiraCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EntradaFinanceiraDTO} or a {@link Page} of {@link EntradaFinanceiraDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EntradaFinanceiraQueryService extends QueryService<EntradaFinanceira> {

    private final Logger log = LoggerFactory.getLogger(EntradaFinanceiraQueryService.class);

    private final EntradaFinanceiraRepository entradaFinanceiraRepository;

    private final EntradaFinanceiraMapper entradaFinanceiraMapper;

    public EntradaFinanceiraQueryService(
        EntradaFinanceiraRepository entradaFinanceiraRepository,
        EntradaFinanceiraMapper entradaFinanceiraMapper
    ) {
        this.entradaFinanceiraRepository = entradaFinanceiraRepository;
        this.entradaFinanceiraMapper = entradaFinanceiraMapper;
    }

    /**
     * Return a {@link List} of {@link EntradaFinanceiraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EntradaFinanceiraDTO> findByCriteria(EntradaFinanceiraCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EntradaFinanceira> specification = createSpecification(criteria);
        return entradaFinanceiraMapper.toDto(entradaFinanceiraRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EntradaFinanceiraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EntradaFinanceiraDTO> findByCriteria(EntradaFinanceiraCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EntradaFinanceira> specification = createSpecification(criteria);
        return entradaFinanceiraRepository.findAll(specification, page).map(entradaFinanceiraMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EntradaFinanceiraCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EntradaFinanceira> specification = createSpecification(criteria);
        return entradaFinanceiraRepository.count(specification);
    }

    /**
     * Function to convert {@link EntradaFinanceiraCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EntradaFinanceira> createSpecification(EntradaFinanceiraCriteria criteria) {
        Specification<EntradaFinanceira> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EntradaFinanceira_.id));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), EntradaFinanceira_.data));
            }
            if (criteria.getValorTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorTotal(), EntradaFinanceira_.valorTotal));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), EntradaFinanceira_.descricao));
            }
            if (criteria.getMetodoPagamento() != null) {
                specification = specification.and(buildSpecification(criteria.getMetodoPagamento(), EntradaFinanceira_.metodoPagamento));
            }
            if (criteria.getStatusPagamento() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusPagamento(), EntradaFinanceira_.statusPagamento));
            }
            if (criteria.getResponsavelPagamento() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getResponsavelPagamento(), EntradaFinanceira_.responsavelPagamento));
            }
            if (criteria.getFornecedorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFornecedorId(),
                            root -> root.join(EntradaFinanceira_.fornecedor, JoinType.LEFT).get(Fornecedor_.id)
                        )
                    );
            }
            if (criteria.getEstoqueId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEstoqueId(),
                            root -> root.join(EntradaFinanceira_.estoque, JoinType.LEFT).get(Estoque_.id)
                        )
                    );
            }
            if (criteria.getFrenteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFrenteId(),
                            root -> root.join(EntradaFinanceira_.frente, JoinType.LEFT).get(Frente_.id)
                        )
                    );
            }
            if (criteria.getFechamentoCaixaDetalhesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFechamentoCaixaDetalhesId(),
                            root -> root.join(EntradaFinanceira_.fechamentoCaixaDetalhes, JoinType.LEFT).get(FechamentoCaixaDetalhes_.id)
                        )
                    );
            }
            if (criteria.getDetalhesEntradaFinanceiraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDetalhesEntradaFinanceiraId(),
                            root ->
                                root.join(EntradaFinanceira_.detalhesEntradaFinanceiras, JoinType.LEFT).get(DetalhesEntradaFinanceira_.id)
                        )
                    );
            }
            if (criteria.getImagemId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getImagemId(),
                            root -> root.join(EntradaFinanceira_.imagems, JoinType.LEFT).get(Imagem_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
