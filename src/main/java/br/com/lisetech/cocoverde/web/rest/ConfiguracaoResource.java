package br.com.lisetech.cocoverde.web.rest;

import br.com.lisetech.cocoverde.repository.ConfiguracaoRepository;
import br.com.lisetech.cocoverde.service.ConfiguracaoQueryService;
import br.com.lisetech.cocoverde.service.ConfiguracaoService;
import br.com.lisetech.cocoverde.service.criteria.ConfiguracaoCriteria;
import br.com.lisetech.cocoverde.service.dto.ConfiguracaoDTO;
import br.com.lisetech.cocoverde.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.lisetech.cocoverde.domain.Configuracao}.
 */
@RestController
@RequestMapping("/api/configuracaos")
public class ConfiguracaoResource {

    private final Logger log = LoggerFactory.getLogger(ConfiguracaoResource.class);

    private static final String ENTITY_NAME = "configuracao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfiguracaoService configuracaoService;

    private final ConfiguracaoRepository configuracaoRepository;

    private final ConfiguracaoQueryService configuracaoQueryService;

    public ConfiguracaoResource(
        ConfiguracaoService configuracaoService,
        ConfiguracaoRepository configuracaoRepository,
        ConfiguracaoQueryService configuracaoQueryService
    ) {
        this.configuracaoService = configuracaoService;
        this.configuracaoRepository = configuracaoRepository;
        this.configuracaoQueryService = configuracaoQueryService;
    }

    /**
     * {@code POST  /configuracaos} : Create a new configuracao.
     *
     * @param configuracaoDTO the configuracaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new configuracaoDTO, or with status {@code 400 (Bad Request)} if the configuracao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ConfiguracaoDTO> createConfiguracao(@RequestBody ConfiguracaoDTO configuracaoDTO) throws URISyntaxException {
        log.debug("REST request to save Configuracao : {}", configuracaoDTO);
        if (configuracaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new configuracao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfiguracaoDTO result = configuracaoService.save(configuracaoDTO);
        return ResponseEntity
            .created(new URI("/api/configuracaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /configuracaos/:id} : Updates an existing configuracao.
     *
     * @param id the id of the configuracaoDTO to save.
     * @param configuracaoDTO the configuracaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configuracaoDTO,
     * or with status {@code 400 (Bad Request)} if the configuracaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the configuracaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ConfiguracaoDTO> updateConfiguracao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConfiguracaoDTO configuracaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Configuracao : {}, {}", id, configuracaoDTO);
        if (configuracaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, configuracaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!configuracaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConfiguracaoDTO result = configuracaoService.update(configuracaoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, configuracaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /configuracaos/:id} : Partial updates given fields of an existing configuracao, field will ignore if it is null
     *
     * @param id the id of the configuracaoDTO to save.
     * @param configuracaoDTO the configuracaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configuracaoDTO,
     * or with status {@code 400 (Bad Request)} if the configuracaoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the configuracaoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the configuracaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConfiguracaoDTO> partialUpdateConfiguracao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConfiguracaoDTO configuracaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Configuracao partially : {}, {}", id, configuracaoDTO);
        if (configuracaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, configuracaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!configuracaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConfiguracaoDTO> result = configuracaoService.partialUpdate(configuracaoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, configuracaoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /configuracaos} : get all the configuracaos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of configuracaos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ConfiguracaoDTO>> getAllConfiguracaos(
        ConfiguracaoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Configuracaos by criteria: {}", criteria);

        Page<ConfiguracaoDTO> page = configuracaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /configuracaos/count} : count all the configuracaos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countConfiguracaos(ConfiguracaoCriteria criteria) {
        log.debug("REST request to count Configuracaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(configuracaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /configuracaos/:id} : get the "id" configuracao.
     *
     * @param id the id of the configuracaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the configuracaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConfiguracaoDTO> getConfiguracao(@PathVariable("id") Long id) {
        log.debug("REST request to get Configuracao : {}", id);
        Optional<ConfiguracaoDTO> configuracaoDTO = configuracaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(configuracaoDTO);
    }

    /**
     * {@code DELETE  /configuracaos/:id} : delete the "id" configuracao.
     *
     * @param id the id of the configuracaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfiguracao(@PathVariable("id") Long id) {
        log.debug("REST request to delete Configuracao : {}", id);
        configuracaoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
