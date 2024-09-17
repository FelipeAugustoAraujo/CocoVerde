package br.com.lisetech.cocoverde.service.mapper;

import br.com.lisetech.cocoverde.domain.DetalhesEntradaFinanceira;
import br.com.lisetech.cocoverde.domain.EntradaFinanceira;
import br.com.lisetech.cocoverde.domain.Produto;
import br.com.lisetech.cocoverde.service.dto.DetalhesEntradaFinanceiraDTO;
import br.com.lisetech.cocoverde.service.dto.EntradaFinanceiraDTO;
import br.com.lisetech.cocoverde.service.dto.ProdutoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetalhesEntradaFinanceira} and its DTO {@link DetalhesEntradaFinanceiraDTO}.
 */
@Mapper(componentModel = "spring")
public interface DetalhesEntradaFinanceiraMapper extends EntityMapper<DetalhesEntradaFinanceiraDTO, DetalhesEntradaFinanceira> {
    @Mapping(target = "produto", source = "produto", qualifiedByName = "produtoId")
    @Mapping(target = "entradaFinanceira", source = "entradaFinanceira", qualifiedByName = "entradaFinanceiraId")
    DetalhesEntradaFinanceiraDTO toDto(DetalhesEntradaFinanceira s);

    @Named("produtoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProdutoDTO toDtoProdutoId(Produto produto);

    @Named("entradaFinanceiraId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EntradaFinanceiraDTO toDtoEntradaFinanceiraId(EntradaFinanceira entradaFinanceira);
}
