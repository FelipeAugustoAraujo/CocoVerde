package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.Frente;
import br.com.lisetech.cocoverde.repository.FrenteRepository;
import br.com.lisetech.cocoverde.service.dto.FrenteDTO;
import br.com.lisetech.cocoverde.service.mapper.FrenteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.lisetech.cocoverde.domain.Frente}.
 */
@Service
@Transactional
public class FrenteService {

    private final Logger log = LoggerFactory.getLogger(FrenteService.class);

    private final FrenteRepository frenteRepository;

    private final FrenteMapper frenteMapper;

    public FrenteService(FrenteRepository frenteRepository, FrenteMapper frenteMapper) {
        this.frenteRepository = frenteRepository;
        this.frenteMapper = frenteMapper;
    }

    /**
     * Save a frente.
     *
     * @param frenteDTO the entity to save.
     * @return the persisted entity.
     */
    public FrenteDTO save(FrenteDTO frenteDTO) {
        log.debug("Request to save Frente : {}", frenteDTO);
        Frente frente = frenteMapper.toEntity(frenteDTO);
        frente = frenteRepository.save(frente);
        return frenteMapper.toDto(frente);
    }

    /**
     * Update a frente.
     *
     * @param frenteDTO the entity to save.
     * @return the persisted entity.
     */
    public FrenteDTO update(FrenteDTO frenteDTO) {
        log.debug("Request to update Frente : {}", frenteDTO);
        Frente frente = frenteMapper.toEntity(frenteDTO);
        frente = frenteRepository.save(frente);
        return frenteMapper.toDto(frente);
    }

    /**
     * Partially update a frente.
     *
     * @param frenteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FrenteDTO> partialUpdate(FrenteDTO frenteDTO) {
        log.debug("Request to partially update Frente : {}", frenteDTO);

        return frenteRepository
            .findById(frenteDTO.getId())
            .map(existingFrente -> {
                frenteMapper.partialUpdate(existingFrente, frenteDTO);

                return existingFrente;
            })
            .map(frenteRepository::save)
            .map(frenteMapper::toDto);
    }

    /**
     * Get all the frentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FrenteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Frentes");
        return frenteRepository.findAll(pageable).map(frenteMapper::toDto);
    }

    /**
     * Get one frente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FrenteDTO> findOne(Long id) {
        log.debug("Request to get Frente : {}", id);
        return frenteRepository.findById(id).map(frenteMapper::toDto);
    }

    /**
     * Delete the frente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Frente : {}", id);
        frenteRepository.deleteById(id);
    }
}
