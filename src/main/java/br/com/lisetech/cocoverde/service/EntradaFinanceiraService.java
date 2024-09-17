package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.EntradaFinanceira;
import br.com.lisetech.cocoverde.repository.EntradaFinanceiraRepository;
import br.com.lisetech.cocoverde.service.dto.EntradaFinanceiraDTO;
import br.com.lisetech.cocoverde.service.mapper.EntradaFinanceiraMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.lisetech.cocoverde.domain.EntradaFinanceira}.
 */
@Service
@Transactional
public class EntradaFinanceiraService {

    private final Logger log = LoggerFactory.getLogger(EntradaFinanceiraService.class);

    private final EntradaFinanceiraRepository entradaFinanceiraRepository;

    private final EntradaFinanceiraMapper entradaFinanceiraMapper;

    public EntradaFinanceiraService(
        EntradaFinanceiraRepository entradaFinanceiraRepository,
        EntradaFinanceiraMapper entradaFinanceiraMapper
    ) {
        this.entradaFinanceiraRepository = entradaFinanceiraRepository;
        this.entradaFinanceiraMapper = entradaFinanceiraMapper;
    }

    /**
     * Save a entradaFinanceira.
     *
     * @param entradaFinanceiraDTO the entity to save.
     * @return the persisted entity.
     */
    public EntradaFinanceiraDTO save(EntradaFinanceiraDTO entradaFinanceiraDTO) {
        log.debug("Request to save EntradaFinanceira : {}", entradaFinanceiraDTO);
        EntradaFinanceira entradaFinanceira = entradaFinanceiraMapper.toEntity(entradaFinanceiraDTO);
        entradaFinanceira = entradaFinanceiraRepository.save(entradaFinanceira);
        return entradaFinanceiraMapper.toDto(entradaFinanceira);
    }

    /**
     * Update a entradaFinanceira.
     *
     * @param entradaFinanceiraDTO the entity to save.
     * @return the persisted entity.
     */
    public EntradaFinanceiraDTO update(EntradaFinanceiraDTO entradaFinanceiraDTO) {
        log.debug("Request to update EntradaFinanceira : {}", entradaFinanceiraDTO);
        EntradaFinanceira entradaFinanceira = entradaFinanceiraMapper.toEntity(entradaFinanceiraDTO);
        entradaFinanceira = entradaFinanceiraRepository.save(entradaFinanceira);
        return entradaFinanceiraMapper.toDto(entradaFinanceira);
    }

    /**
     * Partially update a entradaFinanceira.
     *
     * @param entradaFinanceiraDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EntradaFinanceiraDTO> partialUpdate(EntradaFinanceiraDTO entradaFinanceiraDTO) {
        log.debug("Request to partially update EntradaFinanceira : {}", entradaFinanceiraDTO);

        return entradaFinanceiraRepository
            .findById(entradaFinanceiraDTO.getId())
            .map(existingEntradaFinanceira -> {
                entradaFinanceiraMapper.partialUpdate(existingEntradaFinanceira, entradaFinanceiraDTO);

                return existingEntradaFinanceira;
            })
            .map(entradaFinanceiraRepository::save)
            .map(entradaFinanceiraMapper::toDto);
    }

    /**
     * Get all the entradaFinanceiras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EntradaFinanceiraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EntradaFinanceiras");
        return entradaFinanceiraRepository.findAll(pageable).map(entradaFinanceiraMapper::toDto);
    }

    /**
     * Get one entradaFinanceira by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EntradaFinanceiraDTO> findOne(Long id) {
        log.debug("Request to get EntradaFinanceira : {}", id);
        return entradaFinanceiraRepository.findById(id).map(entradaFinanceiraMapper::toDto);
    }

    /**
     * Delete the entradaFinanceira by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EntradaFinanceira : {}", id);
        entradaFinanceiraRepository.deleteById(id);
    }
}
