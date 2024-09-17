package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.SaidaFinanceira;
import br.com.lisetech.cocoverde.repository.SaidaFinanceiraRepository;
import br.com.lisetech.cocoverde.service.dto.SaidaFinanceiraDTO;
import br.com.lisetech.cocoverde.service.mapper.SaidaFinanceiraMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.lisetech.cocoverde.domain.SaidaFinanceira}.
 */
@Service
@Transactional
public class SaidaFinanceiraService {

    private final Logger log = LoggerFactory.getLogger(SaidaFinanceiraService.class);

    private final SaidaFinanceiraRepository saidaFinanceiraRepository;

    private final SaidaFinanceiraMapper saidaFinanceiraMapper;

    public SaidaFinanceiraService(SaidaFinanceiraRepository saidaFinanceiraRepository, SaidaFinanceiraMapper saidaFinanceiraMapper) {
        this.saidaFinanceiraRepository = saidaFinanceiraRepository;
        this.saidaFinanceiraMapper = saidaFinanceiraMapper;
    }

    /**
     * Save a saidaFinanceira.
     *
     * @param saidaFinanceiraDTO the entity to save.
     * @return the persisted entity.
     */
    public SaidaFinanceiraDTO save(SaidaFinanceiraDTO saidaFinanceiraDTO) {
        log.debug("Request to save SaidaFinanceira : {}", saidaFinanceiraDTO);
        SaidaFinanceira saidaFinanceira = saidaFinanceiraMapper.toEntity(saidaFinanceiraDTO);
        saidaFinanceira = saidaFinanceiraRepository.save(saidaFinanceira);
        return saidaFinanceiraMapper.toDto(saidaFinanceira);
    }

    /**
     * Update a saidaFinanceira.
     *
     * @param saidaFinanceiraDTO the entity to save.
     * @return the persisted entity.
     */
    public SaidaFinanceiraDTO update(SaidaFinanceiraDTO saidaFinanceiraDTO) {
        log.debug("Request to update SaidaFinanceira : {}", saidaFinanceiraDTO);
        SaidaFinanceira saidaFinanceira = saidaFinanceiraMapper.toEntity(saidaFinanceiraDTO);
        saidaFinanceira = saidaFinanceiraRepository.save(saidaFinanceira);
        return saidaFinanceiraMapper.toDto(saidaFinanceira);
    }

    /**
     * Partially update a saidaFinanceira.
     *
     * @param saidaFinanceiraDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SaidaFinanceiraDTO> partialUpdate(SaidaFinanceiraDTO saidaFinanceiraDTO) {
        log.debug("Request to partially update SaidaFinanceira : {}", saidaFinanceiraDTO);

        return saidaFinanceiraRepository
            .findById(saidaFinanceiraDTO.getId())
            .map(existingSaidaFinanceira -> {
                saidaFinanceiraMapper.partialUpdate(existingSaidaFinanceira, saidaFinanceiraDTO);

                return existingSaidaFinanceira;
            })
            .map(saidaFinanceiraRepository::save)
            .map(saidaFinanceiraMapper::toDto);
    }

    /**
     * Get all the saidaFinanceiras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SaidaFinanceiraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SaidaFinanceiras");
        return saidaFinanceiraRepository.findAll(pageable).map(saidaFinanceiraMapper::toDto);
    }

    /**
     * Get one saidaFinanceira by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SaidaFinanceiraDTO> findOne(Long id) {
        log.debug("Request to get SaidaFinanceira : {}", id);
        return saidaFinanceiraRepository.findById(id).map(saidaFinanceiraMapper::toDto);
    }

    /**
     * Delete the saidaFinanceira by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SaidaFinanceira : {}", id);
        saidaFinanceiraRepository.deleteById(id);
    }
}
