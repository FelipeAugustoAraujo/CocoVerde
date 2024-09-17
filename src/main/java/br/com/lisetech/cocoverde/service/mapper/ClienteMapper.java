package br.com.lisetech.cocoverde.service.mapper;

import br.com.lisetech.cocoverde.domain.Cliente;
import br.com.lisetech.cocoverde.service.dto.ClienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cliente} and its DTO {@link ClienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {}
