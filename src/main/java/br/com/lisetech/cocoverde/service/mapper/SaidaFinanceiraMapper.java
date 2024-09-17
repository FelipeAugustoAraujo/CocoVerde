package br.com.lisetech.cocoverde.service.mapper;

import br.com.lisetech.cocoverde.domain.Estoque;
import br.com.lisetech.cocoverde.domain.FechamentoCaixaDetalhes;
import br.com.lisetech.cocoverde.domain.Frente;
import br.com.lisetech.cocoverde.domain.SaidaFinanceira;
import br.com.lisetech.cocoverde.service.dto.EstoqueDTO;
import br.com.lisetech.cocoverde.service.dto.FechamentoCaixaDetalhesDTO;
import br.com.lisetech.cocoverde.service.dto.FrenteDTO;
import br.com.lisetech.cocoverde.service.dto.SaidaFinanceiraDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SaidaFinanceira} and its DTO {@link SaidaFinanceiraDTO}.
 */
@Mapper(componentModel = "spring")
public interface SaidaFinanceiraMapper extends EntityMapper<SaidaFinanceiraDTO, SaidaFinanceira> {
    @Mapping(target = "estoque", source = "estoque", qualifiedByName = "estoqueId")
    @Mapping(target = "frente", source = "frente", qualifiedByName = "frenteId")
    @Mapping(target = "fechamentoCaixaDetalhes", source = "fechamentoCaixaDetalhes", qualifiedByName = "fechamentoCaixaDetalhesId")
    SaidaFinanceiraDTO toDto(SaidaFinanceira s);

    @Named("estoqueId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EstoqueDTO toDtoEstoqueId(Estoque estoque);

    @Named("frenteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FrenteDTO toDtoFrenteId(Frente frente);

    @Named("fechamentoCaixaDetalhesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FechamentoCaixaDetalhesDTO toDtoFechamentoCaixaDetalhesId(FechamentoCaixaDetalhes fechamentoCaixaDetalhes);
}
