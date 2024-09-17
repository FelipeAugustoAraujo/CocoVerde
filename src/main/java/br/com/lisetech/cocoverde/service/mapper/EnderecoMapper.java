package br.com.lisetech.cocoverde.service.mapper;

import br.com.lisetech.cocoverde.domain.Cidade;
import br.com.lisetech.cocoverde.domain.Cliente;
import br.com.lisetech.cocoverde.domain.Endereco;
import br.com.lisetech.cocoverde.domain.Fornecedor;
import br.com.lisetech.cocoverde.domain.Funcionario;
import br.com.lisetech.cocoverde.service.dto.CidadeDTO;
import br.com.lisetech.cocoverde.service.dto.ClienteDTO;
import br.com.lisetech.cocoverde.service.dto.EnderecoDTO;
import br.com.lisetech.cocoverde.service.dto.FornecedorDTO;
import br.com.lisetech.cocoverde.service.dto.FuncionarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Endereco} and its DTO {@link EnderecoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EnderecoMapper extends EntityMapper<EnderecoDTO, Endereco> {
    @Mapping(target = "fornecedor", source = "fornecedor", qualifiedByName = "fornecedorId")
    @Mapping(target = "funcionario", source = "funcionario", qualifiedByName = "funcionarioId")
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "clienteId")
    @Mapping(target = "cidade", source = "cidade", qualifiedByName = "cidadeId")
    EnderecoDTO toDto(Endereco s);

    @Named("fornecedorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FornecedorDTO toDtoFornecedorId(Fornecedor fornecedor);

    @Named("funcionarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FuncionarioDTO toDtoFuncionarioId(Funcionario funcionario);

    @Named("clienteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClienteDTO toDtoClienteId(Cliente cliente);

    @Named("cidadeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CidadeDTO toDtoCidadeId(Cidade cidade);
}
