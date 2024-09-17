package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.DiaTrabalho;
import br.com.lisetech.cocoverde.repository.DiaTrabalhoRepository;
import br.com.lisetech.cocoverde.service.dto.DiaTrabalhoDTO;
import br.com.lisetech.cocoverde.service.mapper.DiaTrabalhoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.lisetech.cocoverde.domain.DiaTrabalho}.
 */
@Service
@Transactional
public class DiaTrabalhoService {

    private final Logger log = LoggerFactory.getLogger(DiaTrabalhoService.class);

    private final DiaTrabalhoRepository diaTrabalhoRepository;

    private final DiaTrabalhoMapper diaTrabalhoMapper;

    public DiaTrabalhoService(DiaTrabalhoRepository diaTrabalhoRepository, DiaTrabalhoMapper diaTrabalhoMapper) {
        this.diaTrabalhoRepository = diaTrabalhoRepository;
        this.diaTrabalhoMapper = diaTrabalhoMapper;
    }

    /**
     * Save a diaTrabalho.
     *
     * @param diaTrabalhoDTO the entity to save.
     * @return the persisted entity.
     */
    public DiaTrabalhoDTO save(DiaTrabalhoDTO diaTrabalhoDTO) {
        log.debug("Request to save DiaTrabalho : {}", diaTrabalhoDTO);
        DiaTrabalho diaTrabalho = diaTrabalhoMapper.toEntity(diaTrabalhoDTO);
        diaTrabalho = diaTrabalhoRepository.save(diaTrabalho);
        return diaTrabalhoMapper.toDto(diaTrabalho);
    }

    /**
     * Update a diaTrabalho.
     *
     * @param diaTrabalhoDTO the entity to save.
     * @return the persisted entity.
     */
    public DiaTrabalhoDTO update(DiaTrabalhoDTO diaTrabalhoDTO) {
        log.debug("Request to update DiaTrabalho : {}", diaTrabalhoDTO);
        DiaTrabalho diaTrabalho = diaTrabalhoMapper.toEntity(diaTrabalhoDTO);
        diaTrabalho = diaTrabalhoRepository.save(diaTrabalho);
        return diaTrabalhoMapper.toDto(diaTrabalho);
    }

    /**
     * Partially update a diaTrabalho.
     *
     * @param diaTrabalhoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DiaTrabalhoDTO> partialUpdate(DiaTrabalhoDTO diaTrabalhoDTO) {
        log.debug("Request to partially update DiaTrabalho : {}", diaTrabalhoDTO);

        return diaTrabalhoRepository
            .findById(diaTrabalhoDTO.getId())
            .map(existingDiaTrabalho -> {
                diaTrabalhoMapper.partialUpdate(existingDiaTrabalho, diaTrabalhoDTO);

                return existingDiaTrabalho;
            })
            .map(diaTrabalhoRepository::save)
            .map(diaTrabalhoMapper::toDto);
    }

    /**
     * Get all the diaTrabalhos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DiaTrabalhoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DiaTrabalhos");
        return diaTrabalhoRepository.findAll(pageable).map(diaTrabalhoMapper::toDto);
    }

    /**
     * Get one diaTrabalho by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DiaTrabalhoDTO> findOne(Long id) {
        log.debug("Request to get DiaTrabalho : {}", id);
        return diaTrabalhoRepository.findById(id).map(diaTrabalhoMapper::toDto);
    }

    /**
     * Delete the diaTrabalho by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DiaTrabalho : {}", id);
        diaTrabalhoRepository.deleteById(id);
    }
}
