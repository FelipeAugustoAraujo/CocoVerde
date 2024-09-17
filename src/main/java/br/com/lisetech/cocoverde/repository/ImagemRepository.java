package br.com.lisetech.cocoverde.repository;

import br.com.lisetech.cocoverde.domain.Imagem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Imagem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long>, JpaSpecificationExecutor<Imagem> {}
