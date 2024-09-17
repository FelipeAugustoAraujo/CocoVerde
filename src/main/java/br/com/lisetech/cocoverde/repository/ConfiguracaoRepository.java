package br.com.lisetech.cocoverde.repository;

import br.com.lisetech.cocoverde.domain.Configuracao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Configuracao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Long>, JpaSpecificationExecutor<Configuracao> {}
