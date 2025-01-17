package br.com.lisetech.cocoverde.web.rest;

import br.com.lisetech.cocoverde.repository.EstoqueRepository;
import br.com.lisetech.cocoverde.service.EstoqueQueryService;
import br.com.lisetech.cocoverde.service.EstoqueService;
import br.com.lisetech.cocoverde.service.criteria.EstoqueCriteria;
import br.com.lisetech.cocoverde.service.dto.EstoqueDTO;
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
 * REST controller for managing {@link br.com.lisetech.cocoverde.domain.Estoque}.
 */
@RestController
@RequestMapping("/api/estoques")
public class EstoqueResource {

    private final Logger log = LoggerFactory.getLogger(EstoqueResource.class);

    private static final String ENTITY_NAME = "estoque";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstoqueService estoqueService;

    private final EstoqueRepository estoqueRepository;

    private final EstoqueQueryService estoqueQueryService;

    public EstoqueResource(EstoqueService estoqueService, EstoqueRepository estoqueRepository, EstoqueQueryService estoqueQueryService) {
        this.estoqueService = estoqueService;
        this.estoqueRepository = estoqueRepository;
        this.estoqueQueryService = estoqueQueryService;
    }

    /**
     * {@code POST  /estoques} : Create a new estoque.
     *
     * @param estoqueDTO the estoqueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estoqueDTO, or with status {@code 400 (Bad Request)} if the estoque has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EstoqueDTO> createEstoque(@RequestBody EstoqueDTO estoqueDTO) throws URISyntaxException {
        log.debug("REST request to save Estoque : {}", estoqueDTO);
        if (estoqueDTO.getId() != null) {
            throw new BadRequestAlertException("A new estoque cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstoqueDTO result = estoqueService.save(estoqueDTO);
        return ResponseEntity
            .created(new URI("/api/estoques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estoques/:id} : Updates an existing estoque.
     *
     * @param id the id of the estoqueDTO to save.
     * @param estoqueDTO the estoqueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estoqueDTO,
     * or with status {@code 400 (Bad Request)} if the estoqueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estoqueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EstoqueDTO> updateEstoque(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstoqueDTO estoqueDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Estoque : {}, {}", id, estoqueDTO);
        if (estoqueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estoqueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estoqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EstoqueDTO result = estoqueService.update(estoqueDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estoqueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /estoques/:id} : Partial updates given fields of an existing estoque, field will ignore if it is null
     *
     * @param id the id of the estoqueDTO to save.
     * @param estoqueDTO the estoqueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estoqueDTO,
     * or with status {@code 400 (Bad Request)} if the estoqueDTO is not valid,
     * or with status {@code 404 (Not Found)} if the estoqueDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the estoqueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EstoqueDTO> partialUpdateEstoque(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstoqueDTO estoqueDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Estoque partially : {}, {}", id, estoqueDTO);
        if (estoqueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estoqueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estoqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EstoqueDTO> result = estoqueService.partialUpdate(estoqueDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estoqueDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /estoques} : get all the estoques.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estoques in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EstoqueDTO>> getAllEstoques(
        EstoqueCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Estoques by criteria: {}", criteria);

        Page<EstoqueDTO> page = estoqueQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /estoques/count} : count all the estoques.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEstoques(EstoqueCriteria criteria) {
        log.debug("REST request to count Estoques by criteria: {}", criteria);
        return ResponseEntity.ok().body(estoqueQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /estoques/:id} : get the "id" estoque.
     *
     * @param id the id of the estoqueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estoqueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EstoqueDTO> getEstoque(@PathVariable("id") Long id) {
        log.debug("REST request to get Estoque : {}", id);
        Optional<EstoqueDTO> estoqueDTO = estoqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estoqueDTO);
    }

    /**
     * {@code DELETE  /estoques/:id} : delete the "id" estoque.
     *
     * @param id the id of the estoqueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstoque(@PathVariable("id") Long id) {
        log.debug("REST request to delete Estoque : {}", id);
        estoqueService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
