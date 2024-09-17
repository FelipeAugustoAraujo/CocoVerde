package br.com.lisetech.cocoverde.repository;

import br.com.lisetech.cocoverde.domain.SaidaFinanceira;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SaidaFinanceira entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaidaFinanceiraRepository extends JpaRepository<SaidaFinanceira, Long>, JpaSpecificationExecutor<SaidaFinanceira> {}
