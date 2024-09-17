package br.com.lisetech.cocoverde.web.rest;

import br.com.lisetech.cocoverde.repository.DetalhesEntradaFinanceiraRepository;
import br.com.lisetech.cocoverde.service.DetalhesEntradaFinanceiraQueryService;
import br.com.lisetech.cocoverde.service.DetalhesEntradaFinanceiraService;
import br.com.lisetech.cocoverde.service.criteria.DetalhesEntradaFinanceiraCriteria;
import br.com.lisetech.cocoverde.service.dto.DetalhesEntradaFinanceiraDTO;
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
 * REST controller for managing {@link br.com.lisetech.cocoverde.domain.DetalhesEntradaFinanceira}.
 */
@RestController
@RequestMapping("/api/detalhes-entrada-financeiras")
public class DetalhesEntradaFinanceiraResource {

    private final Logger log = LoggerFactory.getLogger(DetalhesEntradaFinanceiraResource.class);

    private static final String ENTITY_NAME = "detalhesEntradaFinanceira";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetalhesEntradaFinanceiraService detalhesEntradaFinanceiraService;

    private final DetalhesEntradaFinanceiraRepository detalhesEntradaFinanceiraRepository;

    private final DetalhesEntradaFinanceiraQueryService detalhesEntradaFinanceiraQueryService;

    public DetalhesEntradaFinanceiraResource(
        DetalhesEntradaFinanceiraService detalhesEntradaFinanceiraService,
        DetalhesEntradaFinanceiraRepository detalhesEntradaFinanceiraRepository,
        DetalhesEntradaFinanceiraQueryService detalhesEntradaFinanceiraQueryService
    ) {
        this.detalhesEntradaFinanceiraService = detalhesEntradaFinanceiraService;
        this.detalhesEntradaFinanceiraRepository = detalhesEntradaFinanceiraRepository;
        this.detalhesEntradaFinanceiraQueryService = detalhesEntradaFinanceiraQueryService;
    }

    /**
     * {@code POST  /detalhes-entrada-financeiras} : Create a new detalhesEntradaFinanceira.
     *
     * @param detalhesEntradaFinanceiraDTO the detalhesEntradaFinanceiraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detalhesEntradaFinanceiraDTO, or with status {@code 400 (Bad Request)} if the detalhesEntradaFinanceira has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DetalhesEntradaFinanceiraDTO> createDetalhesEntradaFinanceira(
        @RequestBody DetalhesEntradaFinanceiraDTO detalhesEntradaFinanceiraDTO
    ) throws URISyntaxException {
        log.debug("REST request to save DetalhesEntradaFinanceira : {}", detalhesEntradaFinanceiraDTO);
        if (detalhesEntradaFinanceiraDTO.getId() != null) {
            throw new BadRequestAlertException("A new detalhesEntradaFinanceira cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetalhesEntradaFinanceiraDTO result = detalhesEntradaFinanceiraService.save(detalhesEntradaFinanceiraDTO);
        return ResponseEntity
            .created(new URI("/api/detalhes-entrada-financeiras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detalhes-entrada-financeiras/:id} : Updates an existing detalhesEntradaFinanceira.
     *
     * @param id the id of the detalhesEntradaFinanceiraDTO to save.
     * @param detalhesEntradaFinanceiraDTO the detalhesEntradaFinanceiraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalhesEntradaFinanceiraDTO,
     * or with status {@code 400 (Bad Request)} if the detalhesEntradaFinanceiraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detalhesEntradaFinanceiraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DetalhesEntradaFinanceiraDTO> updateDetalhesEntradaFinanceira(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DetalhesEntradaFinanceiraDTO detalhesEntradaFinanceiraDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DetalhesEntradaFinanceira : {}, {}", id, detalhesEntradaFinanceiraDTO);
        if (detalhesEntradaFinanceiraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detalhesEntradaFinanceiraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detalhesEntradaFinanceiraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetalhesEntradaFinanceiraDTO result = detalhesEntradaFinanceiraService.update(detalhesEntradaFinanceiraDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, detalhesEntradaFinanceiraDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /detalhes-entrada-financeiras/:id} : Partial updates given fields of an existing detalhesEntradaFinanceira, field will ignore if it is null
     *
     * @param id the id of the detalhesEntradaFinanceiraDTO to save.
     * @param detalhesEntradaFinanceiraDTO the detalhesEntradaFinanceiraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalhesEntradaFinanceiraDTO,
     * or with status {@code 400 (Bad Request)} if the detalhesEntradaFinanceiraDTO is not valid,
     * or with status {@code 404 (Not Found)} if the detalhesEntradaFinanceiraDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the detalhesEntradaFinanceiraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DetalhesEntradaFinanceiraDTO> partialUpdateDetalhesEntradaFinanceira(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DetalhesEntradaFinanceiraDTO detalhesEntradaFinanceiraDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DetalhesEntradaFinanceira partially : {}, {}", id, detalhesEntradaFinanceiraDTO);
        if (detalhesEntradaFinanceiraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detalhesEntradaFinanceiraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detalhesEntradaFinanceiraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetalhesEntradaFinanceiraDTO> result = detalhesEntradaFinanceiraService.partialUpdate(detalhesEntradaFinanceiraDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, detalhesEntradaFinanceiraDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /detalhes-entrada-financeiras} : get all the detalhesEntradaFinanceiras.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detalhesEntradaFinanceiras in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DetalhesEntradaFinanceiraDTO>> getAllDetalhesEntradaFinanceiras(
        DetalhesEntradaFinanceiraCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DetalhesEntradaFinanceiras by criteria: {}", criteria);

        Page<DetalhesEntradaFinanceiraDTO> page = detalhesEntradaFinanceiraQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /detalhes-entrada-financeiras/count} : count all the detalhesEntradaFinanceiras.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDetalhesEntradaFinanceiras(DetalhesEntradaFinanceiraCriteria criteria) {
        log.debug("REST request to count DetalhesEntradaFinanceiras by criteria: {}", criteria);
        return ResponseEntity.ok().body(detalhesEntradaFinanceiraQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /detalhes-entrada-financeiras/:id} : get the "id" detalhesEntradaFinanceira.
     *
     * @param id the id of the detalhesEntradaFinanceiraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detalhesEntradaFinanceiraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DetalhesEntradaFinanceiraDTO> getDetalhesEntradaFinanceira(@PathVariable("id") Long id) {
        log.debug("REST request to get DetalhesEntradaFinanceira : {}", id);
        Optional<DetalhesEntradaFinanceiraDTO> detalhesEntradaFinanceiraDTO = detalhesEntradaFinanceiraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detalhesEntradaFinanceiraDTO);
    }

    /**
     * {@code DELETE  /detalhes-entrada-financeiras/:id} : delete the "id" detalhesEntradaFinanceira.
     *
     * @param id the id of the detalhesEntradaFinanceiraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetalhesEntradaFinanceira(@PathVariable("id") Long id) {
        log.debug("REST request to delete DetalhesEntradaFinanceira : {}", id);
        detalhesEntradaFinanceiraService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
