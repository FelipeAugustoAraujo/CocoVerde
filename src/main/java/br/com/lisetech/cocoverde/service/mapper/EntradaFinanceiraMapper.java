package br.com.lisetech.cocoverde.service.mapper;

import br.com.lisetech.cocoverde.domain.EntradaFinanceira;
import br.com.lisetech.cocoverde.domain.Estoque;
import br.com.lisetech.cocoverde.domain.FechamentoCaixaDetalhes;
import br.com.lisetech.cocoverde.domain.Fornecedor;
import br.com.lisetech.cocoverde.domain.Frente;
import br.com.lisetech.cocoverde.service.dto.EntradaFinanceiraDTO;
import br.com.lisetech.cocoverde.service.dto.EstoqueDTO;
import br.com.lisetech.cocoverde.service.dto.FechamentoCaixaDetalhesDTO;
import br.com.lisetech.cocoverde.service.dto.FornecedorDTO;
import br.com.lisetech.cocoverde.service.dto.FrenteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EntradaFinanceira} and its DTO {@link EntradaFinanceiraDTO}.
 */
@Mapper(componentModel = "spring")
public interface EntradaFinanceiraMapper extends EntityMapper<EntradaFinanceiraDTO, EntradaFinanceira> {
    @Mapping(target = "fornecedor", source = "fornecedor", qualifiedByName = "fornecedorId")
    @Mapping(target = "estoque", source = "estoque", qualifiedByName = "estoqueId")
    @Mapping(target = "frente", source = "frente", qualifiedByName = "frenteId")
    @Mapping(target = "fechamentoCaixaDetalhes", source = "fechamentoCaixaDetalhes", qualifiedByName = "fechamentoCaixaDetalhesId")
    EntradaFinanceiraDTO toDto(EntradaFinanceira s);

    @Named("fornecedorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FornecedorDTO toDtoFornecedorId(Fornecedor fornecedor);

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
