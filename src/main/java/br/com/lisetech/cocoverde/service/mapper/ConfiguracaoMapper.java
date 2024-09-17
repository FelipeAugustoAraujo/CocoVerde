package br.com.lisetech.cocoverde.service.mapper;

import br.com.lisetech.cocoverde.domain.Configuracao;
import br.com.lisetech.cocoverde.service.dto.ConfiguracaoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Configuracao} and its DTO {@link ConfiguracaoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConfiguracaoMapper extends EntityMapper<ConfiguracaoDTO, Configuracao> {}
