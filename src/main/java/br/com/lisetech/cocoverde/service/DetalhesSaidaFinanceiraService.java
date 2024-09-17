package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.DetalhesSaidaFinanceira;
import br.com.lisetech.cocoverde.repository.DetalhesSaidaFinanceiraRepository;
import br.com.lisetech.cocoverde.service.dto.DetalhesSaidaFinanceiraDTO;
import br.com.lisetech.cocoverde.service.mapper.DetalhesSaidaFinanceiraMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.lisetech.cocoverde.domain.DetalhesSaidaFinanceira}.
 */
@Service
@Transactional
public class DetalhesSaidaFinanceiraService {

    private final Logger log = LoggerFactory.getLogger(DetalhesSaidaFinanceiraService.class);

    private final DetalhesSaidaFinanceiraRepository detalhesSaidaFinanceiraRepository;

    private final DetalhesSaidaFinanceiraMapper detalhesSaidaFinanceiraMapper;

    public DetalhesSaidaFinanceiraService(
        DetalhesSaidaFinanceiraRepository detalhesSaidaFinanceiraRepository,
        DetalhesSaidaFinanceiraMapper detalhesSaidaFinanceiraMapper
    ) {
        this.detalhesSaidaFinanceiraRepository = detalhesSaidaFinanceiraRepository;
        this.detalhesSaidaFinanceiraMapper = detalhesSaidaFinanceiraMapper;
    }

    /**
     * Save a detalhesSaidaFinanceira.
     *
     * @param detalhesSaidaFinanceiraDTO the entity to save.
     * @return the persisted entity.
     */
    public DetalhesSaidaFinanceiraDTO save(DetalhesSaidaFinanceiraDTO detalhesSaidaFinanceiraDTO) {
        log.debug("Request to save DetalhesSaidaFinanceira : {}", detalhesSaidaFinanceiraDTO);
        DetalhesSaidaFinanceira detalhesSaidaFinanceira = detalhesSaidaFinanceiraMapper.toEntity(detalhesSaidaFinanceiraDTO);
        detalhesSaidaFinanceira = detalhesSaidaFinanceiraRepository.save(detalhesSaidaFinanceira);
        return detalhesSaidaFinanceiraMapper.toDto(detalhesSaidaFinanceira);
    }

    /**
     * Update a detalhesSaidaFinanceira.
     *
     * @param detalhesSaidaFinanceiraDTO the entity to save.
     * @return the persisted entity.
     */
    public DetalhesSaidaFinanceiraDTO update(DetalhesSaidaFinanceiraDTO detalhesSaidaFinanceiraDTO) {
        log.debug("Request to update DetalhesSaidaFinanceira : {}", detalhesSaidaFinanceiraDTO);
        DetalhesSaidaFinanceira detalhesSaidaFinanceira = detalhesSaidaFinanceiraMapper.toEntity(detalhesSaidaFinanceiraDTO);
        detalhesSaidaFinanceira = detalhesSaidaFinanceiraRepository.save(detalhesSaidaFinanceira);
        return detalhesSaidaFinanceiraMapper.toDto(detalhesSaidaFinanceira);
    }

    /**
     * Partially update a detalhesSaidaFinanceira.
     *
     * @param detalhesSaidaFinanceiraDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DetalhesSaidaFinanceiraDTO> partialUpdate(DetalhesSaidaFinanceiraDTO detalhesSaidaFinanceiraDTO) {
        log.debug("Request to partially update DetalhesSaidaFinanceira : {}", detalhesSaidaFinanceiraDTO);

        return detalhesSaidaFinanceiraRepository
            .findById(detalhesSaidaFinanceiraDTO.getId())
            .map(existingDetalhesSaidaFinanceira -> {
                detalhesSaidaFinanceiraMapper.partialUpdate(existingDetalhesSaidaFinanceira, detalhesSaidaFinanceiraDTO);

                return existingDetalhesSaidaFinanceira;
            })
            .map(detalhesSaidaFinanceiraRepository::save)
            .map(detalhesSaidaFinanceiraMapper::toDto);
    }

    /**
     * Get all the detalhesSaidaFinanceiras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DetalhesSaidaFinanceiraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetalhesSaidaFinanceiras");
        return detalhesSaidaFinanceiraRepository.findAll(pageable).map(detalhesSaidaFinanceiraMapper::toDto);
    }

    /**
     * Get one detalhesSaidaFinanceira by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DetalhesSaidaFinanceiraDTO> findOne(Long id) {
        log.debug("Request to get DetalhesSaidaFinanceira : {}", id);
        return detalhesSaidaFinanceiraRepository.findById(id).map(detalhesSaidaFinanceiraMapper::toDto);
    }

    /**
     * Delete the detalhesSaidaFinanceira by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DetalhesSaidaFinanceira : {}", id);
        detalhesSaidaFinanceiraRepository.deleteById(id);
    }
}
