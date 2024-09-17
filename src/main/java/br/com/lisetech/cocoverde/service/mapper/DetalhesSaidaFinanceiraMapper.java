package br.com.lisetech.cocoverde.service.mapper;

import br.com.lisetech.cocoverde.domain.DetalhesSaidaFinanceira;
import br.com.lisetech.cocoverde.service.dto.DetalhesSaidaFinanceiraDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetalhesSaidaFinanceira} and its DTO {@link DetalhesSaidaFinanceiraDTO}.
 */
@Mapper(componentModel = "spring")
public interface DetalhesSaidaFinanceiraMapper extends EntityMapper<DetalhesSaidaFinanceiraDTO, DetalhesSaidaFinanceira> {}
