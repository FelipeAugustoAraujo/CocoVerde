package br.com.lisetech.cocoverde.web.rest;

import br.com.lisetech.cocoverde.repository.FechamentoCaixaDetalhesRepository;
import br.com.lisetech.cocoverde.service.FechamentoCaixaDetalhesQueryService;
import br.com.lisetech.cocoverde.service.FechamentoCaixaDetalhesService;
import br.com.lisetech.cocoverde.service.criteria.FechamentoCaixaDetalhesCriteria;
import br.com.lisetech.cocoverde.service.dto.FechamentoCaixaDetalhesDTO;
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
 * REST controller for managing {@link br.com.lisetech.cocoverde.domain.FechamentoCaixaDetalhes}.
 */
@RestController
@RequestMapping("/api/fechamento-caixa-detalhes")
public class FechamentoCaixaDetalhesResource {

    private final Logger log = LoggerFactory.getLogger(FechamentoCaixaDetalhesResource.class);

    private static final String ENTITY_NAME = "fechamentoCaixaDetalhes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FechamentoCaixaDetalhesService fechamentoCaixaDetalhesService;

    private final FechamentoCaixaDetalhesRepository fechamentoCaixaDetalhesRepository;

    private final FechamentoCaixaDetalhesQueryService fechamentoCaixaDetalhesQueryService;

    public FechamentoCaixaDetalhesResource(
        FechamentoCaixaDetalhesService fechamentoCaixaDetalhesService,
        FechamentoCaixaDetalhesRepository fechamentoCaixaDetalhesRepository,
        FechamentoCaixaDetalhesQueryService fechamentoCaixaDetalhesQueryService
    ) {
        this.fechamentoCaixaDetalhesService = fechamentoCaixaDetalhesService;
        this.fechamentoCaixaDetalhesRepository = fechamentoCaixaDetalhesRepository;
        this.fechamentoCaixaDetalhesQueryService = fechamentoCaixaDetalhesQueryService;
    }

    /**
     * {@code POST  /fechamento-caixa-detalhes} : Create a new fechamentoCaixaDetalhes.
     *
     * @param fechamentoCaixaDetalhesDTO the fechamentoCaixaDetalhesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fechamentoCaixaDetalhesDTO, or with status {@code 400 (Bad Request)} if the fechamentoCaixaDetalhes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FechamentoCaixaDetalhesDTO> createFechamentoCaixaDetalhes(
        @RequestBody FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhesDTO
    ) throws URISyntaxException {
        log.debug("REST request to save FechamentoCaixaDetalhes : {}", fechamentoCaixaDetalhesDTO);
        if (fechamentoCaixaDetalhesDTO.getId() != null) {
            throw new BadRequestAlertException("A new fechamentoCaixaDetalhes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FechamentoCaixaDetalhesDTO result = fechamentoCaixaDetalhesService.save(fechamentoCaixaDetalhesDTO);
        return ResponseEntity
            .created(new URI("/api/fechamento-caixa-detalhes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fechamento-caixa-detalhes/:id} : Updates an existing fechamentoCaixaDetalhes.
     *
     * @param id the id of the fechamentoCaixaDetalhesDTO to save.
     * @param fechamentoCaixaDetalhesDTO the fechamentoCaixaDetalhesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fechamentoCaixaDetalhesDTO,
     * or with status {@code 400 (Bad Request)} if the fechamentoCaixaDetalhesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fechamentoCaixaDetalhesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FechamentoCaixaDetalhesDTO> updateFechamentoCaixaDetalhes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FechamentoCaixaDetalhes : {}, {}", id, fechamentoCaixaDetalhesDTO);
        if (fechamentoCaixaDetalhesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fechamentoCaixaDetalhesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fechamentoCaixaDetalhesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FechamentoCaixaDetalhesDTO result = fechamentoCaixaDetalhesService.update(fechamentoCaixaDetalhesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fechamentoCaixaDetalhesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fechamento-caixa-detalhes/:id} : Partial updates given fields of an existing fechamentoCaixaDetalhes, field will ignore if it is null
     *
     * @param id the id of the fechamentoCaixaDetalhesDTO to save.
     * @param fechamentoCaixaDetalhesDTO the fechamentoCaixaDetalhesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fechamentoCaixaDetalhesDTO,
     * or with status {@code 400 (Bad Request)} if the fechamentoCaixaDetalhesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fechamentoCaixaDetalhesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fechamentoCaixaDetalhesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FechamentoCaixaDetalhesDTO> partialUpdateFechamentoCaixaDetalhes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FechamentoCaixaDetalhes partially : {}, {}", id, fechamentoCaixaDetalhesDTO);
        if (fechamentoCaixaDetalhesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fechamentoCaixaDetalhesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fechamentoCaixaDetalhesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FechamentoCaixaDetalhesDTO> result = fechamentoCaixaDetalhesService.partialUpdate(fechamentoCaixaDetalhesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fechamentoCaixaDetalhesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fechamento-caixa-detalhes} : get all the fechamentoCaixaDetalhes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fechamentoCaixaDetalhes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FechamentoCaixaDetalhesDTO>> getAllFechamentoCaixaDetalhes(
        FechamentoCaixaDetalhesCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FechamentoCaixaDetalhes by criteria: {}", criteria);

        Page<FechamentoCaixaDetalhesDTO> page = fechamentoCaixaDetalhesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fechamento-caixa-detalhes/count} : count all the fechamentoCaixaDetalhes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFechamentoCaixaDetalhes(FechamentoCaixaDetalhesCriteria criteria) {
        log.debug("REST request to count FechamentoCaixaDetalhes by criteria: {}", criteria);
        return ResponseEntity.ok().body(fechamentoCaixaDetalhesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fechamento-caixa-detalhes/:id} : get the "id" fechamentoCaixaDetalhes.
     *
     * @param id the id of the fechamentoCaixaDetalhesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fechamentoCaixaDetalhesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FechamentoCaixaDetalhesDTO> getFechamentoCaixaDetalhes(@PathVariable("id") Long id) {
        log.debug("REST request to get FechamentoCaixaDetalhes : {}", id);
        Optional<FechamentoCaixaDetalhesDTO> fechamentoCaixaDetalhesDTO = fechamentoCaixaDetalhesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fechamentoCaixaDetalhesDTO);
    }

    /**
     * {@code DELETE  /fechamento-caixa-detalhes/:id} : delete the "id" fechamentoCaixaDetalhes.
     *
     * @param id the id of the fechamentoCaixaDetalhesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFechamentoCaixaDetalhes(@PathVariable("id") Long id) {
        log.debug("REST request to delete FechamentoCaixaDetalhes : {}", id);
        fechamentoCaixaDetalhesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
