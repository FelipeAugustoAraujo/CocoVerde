package br.com.lisetech.cocoverde.web.rest;

import br.com.lisetech.cocoverde.repository.FechamentoCaixaRepository;
import br.com.lisetech.cocoverde.service.FechamentoCaixaQueryService;
import br.com.lisetech.cocoverde.service.FechamentoCaixaService;
import br.com.lisetech.cocoverde.service.criteria.FechamentoCaixaCriteria;
import br.com.lisetech.cocoverde.service.dto.FechamentoCaixaDTO;
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
 * REST controller for managing {@link br.com.lisetech.cocoverde.domain.FechamentoCaixa}.
 */
@RestController
@RequestMapping("/api/fechamento-caixas")
public class FechamentoCaixaResource {

    private final Logger log = LoggerFactory.getLogger(FechamentoCaixaResource.class);

    private static final String ENTITY_NAME = "fechamentoCaixa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FechamentoCaixaService fechamentoCaixaService;

    private final FechamentoCaixaRepository fechamentoCaixaRepository;

    private final FechamentoCaixaQueryService fechamentoCaixaQueryService;

    public FechamentoCaixaResource(
        FechamentoCaixaService fechamentoCaixaService,
        FechamentoCaixaRepository fechamentoCaixaRepository,
        FechamentoCaixaQueryService fechamentoCaixaQueryService
    ) {
        this.fechamentoCaixaService = fechamentoCaixaService;
        this.fechamentoCaixaRepository = fechamentoCaixaRepository;
        this.fechamentoCaixaQueryService = fechamentoCaixaQueryService;
    }

    /**
     * {@code POST  /fechamento-caixas} : Create a new fechamentoCaixa.
     *
     * @param fechamentoCaixaDTO the fechamentoCaixaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fechamentoCaixaDTO, or with status {@code 400 (Bad Request)} if the fechamentoCaixa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FechamentoCaixaDTO> createFechamentoCaixa(@RequestBody FechamentoCaixaDTO fechamentoCaixaDTO)
        throws URISyntaxException {
        log.debug("REST request to save FechamentoCaixa : {}", fechamentoCaixaDTO);
        if (fechamentoCaixaDTO.getId() != null) {
            throw new BadRequestAlertException("A new fechamentoCaixa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FechamentoCaixaDTO result = fechamentoCaixaService.save(fechamentoCaixaDTO);
        return ResponseEntity
            .created(new URI("/api/fechamento-caixas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fechamento-caixas/:id} : Updates an existing fechamentoCaixa.
     *
     * @param id the id of the fechamentoCaixaDTO to save.
     * @param fechamentoCaixaDTO the fechamentoCaixaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fechamentoCaixaDTO,
     * or with status {@code 400 (Bad Request)} if the fechamentoCaixaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fechamentoCaixaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FechamentoCaixaDTO> updateFechamentoCaixa(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FechamentoCaixaDTO fechamentoCaixaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FechamentoCaixa : {}, {}", id, fechamentoCaixaDTO);
        if (fechamentoCaixaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fechamentoCaixaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fechamentoCaixaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FechamentoCaixaDTO result = fechamentoCaixaService.update(fechamentoCaixaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fechamentoCaixaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fechamento-caixas/:id} : Partial updates given fields of an existing fechamentoCaixa, field will ignore if it is null
     *
     * @param id the id of the fechamentoCaixaDTO to save.
     * @param fechamentoCaixaDTO the fechamentoCaixaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fechamentoCaixaDTO,
     * or with status {@code 400 (Bad Request)} if the fechamentoCaixaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fechamentoCaixaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fechamentoCaixaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FechamentoCaixaDTO> partialUpdateFechamentoCaixa(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FechamentoCaixaDTO fechamentoCaixaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FechamentoCaixa partially : {}, {}", id, fechamentoCaixaDTO);
        if (fechamentoCaixaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fechamentoCaixaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fechamentoCaixaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FechamentoCaixaDTO> result = fechamentoCaixaService.partialUpdate(fechamentoCaixaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fechamentoCaixaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fechamento-caixas} : get all the fechamentoCaixas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fechamentoCaixas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FechamentoCaixaDTO>> getAllFechamentoCaixas(
        FechamentoCaixaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FechamentoCaixas by criteria: {}", criteria);

        Page<FechamentoCaixaDTO> page = fechamentoCaixaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fechamento-caixas/count} : count all the fechamentoCaixas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFechamentoCaixas(FechamentoCaixaCriteria criteria) {
        log.debug("REST request to count FechamentoCaixas by criteria: {}", criteria);
        return ResponseEntity.ok().body(fechamentoCaixaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fechamento-caixas/:id} : get the "id" fechamentoCaixa.
     *
     * @param id the id of the fechamentoCaixaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fechamentoCaixaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FechamentoCaixaDTO> getFechamentoCaixa(@PathVariable("id") Long id) {
        log.debug("REST request to get FechamentoCaixa : {}", id);
        Optional<FechamentoCaixaDTO> fechamentoCaixaDTO = fechamentoCaixaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fechamentoCaixaDTO);
    }

    /**
     * {@code DELETE  /fechamento-caixas/:id} : delete the "id" fechamentoCaixa.
     *
     * @param id the id of the fechamentoCaixaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFechamentoCaixa(@PathVariable("id") Long id) {
        log.debug("REST request to delete FechamentoCaixa : {}", id);
        fechamentoCaixaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
