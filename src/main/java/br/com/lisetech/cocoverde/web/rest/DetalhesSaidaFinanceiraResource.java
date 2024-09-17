package br.com.lisetech.cocoverde.web.rest;

import br.com.lisetech.cocoverde.repository.DetalhesSaidaFinanceiraRepository;
import br.com.lisetech.cocoverde.service.DetalhesSaidaFinanceiraQueryService;
import br.com.lisetech.cocoverde.service.DetalhesSaidaFinanceiraService;
import br.com.lisetech.cocoverde.service.criteria.DetalhesSaidaFinanceiraCriteria;
import br.com.lisetech.cocoverde.service.dto.DetalhesSaidaFinanceiraDTO;
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
 * REST controller for managing {@link br.com.lisetech.cocoverde.domain.DetalhesSaidaFinanceira}.
 */
@RestController
@RequestMapping("/api/detalhes-saida-financeiras")
public class DetalhesSaidaFinanceiraResource {

    private final Logger log = LoggerFactory.getLogger(DetalhesSaidaFinanceiraResource.class);

    private static final String ENTITY_NAME = "detalhesSaidaFinanceira";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetalhesSaidaFinanceiraService detalhesSaidaFinanceiraService;

    private final DetalhesSaidaFinanceiraRepository detalhesSaidaFinanceiraRepository;

    private final DetalhesSaidaFinanceiraQueryService detalhesSaidaFinanceiraQueryService;

    public DetalhesSaidaFinanceiraResource(
        DetalhesSaidaFinanceiraService detalhesSaidaFinanceiraService,
        DetalhesSaidaFinanceiraRepository detalhesSaidaFinanceiraRepository,
        DetalhesSaidaFinanceiraQueryService detalhesSaidaFinanceiraQueryService
    ) {
        this.detalhesSaidaFinanceiraService = detalhesSaidaFinanceiraService;
        this.detalhesSaidaFinanceiraRepository = detalhesSaidaFinanceiraRepository;
        this.detalhesSaidaFinanceiraQueryService = detalhesSaidaFinanceiraQueryService;
    }

    /**
     * {@code POST  /detalhes-saida-financeiras} : Create a new detalhesSaidaFinanceira.
     *
     * @param detalhesSaidaFinanceiraDTO the detalhesSaidaFinanceiraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detalhesSaidaFinanceiraDTO, or with status {@code 400 (Bad Request)} if the detalhesSaidaFinanceira has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DetalhesSaidaFinanceiraDTO> createDetalhesSaidaFinanceira(
        @RequestBody DetalhesSaidaFinanceiraDTO detalhesSaidaFinanceiraDTO
    ) throws URISyntaxException {
        log.debug("REST request to save DetalhesSaidaFinanceira : {}", detalhesSaidaFinanceiraDTO);
        if (detalhesSaidaFinanceiraDTO.getId() != null) {
            throw new BadRequestAlertException("A new detalhesSaidaFinanceira cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetalhesSaidaFinanceiraDTO result = detalhesSaidaFinanceiraService.save(detalhesSaidaFinanceiraDTO);
        return ResponseEntity
            .created(new URI("/api/detalhes-saida-financeiras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detalhes-saida-financeiras/:id} : Updates an existing detalhesSaidaFinanceira.
     *
     * @param id the id of the detalhesSaidaFinanceiraDTO to save.
     * @param detalhesSaidaFinanceiraDTO the detalhesSaidaFinanceiraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalhesSaidaFinanceiraDTO,
     * or with status {@code 400 (Bad Request)} if the detalhesSaidaFinanceiraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detalhesSaidaFinanceiraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DetalhesSaidaFinanceiraDTO> updateDetalhesSaidaFinanceira(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DetalhesSaidaFinanceiraDTO detalhesSaidaFinanceiraDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DetalhesSaidaFinanceira : {}, {}", id, detalhesSaidaFinanceiraDTO);
        if (detalhesSaidaFinanceiraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detalhesSaidaFinanceiraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detalhesSaidaFinanceiraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetalhesSaidaFinanceiraDTO result = detalhesSaidaFinanceiraService.update(detalhesSaidaFinanceiraDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, detalhesSaidaFinanceiraDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /detalhes-saida-financeiras/:id} : Partial updates given fields of an existing detalhesSaidaFinanceira, field will ignore if it is null
     *
     * @param id the id of the detalhesSaidaFinanceiraDTO to save.
     * @param detalhesSaidaFinanceiraDTO the detalhesSaidaFinanceiraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalhesSaidaFinanceiraDTO,
     * or with status {@code 400 (Bad Request)} if the detalhesSaidaFinanceiraDTO is not valid,
     * or with status {@code 404 (Not Found)} if the detalhesSaidaFinanceiraDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the detalhesSaidaFinanceiraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DetalhesSaidaFinanceiraDTO> partialUpdateDetalhesSaidaFinanceira(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DetalhesSaidaFinanceiraDTO detalhesSaidaFinanceiraDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DetalhesSaidaFinanceira partially : {}, {}", id, detalhesSaidaFinanceiraDTO);
        if (detalhesSaidaFinanceiraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detalhesSaidaFinanceiraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detalhesSaidaFinanceiraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetalhesSaidaFinanceiraDTO> result = detalhesSaidaFinanceiraService.partialUpdate(detalhesSaidaFinanceiraDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, detalhesSaidaFinanceiraDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /detalhes-saida-financeiras} : get all the detalhesSaidaFinanceiras.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detalhesSaidaFinanceiras in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DetalhesSaidaFinanceiraDTO>> getAllDetalhesSaidaFinanceiras(
        DetalhesSaidaFinanceiraCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DetalhesSaidaFinanceiras by criteria: {}", criteria);

        Page<DetalhesSaidaFinanceiraDTO> page = detalhesSaidaFinanceiraQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /detalhes-saida-financeiras/count} : count all the detalhesSaidaFinanceiras.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDetalhesSaidaFinanceiras(DetalhesSaidaFinanceiraCriteria criteria) {
        log.debug("REST request to count DetalhesSaidaFinanceiras by criteria: {}", criteria);
        return ResponseEntity.ok().body(detalhesSaidaFinanceiraQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /detalhes-saida-financeiras/:id} : get the "id" detalhesSaidaFinanceira.
     *
     * @param id the id of the detalhesSaidaFinanceiraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detalhesSaidaFinanceiraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DetalhesSaidaFinanceiraDTO> getDetalhesSaidaFinanceira(@PathVariable("id") Long id) {
        log.debug("REST request to get DetalhesSaidaFinanceira : {}", id);
        Optional<DetalhesSaidaFinanceiraDTO> detalhesSaidaFinanceiraDTO = detalhesSaidaFinanceiraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detalhesSaidaFinanceiraDTO);
    }

    /**
     * {@code DELETE  /detalhes-saida-financeiras/:id} : delete the "id" detalhesSaidaFinanceira.
     *
     * @param id the id of the detalhesSaidaFinanceiraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetalhesSaidaFinanceira(@PathVariable("id") Long id) {
        log.debug("REST request to delete DetalhesSaidaFinanceira : {}", id);
        detalhesSaidaFinanceiraService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
