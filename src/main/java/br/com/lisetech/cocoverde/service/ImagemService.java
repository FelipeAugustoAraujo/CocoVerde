package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.Imagem;
import br.com.lisetech.cocoverde.repository.ImagemRepository;
import br.com.lisetech.cocoverde.service.dto.ImagemDTO;
import br.com.lisetech.cocoverde.service.mapper.ImagemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.lisetech.cocoverde.domain.Imagem}.
 */
@Service
@Transactional
public class ImagemService {

    private final Logger log = LoggerFactory.getLogger(ImagemService.class);

    private final ImagemRepository imagemRepository;

    private final ImagemMapper imagemMapper;

    public ImagemService(ImagemRepository imagemRepository, ImagemMapper imagemMapper) {
        this.imagemRepository = imagemRepository;
        this.imagemMapper = imagemMapper;
    }

    /**
     * Save a imagem.
     *
     * @param imagemDTO the entity to save.
     * @return the persisted entity.
     */
    public ImagemDTO save(ImagemDTO imagemDTO) {
        log.debug("Request to save Imagem : {}", imagemDTO);
        Imagem imagem = imagemMapper.toEntity(imagemDTO);
        imagem = imagemRepository.save(imagem);
        return imagemMapper.toDto(imagem);
    }

    /**
     * Update a imagem.
     *
     * @param imagemDTO the entity to save.
     * @return the persisted entity.
     */
    public ImagemDTO update(ImagemDTO imagemDTO) {
        log.debug("Request to update Imagem : {}", imagemDTO);
        Imagem imagem = imagemMapper.toEntity(imagemDTO);
        imagem = imagemRepository.save(imagem);
        return imagemMapper.toDto(imagem);
    }

    /**
     * Partially update a imagem.
     *
     * @param imagemDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ImagemDTO> partialUpdate(ImagemDTO imagemDTO) {
        log.debug("Request to partially update Imagem : {}", imagemDTO);

        return imagemRepository
            .findById(imagemDTO.getId())
            .map(existingImagem -> {
                imagemMapper.partialUpdate(existingImagem, imagemDTO);

                return existingImagem;
            })
            .map(imagemRepository::save)
            .map(imagemMapper::toDto);
    }

    /**
     * Get all the imagems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ImagemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Imagems");
        return imagemRepository.findAll(pageable).map(imagemMapper::toDto);
    }

    /**
     * Get one imagem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ImagemDTO> findOne(Long id) {
        log.debug("Request to get Imagem : {}", id);
        return imagemRepository.findById(id).map(imagemMapper::toDto);
    }

    /**
     * Delete the imagem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Imagem : {}", id);
        imagemRepository.deleteById(id);
    }
}
