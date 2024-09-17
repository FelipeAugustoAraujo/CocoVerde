package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.FechamentoCaixa;
import br.com.lisetech.cocoverde.repository.FechamentoCaixaRepository;
import br.com.lisetech.cocoverde.service.dto.FechamentoCaixaDTO;
import br.com.lisetech.cocoverde.service.mapper.FechamentoCaixaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.lisetech.cocoverde.domain.FechamentoCaixa}.
 */
@Service
@Transactional
public class FechamentoCaixaService {

    private final Logger log = LoggerFactory.getLogger(FechamentoCaixaService.class);

    private final FechamentoCaixaRepository fechamentoCaixaRepository;

    private final FechamentoCaixaMapper fechamentoCaixaMapper;

    public FechamentoCaixaService(FechamentoCaixaRepository fechamentoCaixaRepository, FechamentoCaixaMapper fechamentoCaixaMapper) {
        this.fechamentoCaixaRepository = fechamentoCaixaRepository;
        this.fechamentoCaixaMapper = fechamentoCaixaMapper;
    }

    /**
     * Save a fechamentoCaixa.
     *
     * @param fechamentoCaixaDTO the entity to save.
     * @return the persisted entity.
     */
    public FechamentoCaixaDTO save(FechamentoCaixaDTO fechamentoCaixaDTO) {
        log.debug("Request to save FechamentoCaixa : {}", fechamentoCaixaDTO);
        FechamentoCaixa fechamentoCaixa = fechamentoCaixaMapper.toEntity(fechamentoCaixaDTO);
        fechamentoCaixa = fechamentoCaixaRepository.save(fechamentoCaixa);
        return fechamentoCaixaMapper.toDto(fechamentoCaixa);
    }

    /**
     * Update a fechamentoCaixa.
     *
     * @param fechamentoCaixaDTO the entity to save.
     * @return the persisted entity.
     */
    public FechamentoCaixaDTO update(FechamentoCaixaDTO fechamentoCaixaDTO) {
        log.debug("Request to update FechamentoCaixa : {}", fechamentoCaixaDTO);
        FechamentoCaixa fechamentoCaixa = fechamentoCaixaMapper.toEntity(fechamentoCaixaDTO);
        fechamentoCaixa = fechamentoCaixaRepository.save(fechamentoCaixa);
        return fechamentoCaixaMapper.toDto(fechamentoCaixa);
    }

    /**
     * Partially update a fechamentoCaixa.
     *
     * @param fechamentoCaixaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FechamentoCaixaDTO> partialUpdate(FechamentoCaixaDTO fechamentoCaixaDTO) {
        log.debug("Request to partially update FechamentoCaixa : {}", fechamentoCaixaDTO);

        return fechamentoCaixaRepository
            .findById(fechamentoCaixaDTO.getId())
            .map(existingFechamentoCaixa -> {
                fechamentoCaixaMapper.partialUpdate(existingFechamentoCaixa, fechamentoCaixaDTO);

                return existingFechamentoCaixa;
            })
            .map(fechamentoCaixaRepository::save)
            .map(fechamentoCaixaMapper::toDto);
    }

    /**
     * Get all the fechamentoCaixas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FechamentoCaixaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FechamentoCaixas");
        return fechamentoCaixaRepository.findAll(pageable).map(fechamentoCaixaMapper::toDto);
    }

    /**
     *  Get all the fechamentoCaixas where FechamentoCaixaDetalhes is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FechamentoCaixaDTO> findAllWhereFechamentoCaixaDetalhesIsNull() {
        log.debug("Request to get all fechamentoCaixas where FechamentoCaixaDetalhes is null");
        return StreamSupport
            .stream(fechamentoCaixaRepository.findAll().spliterator(), false)
            .filter(fechamentoCaixa -> fechamentoCaixa.getFechamentoCaixaDetalhes() == null)
            .map(fechamentoCaixaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one fechamentoCaixa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FechamentoCaixaDTO> findOne(Long id) {
        log.debug("Request to get FechamentoCaixa : {}", id);
        return fechamentoCaixaRepository.findById(id).map(fechamentoCaixaMapper::toDto);
    }

    /**
     * Delete the fechamentoCaixa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FechamentoCaixa : {}", id);
        fechamentoCaixaRepository.deleteById(id);
    }
}
