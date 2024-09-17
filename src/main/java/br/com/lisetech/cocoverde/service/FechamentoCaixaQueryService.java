package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.*; // for static metamodels
import br.com.lisetech.cocoverde.domain.FechamentoCaixa;
import br.com.lisetech.cocoverde.repository.FechamentoCaixaRepository;
import br.com.lisetech.cocoverde.service.criteria.FechamentoCaixaCriteria;
import br.com.lisetech.cocoverde.service.dto.FechamentoCaixaDTO;
import br.com.lisetech.cocoverde.service.mapper.FechamentoCaixaMapper;
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
 * Service for executing complex queries for {@link FechamentoCaixa} entities in the database.
 * The main input is a {@link FechamentoCaixaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FechamentoCaixaDTO} or a {@link Page} of {@link FechamentoCaixaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FechamentoCaixaQueryService extends QueryService<FechamentoCaixa> {

    private final Logger log = LoggerFactory.getLogger(FechamentoCaixaQueryService.class);

    private final FechamentoCaixaRepository fechamentoCaixaRepository;

    private final FechamentoCaixaMapper fechamentoCaixaMapper;

    public FechamentoCaixaQueryService(FechamentoCaixaRepository fechamentoCaixaRepository, FechamentoCaixaMapper fechamentoCaixaMapper) {
        this.fechamentoCaixaRepository = fechamentoCaixaRepository;
        this.fechamentoCaixaMapper = fechamentoCaixaMapper;
    }

    /**
     * Return a {@link List} of {@link FechamentoCaixaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FechamentoCaixaDTO> findByCriteria(FechamentoCaixaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FechamentoCaixa> specification = createSpecification(criteria);
        return fechamentoCaixaMapper.toDto(fechamentoCaixaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FechamentoCaixaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FechamentoCaixaDTO> findByCriteria(FechamentoCaixaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FechamentoCaixa> specification = createSpecification(criteria);
        return fechamentoCaixaRepository.findAll(specification, page).map(fechamentoCaixaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FechamentoCaixaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FechamentoCaixa> specification = createSpecification(criteria);
        return fechamentoCaixaRepository.count(specification);
    }

    /**
     * Function to convert {@link FechamentoCaixaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FechamentoCaixa> createSpecification(FechamentoCaixaCriteria criteria) {
        Specification<FechamentoCaixa> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FechamentoCaixa_.id));
            }
            if (criteria.getDataInicial() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataInicial(), FechamentoCaixa_.dataInicial));
            }
            if (criteria.getDataFinal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataFinal(), FechamentoCaixa_.dataFinal));
            }
            if (criteria.getQuantidadeCocosPerdidos() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getQuantidadeCocosPerdidos(), FechamentoCaixa_.quantidadeCocosPerdidos)
                    );
            }
            if (criteria.getQuantidadeCocosVendidos() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getQuantidadeCocosVendidos(), FechamentoCaixa_.quantidadeCocosVendidos)
                    );
            }
            if (criteria.getQuantidadeCocoSobrou() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getQuantidadeCocoSobrou(), FechamentoCaixa_.quantidadeCocoSobrou));
            }
            if (criteria.getDivididoPor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDivididoPor(), FechamentoCaixa_.divididoPor));
            }
            if (criteria.getValorTotalCoco() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorTotalCoco(), FechamentoCaixa_.valorTotalCoco));
            }
            if (criteria.getValorTotalCocoPerdido() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getValorTotalCocoPerdido(), FechamentoCaixa_.valorTotalCocoPerdido));
            }
            if (criteria.getValorPorPessoa() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorPorPessoa(), FechamentoCaixa_.valorPorPessoa));
            }
            if (criteria.getValorDespesas() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorDespesas(), FechamentoCaixa_.valorDespesas));
            }
            if (criteria.getValorDinheiro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorDinheiro(), FechamentoCaixa_.valorDinheiro));
            }
            if (criteria.getValorCartao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorCartao(), FechamentoCaixa_.valorCartao));
            }
            if (criteria.getValorTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorTotal(), FechamentoCaixa_.valorTotal));
            }
            if (criteria.getFechamentoCaixaDetalhesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFechamentoCaixaDetalhesId(),
                            root -> root.join(FechamentoCaixa_.fechamentoCaixaDetalhes, JoinType.LEFT).get(FechamentoCaixaDetalhes_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
