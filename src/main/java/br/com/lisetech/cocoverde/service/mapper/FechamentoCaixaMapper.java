package br.com.lisetech.cocoverde.service.mapper;

import br.com.lisetech.cocoverde.domain.FechamentoCaixa;
import br.com.lisetech.cocoverde.service.dto.FechamentoCaixaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FechamentoCaixa} and its DTO {@link FechamentoCaixaDTO}.
 */
@Mapper(componentModel = "spring")
public interface FechamentoCaixaMapper extends EntityMapper<FechamentoCaixaDTO, FechamentoCaixa> {}
