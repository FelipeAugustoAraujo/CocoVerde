package br.com.lisetech.cocoverde.service;

import br.com.lisetech.cocoverde.domain.Funcionario;
import br.com.lisetech.cocoverde.repository.FuncionarioRepository;
import br.com.lisetech.cocoverde.service.dto.FuncionarioDTO;
import br.com.lisetech.cocoverde.service.mapper.FuncionarioMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.lisetech.cocoverde.domain.Funcionario}.
 */
@Service
@Transactional
public class FuncionarioService {

    private final Logger log = LoggerFactory.getLogger(FuncionarioService.class);

    private final FuncionarioRepository funcionarioRepository;

    private final FuncionarioMapper funcionarioMapper;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioMapper = funcionarioMapper;
    }

    /**
     * Save a funcionario.
     *
     * @param funcionarioDTO the entity to save.
     * @return the persisted entity.
     */
    public FuncionarioDTO save(FuncionarioDTO funcionarioDTO) {
        log.debug("Request to save Funcionario : {}", funcionarioDTO);
        Funcionario funcionario = funcionarioMapper.toEntity(funcionarioDTO);
        funcionario = funcionarioRepository.save(funcionario);
        return funcionarioMapper.toDto(funcionario);
    }

    /**
     * Update a funcionario.
     *
     * @param funcionarioDTO the entity to save.
     * @return the persisted entity.
     */
    public FuncionarioDTO update(FuncionarioDTO funcionarioDTO) {
        log.debug("Request to update Funcionario : {}", funcionarioDTO);
        Funcionario funcionario = funcionarioMapper.toEntity(funcionarioDTO);
        funcionario = funcionarioRepository.save(funcionario);
        return funcionarioMapper.toDto(funcionario);
    }

    /**
     * Partially update a funcionario.
     *
     * @param funcionarioDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FuncionarioDTO> partialUpdate(FuncionarioDTO funcionarioDTO) {
        log.debug("Request to partially update Funcionario : {}", funcionarioDTO);

        return funcionarioRepository
            .findById(funcionarioDTO.getId())
            .map(existingFuncionario -> {
                funcionarioMapper.partialUpdate(existingFuncionario, funcionarioDTO);

                return existingFuncionario;
            })
            .map(funcionarioRepository::save)
            .map(funcionarioMapper::toDto);
    }

    /**
     * Get all the funcionarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FuncionarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Funcionarios");
        return funcionarioRepository.findAll(pageable).map(funcionarioMapper::toDto);
    }

    /**
     *  Get all the funcionarios where Endereco is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FuncionarioDTO> findAllWhereEnderecoIsNull() {
        log.debug("Request to get all funcionarios where Endereco is null");
        return StreamSupport
            .stream(funcionarioRepository.findAll().spliterator(), false)
            .filter(funcionario -> funcionario.getEndereco() == null)
            .map(funcionarioMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one funcionario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FuncionarioDTO> findOne(Long id) {
        log.debug("Request to get Funcionario : {}", id);
        return funcionarioRepository.findById(id).map(funcionarioMapper::toDto);
    }

    /**
     * Delete the funcionario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Funcionario : {}", id);
        funcionarioRepository.deleteById(id);
    }
}
