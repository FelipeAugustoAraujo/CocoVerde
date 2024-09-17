package br.com.lisetech.cocoverde.repository;

import br.com.lisetech.cocoverde.domain.DetalhesEntradaFinanceira;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DetalhesEntradaFinanceira entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalhesEntradaFinanceiraRepository
    extends JpaRepository<DetalhesEntradaFinanceira, Long>, JpaSpecificationExecutor<DetalhesEntradaFinanceira> {}
