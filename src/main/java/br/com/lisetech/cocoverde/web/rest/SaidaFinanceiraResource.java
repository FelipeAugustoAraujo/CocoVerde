package br.com.lisetech.cocoverde.web.rest;

import br.com.lisetech.cocoverde.repository.SaidaFinanceiraRepository;
import br.com.lisetech.cocoverde.service.SaidaFinanceiraQueryService;
import br.com.lisetech.cocoverde.service.SaidaFinanceiraService;
import br.com.lisetech.cocoverde.service.criteria.SaidaFinanceiraCriteria;
import br.com.lisetech.cocoverde.service.dto.SaidaFinanceiraDTO;
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
 * REST controller for managing {@link br.com.lisetech.cocoverde.domain.SaidaFinanceira}.
 */
@RestController
@RequestMapping("/api/saida-financeiras")
public class SaidaFinanceiraResource {

    private final Logger log = LoggerFactory.getLogger(SaidaFinanceiraResource.class);

    private static final String ENTITY_NAME = "saidaFinanceira";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaidaFinanceiraService saidaFinanceiraService;

    private final SaidaFinanceiraRepository saidaFinanceiraRepository;

    private final SaidaFinanceiraQueryService saidaFinanceiraQueryService;

    public SaidaFinanceiraResource(
        SaidaFinanceiraService saidaFinanceiraService,
        SaidaFinanceiraRepository saidaFinanceiraRepository,
        SaidaFinanceiraQueryService saidaFinanceiraQueryService
    ) {
        this.saidaFinanceiraService = saidaFinanceiraService;
        this.saidaFinanceiraRepository = saidaFinanceiraRepository;
        this.saidaFinanceiraQueryService = saidaFinanceiraQueryService;
    }

    /**
     * {@code POST  /saida-financeiras} : Create a new saidaFinanceira.
     *
     * @param saidaFinanceiraDTO the saidaFinanceiraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new saidaFinanceiraDTO, or with status {@code 400 (Bad Request)} if the saidaFinanceira has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SaidaFinanceiraDTO> createSaidaFinanceira(@RequestBody SaidaFinanceiraDTO saidaFinanceiraDTO)
        throws URISyntaxException {
        log.debug("REST request to save SaidaFinanceira : {}", saidaFinanceiraDTO);
        if (saidaFinanceiraDTO.getId() != null) {
            throw new BadRequestAlertException("A new saidaFinanceira cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SaidaFinanceiraDTO result = saidaFinanceiraService.save(saidaFinanceiraDTO);
        return ResponseEntity
            .created(new URI("/api/saida-financeiras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /saida-financeiras/:id} : Updates an existing saidaFinanceira.
     *
     * @param id the id of the saidaFinanceiraDTO to save.
     * @param saidaFinanceiraDTO the saidaFinanceiraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saidaFinanceiraDTO,
     * or with status {@code 400 (Bad Request)} if the saidaFinanceiraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the saidaFinanceiraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SaidaFinanceiraDTO> updateSaidaFinanceira(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SaidaFinanceiraDTO saidaFinanceiraDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SaidaFinanceira : {}, {}", id, saidaFinanceiraDTO);
        if (saidaFinanceiraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saidaFinanceiraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saidaFinanceiraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SaidaFinanceiraDTO result = saidaFinanceiraService.update(saidaFinanceiraDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, saidaFinanceiraDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /saida-financeiras/:id} : Partial updates given fields of an existing saidaFinanceira, field will ignore if it is null
     *
     * @param id the id of the saidaFinanceiraDTO to save.
     * @param saidaFinanceiraDTO the saidaFinanceiraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saidaFinanceiraDTO,
     * or with status {@code 400 (Bad Request)} if the saidaFinanceiraDTO is not valid,
     * or with status {@code 404 (Not Found)} if the saidaFinanceiraDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the saidaFinanceiraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SaidaFinanceiraDTO> partialUpdateSaidaFinanceira(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SaidaFinanceiraDTO saidaFinanceiraDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SaidaFinanceira partially : {}, {}", id, saidaFinanceiraDTO);
        if (saidaFinanceiraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saidaFinanceiraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saidaFinanceiraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SaidaFinanceiraDTO> result = saidaFinanceiraService.partialUpdate(saidaFinanceiraDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, saidaFinanceiraDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /saida-financeiras} : get all the saidaFinanceiras.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saidaFinanceiras in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SaidaFinanceiraDTO>> getAllSaidaFinanceiras(
        SaidaFinanceiraCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SaidaFinanceiras by criteria: {}", criteria);

        Page<SaidaFinanceiraDTO> page = saidaFinanceiraQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /saida-financeiras/count} : count all the saidaFinanceiras.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSaidaFinanceiras(SaidaFinanceiraCriteria criteria) {
        log.debug("REST request to count SaidaFinanceiras by criteria: {}", criteria);
        return ResponseEntity.ok().body(saidaFinanceiraQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /saida-financeiras/:id} : get the "id" saidaFinanceira.
     *
     * @param id the id of the saidaFinanceiraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the saidaFinanceiraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SaidaFinanceiraDTO> getSaidaFinanceira(@PathVariable("id") Long id) {
        log.debug("REST request to get SaidaFinanceira : {}", id);
        Optional<SaidaFinanceiraDTO> saidaFinanceiraDTO = saidaFinanceiraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(saidaFinanceiraDTO);
    }

    /**
     * {@code DELETE  /saida-financeiras/:id} : delete the "id" saidaFinanceira.
     *
     * @param id the id of the saidaFinanceiraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaidaFinanceira(@PathVariable("id") Long id) {
        log.debug("REST request to delete SaidaFinanceira : {}", id);
        saidaFinanceiraService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
