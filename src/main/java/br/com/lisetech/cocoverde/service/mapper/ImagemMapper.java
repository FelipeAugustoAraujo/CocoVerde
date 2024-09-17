package br.com.lisetech.cocoverde.service.mapper;

import br.com.lisetech.cocoverde.domain.EntradaFinanceira;
import br.com.lisetech.cocoverde.domain.Imagem;
import br.com.lisetech.cocoverde.domain.SaidaFinanceira;
import br.com.lisetech.cocoverde.service.dto.EntradaFinanceiraDTO;
import br.com.lisetech.cocoverde.service.dto.ImagemDTO;
import br.com.lisetech.cocoverde.service.dto.SaidaFinanceiraDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Imagem} and its DTO {@link ImagemDTO}.
 */
@Mapper(componentModel = "spring")
public interface ImagemMapper extends EntityMapper<ImagemDTO, Imagem> {
    @Mapping(target = "saidaFinanceira", source = "saidaFinanceira", qualifiedByName = "saidaFinanceiraId")
    @Mapping(target = "entradaFinanceira", source = "entradaFinanceira", qualifiedByName = "entradaFinanceiraId")
    ImagemDTO toDto(Imagem s);

    @Named("saidaFinanceiraId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SaidaFinanceiraDTO toDtoSaidaFinanceiraId(SaidaFinanceira saidaFinanceira);

    @Named("entradaFinanceiraId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EntradaFinanceiraDTO toDtoEntradaFinanceiraId(EntradaFinanceira entradaFinanceira);
}
