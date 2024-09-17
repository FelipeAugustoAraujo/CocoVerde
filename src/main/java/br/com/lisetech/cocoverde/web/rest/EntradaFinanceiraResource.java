package br.com.lisetech.cocoverde.web.rest;

import br.com.lisetech.cocoverde.repository.EntradaFinanceiraRepository;
import br.com.lisetech.cocoverde.service.EntradaFinanceiraQueryService;
import br.com.lisetech.cocoverde.service.EntradaFinanceiraService;
import br.com.lisetech.cocoverde.service.criteria.EntradaFinanceiraCriteria;
import br.com.lisetech.cocoverde.service.dto.EntradaFinanceiraDTO;
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
 * REST controller for managing {@link br.com.lisetech.cocoverde.domain.EntradaFinanceira}.
 */
@RestController
@RequestMapping("/api/entrada-financeiras")
public class EntradaFinanceiraResource {

    private final Logger log = LoggerFactory.getLogger(EntradaFinanceiraResource.class);

    private static final String ENTITY_NAME = "entradaFinanceira";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntradaFinanceiraService entradaFinanceiraService;

    private final EntradaFinanceiraRepository entradaFinanceiraRepository;

    private final EntradaFinanceiraQueryService entradaFinanceiraQueryService;

    public EntradaFinanceiraResource(
        EntradaFinanceiraService entradaFinanceiraService,
        EntradaFinanceiraRepository entradaFinanceiraRepository,
        EntradaFinanceiraQueryService entradaFinanceiraQueryService
    ) {
        this.entradaFinanceiraService = entradaFinanceiraService;
        this.entradaFinanceiraRepository = entradaFinanceiraRepository;
        this.entradaFinanceiraQueryService = entradaFinanceiraQueryService;
    }

    /**
     * {@code POST  /entrada-financeiras} : Create a new entradaFinanceira.
     *
     * @param entradaFinanceiraDTO the entradaFinanceiraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entradaFinanceiraDTO, or with status {@code 400 (Bad Request)} if the entradaFinanceira has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EntradaFinanceiraDTO> createEntradaFinanceira(@RequestBody EntradaFinanceiraDTO entradaFinanceiraDTO)
        throws URISyntaxException {
        log.debug("REST request to save EntradaFinanceira : {}", entradaFinanceiraDTO);
        if (entradaFinanceiraDTO.getId() != null) {
            throw new BadRequestAlertException("A new entradaFinanceira cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntradaFinanceiraDTO result = entradaFinanceiraService.save(entradaFinanceiraDTO);
        return ResponseEntity
            .created(new URI("/api/entrada-financeiras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entrada-financeiras/:id} : Updates an existing entradaFinanceira.
     *
     * @param id the id of the entradaFinanceiraDTO to save.
     * @param entradaFinanceiraDTO the entradaFinanceiraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entradaFinanceiraDTO,
     * or with status {@code 400 (Bad Request)} if the entradaFinanceiraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entradaFinanceiraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntradaFinanceiraDTO> updateEntradaFinanceira(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EntradaFinanceiraDTO entradaFinanceiraDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EntradaFinanceira : {}, {}", id, entradaFinanceiraDTO);
        if (entradaFinanceiraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entradaFinanceiraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entradaFinanceiraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EntradaFinanceiraDTO result = entradaFinanceiraService.update(entradaFinanceiraDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entradaFinanceiraDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /entrada-financeiras/:id} : Partial updates given fields of an existing entradaFinanceira, field will ignore if it is null
     *
     * @param id the id of the entradaFinanceiraDTO to save.
     * @param entradaFinanceiraDTO the entradaFinanceiraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entradaFinanceiraDTO,
     * or with status {@code 400 (Bad Request)} if the entradaFinanceiraDTO is not valid,
     * or with status {@code 404 (Not Found)} if the entradaFinanceiraDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the entradaFinanceiraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EntradaFinanceiraDTO> partialUpdateEntradaFinanceira(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EntradaFinanceiraDTO entradaFinanceiraDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EntradaFinanceira partially : {}, {}", id, entradaFinanceiraDTO);
        if (entradaFinanceiraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entradaFinanceiraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entradaFinanceiraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EntradaFinanceiraDTO> result = entradaFinanceiraService.partialUpdate(entradaFinanceiraDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entradaFinanceiraDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /entrada-financeiras} : get all the entradaFinanceiras.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entradaFinanceiras in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EntradaFinanceiraDTO>> getAllEntradaFinanceiras(
        EntradaFinanceiraCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EntradaFinanceiras by criteria: {}", criteria);

        Page<EntradaFinanceiraDTO> page = entradaFinanceiraQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entrada-financeiras/count} : count all the entradaFinanceiras.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEntradaFinanceiras(EntradaFinanceiraCriteria criteria) {
        log.debug("REST request to count EntradaFinanceiras by criteria: {}", criteria);
        return ResponseEntity.ok().body(entradaFinanceiraQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /entrada-financeiras/:id} : get the "id" entradaFinanceira.
     *
     * @param id the id of the entradaFinanceiraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entradaFinanceiraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntradaFinanceiraDTO> getEntradaFinanceira(@PathVariable("id") Long id) {
        log.debug("REST request to get EntradaFinanceira : {}", id);
        Optional<EntradaFinanceiraDTO> entradaFinanceiraDTO = entradaFinanceiraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entradaFinanceiraDTO);
    }

    /**
     * {@code DELETE  /entrada-financeiras/:id} : delete the "id" entradaFinanceira.
     *
     * @param id the id of the entradaFinanceiraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntradaFinanceira(@PathVariable("id") Long id) {
        log.debug("REST request to delete EntradaFinanceira : {}", id);
        entradaFinanceiraService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
