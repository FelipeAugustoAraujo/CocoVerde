package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.DetalhesEntradaFinanceira;
import br.com.lisetech.cocoverde.repository.DetalhesEntradaFinanceiraRepository;
import br.com.lisetech.cocoverde.service.dto.DetalhesEntradaFinanceiraDTO;
import br.com.lisetech.cocoverde.service.mapper.DetalhesEntradaFinanceiraMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.lisetech.cocoverde.domain.DetalhesEntradaFinanceira}.
 */
@Service
@Transactional
public class DetalhesEntradaFinanceiraService {

    private final Logger log = LoggerFactory.getLogger(DetalhesEntradaFinanceiraService.class);

    private final DetalhesEntradaFinanceiraRepository detalhesEntradaFinanceiraRepository;

    private final DetalhesEntradaFinanceiraMapper detalhesEntradaFinanceiraMapper;

    public DetalhesEntradaFinanceiraService(
        DetalhesEntradaFinanceiraRepository detalhesEntradaFinanceiraRepository,
        DetalhesEntradaFinanceiraMapper detalhesEntradaFinanceiraMapper
    ) {
        this.detalhesEntradaFinanceiraRepository = detalhesEntradaFinanceiraRepository;
        this.detalhesEntradaFinanceiraMapper = detalhesEntradaFinanceiraMapper;
    }

    /**
     * Save a detalhesEntradaFinanceira.
     *
     * @param detalhesEntradaFinanceiraDTO the entity to save.
     * @return the persisted entity.
     */
    public DetalhesEntradaFinanceiraDTO save(DetalhesEntradaFinanceiraDTO detalhesEntradaFinanceiraDTO) {
        log.debug("Request to save DetalhesEntradaFinanceira : {}", detalhesEntradaFinanceiraDTO);
        DetalhesEntradaFinanceira detalhesEntradaFinanceira = detalhesEntradaFinanceiraMapper.toEntity(detalhesEntradaFinanceiraDTO);
        detalhesEntradaFinanceira = detalhesEntradaFinanceiraRepository.save(detalhesEntradaFinanceira);
        return detalhesEntradaFinanceiraMapper.toDto(detalhesEntradaFinanceira);
    }

    /**
     * Update a detalhesEntradaFinanceira.
     *
     * @param detalhesEntradaFinanceiraDTO the entity to save.
     * @return the persisted entity.
     */
    public DetalhesEntradaFinanceiraDTO update(DetalhesEntradaFinanceiraDTO detalhesEntradaFinanceiraDTO) {
        log.debug("Request to update DetalhesEntradaFinanceira : {}", detalhesEntradaFinanceiraDTO);
        DetalhesEntradaFinanceira detalhesEntradaFinanceira = detalhesEntradaFinanceiraMapper.toEntity(detalhesEntradaFinanceiraDTO);
        detalhesEntradaFinanceira = detalhesEntradaFinanceiraRepository.save(detalhesEntradaFinanceira);
        return detalhesEntradaFinanceiraMapper.toDto(detalhesEntradaFinanceira);
    }

    /**
     * Partially update a detalhesEntradaFinanceira.
     *
     * @param detalhesEntradaFinanceiraDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DetalhesEntradaFinanceiraDTO> partialUpdate(DetalhesEntradaFinanceiraDTO detalhesEntradaFinanceiraDTO) {
        log.debug("Request to partially update DetalhesEntradaFinanceira : {}", detalhesEntradaFinanceiraDTO);

        return detalhesEntradaFinanceiraRepository
            .findById(detalhesEntradaFinanceiraDTO.getId())
            .map(existingDetalhesEntradaFinanceira -> {
                detalhesEntradaFinanceiraMapper.partialUpdate(existingDetalhesEntradaFinanceira, detalhesEntradaFinanceiraDTO);

                return existingDetalhesEntradaFinanceira;
            })
            .map(detalhesEntradaFinanceiraRepository::save)
            .map(detalhesEntradaFinanceiraMapper::toDto);
    }

    /**
     * Get all the detalhesEntradaFinanceiras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DetalhesEntradaFinanceiraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetalhesEntradaFinanceiras");
        return detalhesEntradaFinanceiraRepository.findAll(pageable).map(detalhesEntradaFinanceiraMapper::toDto);
    }

    /**
     * Get one detalhesEntradaFinanceira by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DetalhesEntradaFinanceiraDTO> findOne(Long id) {
        log.debug("Request to get DetalhesEntradaFinanceira : {}", id);
        return detalhesEntradaFinanceiraRepository.findById(id).map(detalhesEntradaFinanceiraMapper::toDto);
    }

    /**
     * Delete the detalhesEntradaFinanceira by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DetalhesEntradaFinanceira : {}", id);
        detalhesEntradaFinanceiraRepository.deleteById(id);
    }
}
