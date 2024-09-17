package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.*; // for static metamodels
import br.com.lisetech.cocoverde.domain.Fornecedor;
import br.com.lisetech.cocoverde.repository.FornecedorRepository;
import br.com.lisetech.cocoverde.service.criteria.FornecedorCriteria;
import br.com.lisetech.cocoverde.service.dto.FornecedorDTO;
import br.com.lisetech.cocoverde.service.mapper.FornecedorMapper;
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
 * Service for executing complex queries for {@link Fornecedor} entities in the database.
 * The main input is a {@link FornecedorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FornecedorDTO} or a {@link Page} of {@link FornecedorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FornecedorQueryService extends QueryService<Fornecedor> {

    private final Logger log = LoggerFactory.getLogger(FornecedorQueryService.class);

    private final FornecedorRepository fornecedorRepository;

    private final FornecedorMapper fornecedorMapper;

    public FornecedorQueryService(FornecedorRepository fornecedorRepository, FornecedorMapper fornecedorMapper) {
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorMapper = fornecedorMapper;
    }

    /**
     * Return a {@link List} of {@link FornecedorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FornecedorDTO> findByCriteria(FornecedorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Fornecedor> specification = createSpecification(criteria);
        return fornecedorMapper.toDto(fornecedorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FornecedorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FornecedorDTO> findByCriteria(FornecedorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Fornecedor> specification = createSpecification(criteria);
        return fornecedorRepository.findAll(specification, page).map(fornecedorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FornecedorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Fornecedor> specification = createSpecification(criteria);
        return fornecedorRepository.count(specification);
    }

    /**
     * Function to convert {@link FornecedorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Fornecedor> createSpecification(FornecedorCriteria criteria) {
        Specification<Fornecedor> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Fornecedor_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Fornecedor_.nome));
            }
            if (criteria.getIdentificador() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdentificador(), Fornecedor_.identificador));
            }
            if (criteria.getTelefone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefone(), Fornecedor_.telefone));
            }
            if (criteria.getDataCadastro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataCadastro(), Fornecedor_.dataCadastro));
            }
            if (criteria.getProdutoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProdutoId(), root -> root.join(Fornecedor_.produtos, JoinType.LEFT).get(Produto_.id))
                    );
            }
            if (criteria.getEnderecoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEnderecoId(),
                            root -> root.join(Fornecedor_.endereco, JoinType.LEFT).get(Endereco_.id)
                        )
                    );
            }
            if (criteria.getEntradaFinanceiraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEntradaFinanceiraId(),
                            root -> root.join(Fornecedor_.entradaFinanceiras, JoinType.LEFT).get(EntradaFinanceira_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
