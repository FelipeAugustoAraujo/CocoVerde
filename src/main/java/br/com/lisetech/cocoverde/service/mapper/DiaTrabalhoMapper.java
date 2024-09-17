package br.com.lisetech.cocoverde.service.mapper;

import br.com.lisetech.cocoverde.domain.DiaTrabalho;
import br.com.lisetech.cocoverde.service.dto.DiaTrabalhoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DiaTrabalho} and its DTO {@link DiaTrabalhoDTO}.
 */
@Mapper(componentModel = "spring")
public interface DiaTrabalhoMapper extends EntityMapper<DiaTrabalhoDTO, DiaTrabalho> {}
