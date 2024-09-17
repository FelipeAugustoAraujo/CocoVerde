package br.com.lisetech.cocoverde.service.mapper;

import br.com.lisetech.cocoverde.domain.Estoque;
import br.com.lisetech.cocoverde.domain.Frente;
import br.com.lisetech.cocoverde.domain.Produto;
import br.com.lisetech.cocoverde.service.dto.EstoqueDTO;
import br.com.lisetech.cocoverde.service.dto.FrenteDTO;
import br.com.lisetech.cocoverde.service.dto.ProdutoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produto} and its DTO {@link ProdutoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProdutoMapper extends EntityMapper<ProdutoDTO, Produto> {
    @Mapping(target = "estoque", source = "estoque", qualifiedByName = "estoqueId")
    @Mapping(target = "frente", source = "frente", qualifiedByName = "frenteId")
    ProdutoDTO toDto(Produto s);

    @Named("estoqueId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EstoqueDTO toDtoEstoqueId(Estoque estoque);

    @Named("frenteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FrenteDTO toDtoFrenteId(Frente frente);
}
