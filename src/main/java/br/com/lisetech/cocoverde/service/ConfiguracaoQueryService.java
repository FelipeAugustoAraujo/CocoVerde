package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.*; // for static metamodels
import br.com.lisetech.cocoverde.domain.Configuracao;
import br.com.lisetech.cocoverde.repository.ConfiguracaoRepository;
import br.com.lisetech.cocoverde.service.criteria.ConfiguracaoCriteria;
import br.com.lisetech.cocoverde.service.dto.ConfiguracaoDTO;
import br.com.lisetech.cocoverde.service.mapper.ConfiguracaoMapper;
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
 * Service for executing complex queries for {@link Configuracao} entities in the database.
 * The main input is a {@link ConfiguracaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConfiguracaoDTO} or a {@link Page} of {@link ConfiguracaoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConfiguracaoQueryService extends QueryService<Configuracao> {

    private final Logger log = LoggerFactory.getLogger(ConfiguracaoQueryService.class);

    private final ConfiguracaoRepository configuracaoRepository;

    private final ConfiguracaoMapper configuracaoMapper;

    public ConfiguracaoQueryService(ConfiguracaoRepository configuracaoRepository, ConfiguracaoMapper configuracaoMapper) {
        this.configuracaoRepository = configuracaoRepository;
        this.configuracaoMapper = configuracaoMapper;
    }

    /**
     * Return a {@link List} of {@link ConfiguracaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConfiguracaoDTO> findByCriteria(ConfiguracaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Configuracao> specification = createSpecification(criteria);
        return configuracaoMapper.toDto(configuracaoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ConfiguracaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConfiguracaoDTO> findByCriteria(ConfiguracaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Configuracao> specification = createSpecification(criteria);
        return configuracaoRepository.findAll(specification, page).map(configuracaoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConfiguracaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Configuracao> specification = createSpecification(criteria);
        return configuracaoRepository.count(specification);
    }

    /**
     * Function to convert {@link ConfiguracaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Configuracao> createSpecification(ConfiguracaoCriteria criteria) {
        Specification<Configuracao> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Configuracao_.id));
            }
        }
        return specification;
    }
}
