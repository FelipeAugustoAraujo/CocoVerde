package br.com.lisetech.cocoverde.repository;

import br.com.lisetech.cocoverde.domain.Fornecedor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface FornecedorRepositoryWithBagRelationships {
    Optional<Fornecedor> fetchBagRelationships(Optional<Fornecedor> fornecedor);

    List<Fornecedor> fetchBagRelationships(List<Fornecedor> fornecedors);

    Page<Fornecedor> fetchBagRelationships(Page<Fornecedor> fornecedors);
}
