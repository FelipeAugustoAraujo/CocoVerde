package br.com.lisetech.cocoverde.repository;

import br.com.lisetech.cocoverde.domain.Fornecedor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Fornecedor entity.
 *
 * When extending this class, extend FornecedorRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface FornecedorRepository
    extends FornecedorRepositoryWithBagRelationships, JpaRepository<Fornecedor, Long>, JpaSpecificationExecutor<Fornecedor> {
    default Optional<Fornecedor> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Fornecedor> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Fornecedor> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
