package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.*; // for static metamodels
import br.com.lisetech.cocoverde.domain.Imagem;
import br.com.lisetech.cocoverde.repository.ImagemRepository;
import br.com.lisetech.cocoverde.service.criteria.ImagemCriteria;
import br.com.lisetech.cocoverde.service.dto.ImagemDTO;
import br.com.lisetech.cocoverde.service.mapper.ImagemMapper;
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
 * Service for executing complex queries for {@link Imagem} entities in the database.
 * The main input is a {@link ImagemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ImagemDTO} or a {@link Page} of {@link ImagemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ImagemQueryService extends QueryService<Imagem> {

    private final Logger log = LoggerFactory.getLogger(ImagemQueryService.class);

    private final ImagemRepository imagemRepository;

    private final ImagemMapper imagemMapper;

    public ImagemQueryService(ImagemRepository imagemRepository, ImagemMapper imagemMapper) {
        this.imagemRepository = imagemRepository;
        this.imagemMapper = imagemMapper;
    }

    /**
     * Return a {@link List} of {@link ImagemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ImagemDTO> findByCriteria(ImagemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Imagem> specification = createSpecification(criteria);
        return imagemMapper.toDto(imagemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ImagemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ImagemDTO> findByCriteria(ImagemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Imagem> specification = createSpecification(criteria);
        return imagemRepository.findAll(specification, page).map(imagemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ImagemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Imagem> specification = createSpecification(criteria);
        return imagemRepository.count(specification);
    }

    /**
     * Function to convert {@link ImagemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Imagem> createSpecification(ImagemCriteria criteria) {
        Specification<Imagem> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Imagem_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Imagem_.name));
            }
            if (criteria.getContentType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContentType(), Imagem_.contentType));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Imagem_.description));
            }
            if (criteria.getSaidaFinanceiraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSaidaFinanceiraId(),
                            root -> root.join(Imagem_.saidaFinanceira, JoinType.LEFT).get(SaidaFinanceira_.id)
                        )
                    );
            }
            if (criteria.getEntradaFinanceiraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEntradaFinanceiraId(),
                            root -> root.join(Imagem_.entradaFinanceira, JoinType.LEFT).get(EntradaFinanceira_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
