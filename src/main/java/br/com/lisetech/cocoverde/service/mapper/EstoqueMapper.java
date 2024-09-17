package br.com.lisetech.cocoverde.service.mapper;

import br.com.lisetech.cocoverde.domain.Estoque;
import br.com.lisetech.cocoverde.service.dto.EstoqueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Estoque} and its DTO {@link EstoqueDTO}.
 */
@Mapper(componentModel = "spring")
public interface EstoqueMapper extends EntityMapper<EstoqueDTO, Estoque> {}
