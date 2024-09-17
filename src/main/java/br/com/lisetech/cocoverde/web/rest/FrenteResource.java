package br.com.lisetech.cocoverde.web.rest;

import br.com.lisetech.cocoverde.repository.FrenteRepository;
import br.com.lisetech.cocoverde.service.FrenteQueryService;
import br.com.lisetech.cocoverde.service.FrenteService;
import br.com.lisetech.cocoverde.service.criteria.FrenteCriteria;
import br.com.lisetech.cocoverde.service.dto.FrenteDTO;
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
 * REST controller for managing {@link br.com.lisetech.cocoverde.domain.Frente}.
 */
@RestController
@RequestMapping("/api/frentes")
public class FrenteResource {

    private final Logger log = LoggerFactory.getLogger(FrenteResource.class);

    private static final String ENTITY_NAME = "frente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FrenteService frenteService;

    private final FrenteRepository frenteRepository;

    private final FrenteQueryService frenteQueryService;

    public FrenteResource(FrenteService frenteService, FrenteRepository frenteRepository, FrenteQueryService frenteQueryService) {
        this.frenteService = frenteService;
        this.frenteRepository = frenteRepository;
        this.frenteQueryService = frenteQueryService;
    }

    /**
     * {@code POST  /frentes} : Create a new frente.
     *
     * @param frenteDTO the frenteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new frenteDTO, or with status {@code 400 (Bad Request)} if the frente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FrenteDTO> createFrente(@RequestBody FrenteDTO frenteDTO) throws URISyntaxException {
        log.debug("REST request to save Frente : {}", frenteDTO);
        if (frenteDTO.getId() != null) {
            throw new BadRequestAlertException("A new frente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FrenteDTO result = frenteService.save(frenteDTO);
        return ResponseEntity
            .created(new URI("/api/frentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /frentes/:id} : Updates an existing frente.
     *
     * @param id the id of the frenteDTO to save.
     * @param frenteDTO the frenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated frenteDTO,
     * or with status {@code 400 (Bad Request)} if the frenteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the frenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FrenteDTO> updateFrente(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FrenteDTO frenteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Frente : {}, {}", id, frenteDTO);
        if (frenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, frenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!frenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FrenteDTO result = frenteService.update(frenteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, frenteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /frentes/:id} : Partial updates given fields of an existing frente, field will ignore if it is null
     *
     * @param id the id of the frenteDTO to save.
     * @param frenteDTO the frenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated frenteDTO,
     * or with status {@code 400 (Bad Request)} if the frenteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the frenteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the frenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FrenteDTO> partialUpdateFrente(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FrenteDTO frenteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Frente partially : {}, {}", id, frenteDTO);
        if (frenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, frenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!frenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FrenteDTO> result = frenteService.partialUpdate(frenteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, frenteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /frentes} : get all the frentes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of frentes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FrenteDTO>> getAllFrentes(
        FrenteCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Frentes by criteria: {}", criteria);

        Page<FrenteDTO> page = frenteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /frentes/count} : count all the frentes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFrentes(FrenteCriteria criteria) {
        log.debug("REST request to count Frentes by criteria: {}", criteria);
        return ResponseEntity.ok().body(frenteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /frentes/:id} : get the "id" frente.
     *
     * @param id the id of the frenteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the frenteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FrenteDTO> getFrente(@PathVariable("id") Long id) {
        log.debug("REST request to get Frente : {}", id);
        Optional<FrenteDTO> frenteDTO = frenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(frenteDTO);
    }

    /**
     * {@code DELETE  /frentes/:id} : delete the "id" frente.
     *
     * @param id the id of the frenteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFrente(@PathVariable("id") Long id) {
        log.debug("REST request to delete Frente : {}", id);
        frenteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
