package br.com.lisetech.cocoverde.repository;

import br.com.lisetech.cocoverde.domain.DetalhesSaidaFinanceira;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DetalhesSaidaFinanceira entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalhesSaidaFinanceiraRepository
    extends JpaRepository<DetalhesSaidaFinanceira, Long>, JpaSpecificationExecutor<DetalhesSaidaFinanceira> {}
