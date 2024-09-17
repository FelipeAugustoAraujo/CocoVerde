package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.*; // for static metamodels
import br.com.lisetech.cocoverde.domain.DiaTrabalho;
import br.com.lisetech.cocoverde.repository.DiaTrabalhoRepository;
import br.com.lisetech.cocoverde.service.criteria.DiaTrabalhoCriteria;
import br.com.lisetech.cocoverde.service.dto.DiaTrabalhoDTO;
import br.com.lisetech.cocoverde.service.mapper.DiaTrabalhoMapper;
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
 * Service for executing complex queries for {@link DiaTrabalho} entities in the database.
 * The main input is a {@link DiaTrabalhoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DiaTrabalhoDTO} or a {@link Page} of {@link DiaTrabalhoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DiaTrabalhoQueryService extends QueryService<DiaTrabalho> {

    private final Logger log = LoggerFactory.getLogger(DiaTrabalhoQueryService.class);

    private final DiaTrabalhoRepository diaTrabalhoRepository;

    private final DiaTrabalhoMapper diaTrabalhoMapper;

    public DiaTrabalhoQueryService(DiaTrabalhoRepository diaTrabalhoRepository, DiaTrabalhoMapper diaTrabalhoMapper) {
        this.diaTrabalhoRepository = diaTrabalhoRepository;
        this.diaTrabalhoMapper = diaTrabalhoMapper;
    }

    /**
     * Return a {@link List} of {@link DiaTrabalhoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DiaTrabalhoDTO> findByCriteria(DiaTrabalhoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DiaTrabalho> specification = createSpecification(criteria);
        return diaTrabalhoMapper.toDto(diaTrabalhoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DiaTrabalhoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DiaTrabalhoDTO> findByCriteria(DiaTrabalhoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DiaTrabalho> specification = createSpecification(criteria);
        return diaTrabalhoRepository.findAll(specification, page).map(diaTrabalhoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DiaTrabalhoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DiaTrabalho> specification = createSpecification(criteria);
        return diaTrabalhoRepository.count(specification);
    }

    /**
     * Function to convert {@link DiaTrabalhoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DiaTrabalho> createSpecification(DiaTrabalhoCriteria criteria) {
        Specification<DiaTrabalho> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DiaTrabalho_.id));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), DiaTrabalho_.data));
            }
        }
        return specification;
    }
}
