package br.com.lisetech.cocoverde.repository;

import br.com.lisetech.cocoverde.domain.Frente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Frente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FrenteRepository extends JpaRepository<Frente, Long>, JpaSpecificationExecutor<Frente> {}
