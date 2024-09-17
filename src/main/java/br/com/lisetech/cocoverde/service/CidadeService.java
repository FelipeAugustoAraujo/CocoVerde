package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.Cidade;
import br.com.lisetech.cocoverde.repository.CidadeRepository;
import br.com.lisetech.cocoverde.service.dto.CidadeDTO;
import br.com.lisetech.cocoverde.service.mapper.CidadeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.lisetech.cocoverde.domain.Cidade}.
 */
@Service
@Transactional
public class CidadeService {

    private final Logger log = LoggerFactory.getLogger(CidadeService.class);

    private final CidadeRepository cidadeRepository;

    private final CidadeMapper cidadeMapper;

    public CidadeService(CidadeRepository cidadeRepository, CidadeMapper cidadeMapper) {
        this.cidadeRepository = cidadeRepository;
        this.cidadeMapper = cidadeMapper;
    }

    /**
     * Save a cidade.
     *
     * @param cidadeDTO the entity to save.
     * @return the persisted entity.
     */
    public CidadeDTO save(CidadeDTO cidadeDTO) {
        log.debug("Request to save Cidade : {}", cidadeDTO);
        Cidade cidade = cidadeMapper.toEntity(cidadeDTO);
        cidade = cidadeRepository.save(cidade);
        return cidadeMapper.toDto(cidade);
    }

    /**
     * Update a cidade.
     *
     * @param cidadeDTO the entity to save.
     * @return the persisted entity.
     */
    public CidadeDTO update(CidadeDTO cidadeDTO) {
        log.debug("Request to update Cidade : {}", cidadeDTO);
        Cidade cidade = cidadeMapper.toEntity(cidadeDTO);
        cidade = cidadeRepository.save(cidade);
        return cidadeMapper.toDto(cidade);
    }

    /**
     * Partially update a cidade.
     *
     * @param cidadeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CidadeDTO> partialUpdate(CidadeDTO cidadeDTO) {
        log.debug("Request to partially update Cidade : {}", cidadeDTO);

        return cidadeRepository
            .findById(cidadeDTO.getId())
            .map(existingCidade -> {
                cidadeMapper.partialUpdate(existingCidade, cidadeDTO);

                return existingCidade;
            })
            .map(cidadeRepository::save)
            .map(cidadeMapper::toDto);
    }

    /**
     * Get all the cidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CidadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cidades");
        return cidadeRepository.findAll(pageable).map(cidadeMapper::toDto);
    }

    /**
     * Get one cidade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CidadeDTO> findOne(Long id) {
        log.debug("Request to get Cidade : {}", id);
        return cidadeRepository.findById(id).map(cidadeMapper::toDto);
    }

    /**
     * Delete the cidade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cidade : {}", id);
        cidadeRepository.deleteById(id);
    }
}
