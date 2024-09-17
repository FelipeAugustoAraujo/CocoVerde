package br.com.lisetech.cocoverde.web.rest;

import br.com.lisetech.cocoverde.repository.ImagemRepository;
import br.com.lisetech.cocoverde.service.ImagemQueryService;
import br.com.lisetech.cocoverde.service.ImagemService;
import br.com.lisetech.cocoverde.service.criteria.ImagemCriteria;
import br.com.lisetech.cocoverde.service.dto.ImagemDTO;
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
 * REST controller for managing {@link br.com.lisetech.cocoverde.domain.Imagem}.
 */
@RestController
@RequestMapping("/api/imagems")
public class ImagemResource {

    private final Logger log = LoggerFactory.getLogger(ImagemResource.class);

    private static final String ENTITY_NAME = "imagem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImagemService imagemService;

    private final ImagemRepository imagemRepository;

    private final ImagemQueryService imagemQueryService;

    public ImagemResource(ImagemService imagemService, ImagemRepository imagemRepository, ImagemQueryService imagemQueryService) {
        this.imagemService = imagemService;
        this.imagemRepository = imagemRepository;
        this.imagemQueryService = imagemQueryService;
    }

    /**
     * {@code POST  /imagems} : Create a new imagem.
     *
     * @param imagemDTO the imagemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new imagemDTO, or with status {@code 400 (Bad Request)} if the imagem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ImagemDTO> createImagem(@RequestBody ImagemDTO imagemDTO) throws URISyntaxException {
        log.debug("REST request to save Imagem : {}", imagemDTO);
        if (imagemDTO.getId() != null) {
            throw new BadRequestAlertException("A new imagem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImagemDTO result = imagemService.save(imagemDTO);
        return ResponseEntity
            .created(new URI("/api/imagems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /imagems/:id} : Updates an existing imagem.
     *
     * @param id the id of the imagemDTO to save.
     * @param imagemDTO the imagemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imagemDTO,
     * or with status {@code 400 (Bad Request)} if the imagemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imagemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ImagemDTO> updateImagem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ImagemDTO imagemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Imagem : {}, {}", id, imagemDTO);
        if (imagemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, imagemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!imagemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ImagemDTO result = imagemService.update(imagemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, imagemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /imagems/:id} : Partial updates given fields of an existing imagem, field will ignore if it is null
     *
     * @param id the id of the imagemDTO to save.
     * @param imagemDTO the imagemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imagemDTO,
     * or with status {@code 400 (Bad Request)} if the imagemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the imagemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the imagemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ImagemDTO> partialUpdateImagem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ImagemDTO imagemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Imagem partially : {}, {}", id, imagemDTO);
        if (imagemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, imagemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!imagemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ImagemDTO> result = imagemService.partialUpdate(imagemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, imagemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /imagems} : get all the imagems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of imagems in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ImagemDTO>> getAllImagems(
        ImagemCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Imagems by criteria: {}", criteria);

        Page<ImagemDTO> page = imagemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /imagems/count} : count all the imagems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countImagems(ImagemCriteria criteria) {
        log.debug("REST request to count Imagems by criteria: {}", criteria);
        return ResponseEntity.ok().body(imagemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /imagems/:id} : get the "id" imagem.
     *
     * @param id the id of the imagemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imagemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ImagemDTO> getImagem(@PathVariable("id") Long id) {
        log.debug("REST request to get Imagem : {}", id);
        Optional<ImagemDTO> imagemDTO = imagemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imagemDTO);
    }

    /**
     * {@code DELETE  /imagems/:id} : delete the "id" imagem.
     *
     * @param id the id of the imagemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImagem(@PathVariable("id") Long id) {
        log.debug("REST request to delete Imagem : {}", id);
        imagemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
