package br.com.lisetech.cocoverde.service.mapper;

import br.com.lisetech.cocoverde.domain.Fornecedor;
import br.com.lisetech.cocoverde.domain.Produto;
import br.com.lisetech.cocoverde.service.dto.FornecedorDTO;
import br.com.lisetech.cocoverde.service.dto.ProdutoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fornecedor} and its DTO {@link FornecedorDTO}.
 */
@Mapper(componentModel = "spring")
public interface FornecedorMapper extends EntityMapper<FornecedorDTO, Fornecedor> {
    @Mapping(target = "produtos", source = "produtos", qualifiedByName = "produtoIdSet")
    FornecedorDTO toDto(Fornecedor s);

    @Mapping(target = "removeProduto", ignore = true)
    Fornecedor toEntity(FornecedorDTO fornecedorDTO);

    @Named("produtoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProdutoDTO toDtoProdutoId(Produto produto);

    @Named("produtoIdSet")
    default Set<ProdutoDTO> toDtoProdutoIdSet(Set<Produto> produto) {
        return produto.stream().map(this::toDtoProdutoId).collect(Collectors.toSet());
    }
}
