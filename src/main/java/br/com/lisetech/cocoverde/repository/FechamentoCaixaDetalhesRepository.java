package br.com.lisetech.cocoverde.repository;

import br.com.lisetech.cocoverde.domain.FechamentoCaixaDetalhes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FechamentoCaixaDetalhes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FechamentoCaixaDetalhesRepository
    extends JpaRepository<FechamentoCaixaDetalhes, Long>, JpaSpecificationExecutor<FechamentoCaixaDetalhes> {}
