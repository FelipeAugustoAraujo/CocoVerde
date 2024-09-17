package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.FechamentoCaixaDetalhes;
import br.com.lisetech.cocoverde.repository.FechamentoCaixaDetalhesRepository;
import br.com.lisetech.cocoverde.service.dto.FechamentoCaixaDetalhesDTO;
import br.com.lisetech.cocoverde.service.mapper.FechamentoCaixaDetalhesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.lisetech.cocoverde.domain.FechamentoCaixaDetalhes}.
 */
@Service
@Transactional
public class FechamentoCaixaDetalhesService {

    private final Logger log = LoggerFactory.getLogger(FechamentoCaixaDetalhesService.class);

    private final FechamentoCaixaDetalhesRepository fechamentoCaixaDetalhesRepository;

    private final FechamentoCaixaDetalhesMapper fechamentoCaixaDetalhesMapper;

    public FechamentoCaixaDetalhesService(
        FechamentoCaixaDetalhesRepository fechamentoCaixaDetalhesRepository,
        FechamentoCaixaDetalhesMapper fechamentoCaixaDetalhesMapper
    ) {
        this.fechamentoCaixaDetalhesRepository = fechamentoCaixaDetalhesRepository;
        this.fechamentoCaixaDetalhesMapper = fechamentoCaixaDetalhesMapper;
    }

    /**
     * Save a fechamentoCaixaDetalhes.
     *
     * @param fechamentoCaixaDetalhesDTO the entity to save.
     * @return the persisted entity.
     */
    public FechamentoCaixaDetalhesDTO save(FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhesDTO) {
        log.debug("Request to save FechamentoCaixaDetalhes : {}", fechamentoCaixaDetalhesDTO);
        FechamentoCaixaDetalhes fechamentoCaixaDetalhes = fechamentoCaixaDetalhesMapper.toEntity(fechamentoCaixaDetalhesDTO);
        fechamentoCaixaDetalhes = fechamentoCaixaDetalhesRepository.save(fechamentoCaixaDetalhes);
        return fechamentoCaixaDetalhesMapper.toDto(fechamentoCaixaDetalhes);
    }

    /**
     * Update a fechamentoCaixaDetalhes.
     *
     * @param fechamentoCaixaDetalhesDTO the entity to save.
     * @return the persisted entity.
     */
    public FechamentoCaixaDetalhesDTO update(FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhesDTO) {
        log.debug("Request to update FechamentoCaixaDetalhes : {}", fechamentoCaixaDetalhesDTO);
        FechamentoCaixaDetalhes fechamentoCaixaDetalhes = fechamentoCaixaDetalhesMapper.toEntity(fechamentoCaixaDetalhesDTO);
        fechamentoCaixaDetalhes = fechamentoCaixaDetalhesRepository.save(fechamentoCaixaDetalhes);
        return fechamentoCaixaDetalhesMapper.toDto(fechamentoCaixaDetalhes);
    }

    /**
     * Partially update a fechamentoCaixaDetalhes.
     *
     * @param fechamentoCaixaDetalhesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FechamentoCaixaDetalhesDTO> partialUpdate(FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhesDTO) {
        log.debug("Request to partially update FechamentoCaixaDetalhes : {}", fechamentoCaixaDetalhesDTO);

        return fechamentoCaixaDetalhesRepository
            .findById(fechamentoCaixaDetalhesDTO.getId())
            .map(existingFechamentoCaixaDetalhes -> {
                fechamentoCaixaDetalhesMapper.partialUpdate(existingFechamentoCaixaDetalhes, fechamentoCaixaDetalhesDTO);

                return existingFechamentoCaixaDetalhes;
            })
            .map(fechamentoCaixaDetalhesRepository::save)
            .map(fechamentoCaixaDetalhesMapper::toDto);
    }

    /**
     * Get all the fechamentoCaixaDetalhes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FechamentoCaixaDetalhesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FechamentoCaixaDetalhes");
        return fechamentoCaixaDetalhesRepository.findAll(pageable).map(fechamentoCaixaDetalhesMapper::toDto);
    }

    /**
     * Get one fechamentoCaixaDetalhes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FechamentoCaixaDetalhesDTO> findOne(Long id) {
        log.debug("Request to get FechamentoCaixaDetalhes : {}", id);
        return fechamentoCaixaDetalhesRepository.findById(id).map(fechamentoCaixaDetalhesMapper::toDto);
    }

    /**
     * Delete the fechamentoCaixaDetalhes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FechamentoCaixaDetalhes : {}", id);
        fechamentoCaixaDetalhesRepository.deleteById(id);
    }
}
