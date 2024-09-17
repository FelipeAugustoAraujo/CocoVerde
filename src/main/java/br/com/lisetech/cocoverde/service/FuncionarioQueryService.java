package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.*; // for static metamodels
import br.com.lisetech.cocoverde.domain.Funcionario;
import br.com.lisetech.cocoverde.repository.FuncionarioRepository;
import br.com.lisetech.cocoverde.service.criteria.FuncionarioCriteria;
import br.com.lisetech.cocoverde.service.dto.FuncionarioDTO;
import br.com.lisetech.cocoverde.service.mapper.FuncionarioMapper;
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
 * Service for executing complex queries for {@link Funcionario} entities in the database.
 * The main input is a {@link FuncionarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FuncionarioDTO} or a {@link Page} of {@link FuncionarioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FuncionarioQueryService extends QueryService<Funcionario> {

    private final Logger log = LoggerFactory.getLogger(FuncionarioQueryService.class);

    private final FuncionarioRepository funcionarioRepository;

    private final FuncionarioMapper funcionarioMapper;

    public FuncionarioQueryService(FuncionarioRepository funcionarioRepository, FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioMapper = funcionarioMapper;
    }

    /**
     * Return a {@link List} of {@link FuncionarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FuncionarioDTO> findByCriteria(FuncionarioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Funcionario> specification = createSpecification(criteria);
        return funcionarioMapper.toDto(funcionarioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FuncionarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FuncionarioDTO> findByCriteria(FuncionarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Funcionario> specification = createSpecification(criteria);
        return funcionarioRepository.findAll(specification, page).map(funcionarioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FuncionarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Funcionario> specification = createSpecification(criteria);
        return funcionarioRepository.count(specification);
    }

    /**
     * Function to convert {@link FuncionarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Funcionario> createSpecification(FuncionarioCriteria criteria) {
        Specification<Funcionario> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Funcionario_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Funcionario_.nome));
            }
            if (criteria.getDataNascimento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDataNascimento(), Funcionario_.dataNascimento));
            }
            if (criteria.getIdentificador() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdentificador(), Funcionario_.identificador));
            }
            if (criteria.getTelefone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefone(), Funcionario_.telefone));
            }
            if (criteria.getDataCadastro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataCadastro(), Funcionario_.dataCadastro));
            }
            if (criteria.getValorBase() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorBase(), Funcionario_.valorBase));
            }
            if (criteria.getEnderecoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEnderecoId(),
                            root -> root.join(Funcionario_.endereco, JoinType.LEFT).get(Endereco_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
