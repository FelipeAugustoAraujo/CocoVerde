package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.Configuracao;
import br.com.lisetech.cocoverde.repository.ConfiguracaoRepository;
import br.com.lisetech.cocoverde.service.dto.ConfiguracaoDTO;
import br.com.lisetech.cocoverde.service.mapper.ConfiguracaoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.lisetech.cocoverde.domain.Configuracao}.
 */
@Service
@Transactional
public class ConfiguracaoService {

    private final Logger log = LoggerFactory.getLogger(ConfiguracaoService.class);

    private final ConfiguracaoRepository configuracaoRepository;

    private final ConfiguracaoMapper configuracaoMapper;

    public ConfiguracaoService(ConfiguracaoRepository configuracaoRepository, ConfiguracaoMapper configuracaoMapper) {
        this.configuracaoRepository = configuracaoRepository;
        this.configuracaoMapper = configuracaoMapper;
    }

    /**
     * Save a configuracao.
     *
     * @param configuracaoDTO the entity to save.
     * @return the persisted entity.
     */
    public ConfiguracaoDTO save(ConfiguracaoDTO configuracaoDTO) {
        log.debug("Request to save Configuracao : {}", configuracaoDTO);
        Configuracao configuracao = configuracaoMapper.toEntity(configuracaoDTO);
        configuracao = configuracaoRepository.save(configuracao);
        return configuracaoMapper.toDto(configuracao);
    }

    /**
     * Update a configuracao.
     *
     * @param configuracaoDTO the entity to save.
     * @return the persisted entity.
     */
    public ConfiguracaoDTO update(ConfiguracaoDTO configuracaoDTO) {
        log.debug("Request to update Configuracao : {}", configuracaoDTO);
        Configuracao configuracao = configuracaoMapper.toEntity(configuracaoDTO);
        // no save call needed as we have no fields that can be updated
        return configuracaoMapper.toDto(configuracao);
    }

    /**
     * Partially update a configuracao.
     *
     * @param configuracaoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ConfiguracaoDTO> partialUpdate(ConfiguracaoDTO configuracaoDTO) {
        log.debug("Request to partially update Configuracao : {}", configuracaoDTO);

        return configuracaoRepository
            .findById(configuracaoDTO.getId())
            .map(existingConfiguracao -> {
                configuracaoMapper.partialUpdate(existingConfiguracao, configuracaoDTO);

                return existingConfiguracao;
            })
            // .map(configuracaoRepository::save)
            .map(configuracaoMapper::toDto);
    }

    /**
     * Get all the configuracaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConfiguracaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Configuracaos");
        return configuracaoRepository.findAll(pageable).map(configuracaoMapper::toDto);
    }

    /**
     * Get one configuracao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConfiguracaoDTO> findOne(Long id) {
        log.debug("Request to get Configuracao : {}", id);
        return configuracaoRepository.findById(id).map(configuracaoMapper::toDto);
    }

    /**
     * Delete the configuracao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Configuracao : {}", id);
        configuracaoRepository.deleteById(id);
    }
}
