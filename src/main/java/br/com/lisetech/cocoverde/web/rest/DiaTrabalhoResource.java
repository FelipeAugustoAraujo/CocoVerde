package br.com.lisetech.cocoverde.web.rest;

import br.com.lisetech.cocoverde.repository.DiaTrabalhoRepository;
import br.com.lisetech.cocoverde.service.DiaTrabalhoQueryService;
import br.com.lisetech.cocoverde.service.DiaTrabalhoService;
import br.com.lisetech.cocoverde.service.criteria.DiaTrabalhoCriteria;
import br.com.lisetech.cocoverde.service.dto.DiaTrabalhoDTO;
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
 * REST controller for managing {@link br.com.lisetech.cocoverde.domain.DiaTrabalho}.
 */
@RestController
@RequestMapping("/api/dia-trabalhos")
public class DiaTrabalhoResource {

    private final Logger log = LoggerFactory.getLogger(DiaTrabalhoResource.class);

    private static final String ENTITY_NAME = "diaTrabalho";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiaTrabalhoService diaTrabalhoService;

    private final DiaTrabalhoRepository diaTrabalhoRepository;

    private final DiaTrabalhoQueryService diaTrabalhoQueryService;

    public DiaTrabalhoResource(
        DiaTrabalhoService diaTrabalhoService,
        DiaTrabalhoRepository diaTrabalhoRepository,
        DiaTrabalhoQueryService diaTrabalhoQueryService
    ) {
        this.diaTrabalhoService = diaTrabalhoService;
        this.diaTrabalhoRepository = diaTrabalhoRepository;
        this.diaTrabalhoQueryService = diaTrabalhoQueryService;
    }

    /**
     * {@code POST  /dia-trabalhos} : Create a new diaTrabalho.
     *
     * @param diaTrabalhoDTO the diaTrabalhoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diaTrabalhoDTO, or with status {@code 400 (Bad Request)} if the diaTrabalho has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DiaTrabalhoDTO> createDiaTrabalho(@RequestBody DiaTrabalhoDTO diaTrabalhoDTO) throws URISyntaxException {
        log.debug("REST request to save DiaTrabalho : {}", diaTrabalhoDTO);
        if (diaTrabalhoDTO.getId() != null) {
            throw new BadRequestAlertException("A new diaTrabalho cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiaTrabalhoDTO result = diaTrabalhoService.save(diaTrabalhoDTO);
        return ResponseEntity
            .created(new URI("/api/dia-trabalhos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dia-trabalhos/:id} : Updates an existing diaTrabalho.
     *
     * @param id the id of the diaTrabalhoDTO to save.
     * @param diaTrabalhoDTO the diaTrabalhoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diaTrabalhoDTO,
     * or with status {@code 400 (Bad Request)} if the diaTrabalhoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diaTrabalhoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DiaTrabalhoDTO> updateDiaTrabalho(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DiaTrabalhoDTO diaTrabalhoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DiaTrabalho : {}, {}", id, diaTrabalhoDTO);
        if (diaTrabalhoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diaTrabalhoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diaTrabalhoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DiaTrabalhoDTO result = diaTrabalhoService.update(diaTrabalhoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, diaTrabalhoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dia-trabalhos/:id} : Partial updates given fields of an existing diaTrabalho, field will ignore if it is null
     *
     * @param id the id of the diaTrabalhoDTO to save.
     * @param diaTrabalhoDTO the diaTrabalhoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diaTrabalhoDTO,
     * or with status {@code 400 (Bad Request)} if the diaTrabalhoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the diaTrabalhoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the diaTrabalhoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DiaTrabalhoDTO> partialUpdateDiaTrabalho(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DiaTrabalhoDTO diaTrabalhoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DiaTrabalho partially : {}, {}", id, diaTrabalhoDTO);
        if (diaTrabalhoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diaTrabalhoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diaTrabalhoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DiaTrabalhoDTO> result = diaTrabalhoService.partialUpdate(diaTrabalhoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, diaTrabalhoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dia-trabalhos} : get all the diaTrabalhos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diaTrabalhos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DiaTrabalhoDTO>> getAllDiaTrabalhos(
        DiaTrabalhoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DiaTrabalhos by criteria: {}", criteria);

        Page<DiaTrabalhoDTO> page = diaTrabalhoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dia-trabalhos/count} : count all the diaTrabalhos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDiaTrabalhos(DiaTrabalhoCriteria criteria) {
        log.debug("REST request to count DiaTrabalhos by criteria: {}", criteria);
        return ResponseEntity.ok().body(diaTrabalhoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dia-trabalhos/:id} : get the "id" diaTrabalho.
     *
     * @param id the id of the diaTrabalhoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diaTrabalhoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DiaTrabalhoDTO> getDiaTrabalho(@PathVariable("id") Long id) {
        log.debug("REST request to get DiaTrabalho : {}", id);
        Optional<DiaTrabalhoDTO> diaTrabalhoDTO = diaTrabalhoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diaTrabalhoDTO);
    }

    /**
     * {@code DELETE  /dia-trabalhos/:id} : delete the "id" diaTrabalho.
     *
     * @param id the id of the diaTrabalhoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiaTrabalho(@PathVariable("id") Long id) {
        log.debug("REST request to delete DiaTrabalho : {}", id);
        diaTrabalhoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
