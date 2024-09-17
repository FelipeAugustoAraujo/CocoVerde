package br.com.lisetech.cocoverde.service.mapper;

import br.com.lisetech.cocoverde.domain.Frente;
import br.com.lisetech.cocoverde.service.dto.FrenteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Frente} and its DTO {@link FrenteDTO}.
 */
@Mapper(componentModel = "spring")
public interface FrenteMapper extends EntityMapper<FrenteDTO, Frente> {}
