package br.com.lisetech.cocoverde.repository;

import br.com.lisetech.cocoverde.domain.DiaTrabalho;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DiaTrabalho entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiaTrabalhoRepository extends JpaRepository<DiaTrabalho, Long>, JpaSpecificationExecutor<DiaTrabalho> {}
