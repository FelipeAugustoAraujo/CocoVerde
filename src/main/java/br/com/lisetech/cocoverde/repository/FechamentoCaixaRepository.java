package br.com.lisetech.cocoverde.repository;

import br.com.lisetech.cocoverde.domain.FechamentoCaixa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FechamentoCaixa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FechamentoCaixaRepository extends JpaRepository<FechamentoCaixa, Long>, JpaSpecificationExecutor<FechamentoCaixa> {}
