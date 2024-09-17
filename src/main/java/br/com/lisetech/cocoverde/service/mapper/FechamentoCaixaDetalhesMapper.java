package br.com.lisetech.cocoverde.service.mapper;

import br.com.lisetech.cocoverde.domain.FechamentoCaixa;
import br.com.lisetech.cocoverde.domain.FechamentoCaixaDetalhes;
import br.com.lisetech.cocoverde.service.dto.FechamentoCaixaDTO;
import br.com.lisetech.cocoverde.service.dto.FechamentoCaixaDetalhesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FechamentoCaixaDetalhes} and its DTO {@link FechamentoCaixaDetalhesDTO}.
 */
@Mapper(componentModel = "spring")
public interface FechamentoCaixaDetalhesMapper extends EntityMapper<FechamentoCaixaDetalhesDTO, FechamentoCaixaDetalhes> {
    @Mapping(target = "fechamentoCaixa", source = "fechamentoCaixa", qualifiedByName = "fechamentoCaixaId")
    FechamentoCaixaDetalhesDTO toDto(FechamentoCaixaDetalhes s);

    @Named("fechamentoCaixaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FechamentoCaixaDTO toDtoFechamentoCaixaId(FechamentoCaixa fechamentoCaixa);
}
