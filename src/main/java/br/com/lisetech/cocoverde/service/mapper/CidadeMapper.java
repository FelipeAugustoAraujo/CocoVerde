package br.com.lisetech.cocoverde.service.mapper;

import br.com.lisetech.cocoverde.domain.Cidade;
import br.com.lisetech.cocoverde.service.dto.CidadeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cidade} and its DTO {@link CidadeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CidadeMapper extends EntityMapper<CidadeDTO, Cidade> {}
