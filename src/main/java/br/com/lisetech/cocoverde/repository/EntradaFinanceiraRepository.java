package br.com.lisetech.cocoverde.repository;

import br.com.lisetech.cocoverde.domain.EntradaFinanceira;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EntradaFinanceira entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntradaFinanceiraRepository extends JpaRepository<EntradaFinanceira, Long>, JpaSpecificationExecutor<EntradaFinanceira> {}
