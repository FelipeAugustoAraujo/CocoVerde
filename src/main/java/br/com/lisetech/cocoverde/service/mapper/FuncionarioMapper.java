package br.com.lisetech.cocoverde.service.mapper;

import br.com.lisetech.cocoverde.domain.Funcionario;
import br.com.lisetech.cocoverde.service.dto.FuncionarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Funcionario} and its DTO {@link FuncionarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface FuncionarioMapper extends EntityMapper<FuncionarioDTO, Funcionario> {}
