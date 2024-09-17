package br.com.lisetech.cocoverde.repository;

import br.com.lisetech.cocoverde.domain.Fornecedor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class FornecedorRepositoryWithBagRelationshipsImpl implements FornecedorRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Fornecedor> fetchBagRelationships(Optional<Fornecedor> fornecedor) {
        return fornecedor.map(this::fetchProdutos);
    }

    @Override
    public Page<Fornecedor> fetchBagRelationships(Page<Fornecedor> fornecedors) {
        return new PageImpl<>(fetchBagRelationships(fornecedors.getContent()), fornecedors.getPageable(), fornecedors.getTotalElements());
    }

    @Override
    public List<Fornecedor> fetchBagRelationships(List<Fornecedor> fornecedors) {
        return Optional.of(fornecedors).map(this::fetchProdutos).orElse(Collections.emptyList());
    }

    Fornecedor fetchProdutos(Fornecedor result) {
        return entityManager
            .createQuery(
                "select fornecedor from Fornecedor fornecedor left join fetch fornecedor.produtos where fornecedor.id = :id",
                Fornecedor.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Fornecedor> fetchProdutos(List<Fornecedor> fornecedors) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, fornecedors.size()).forEach(index -> order.put(fornecedors.get(index).getId(), index));
        List<Fornecedor> result = entityManager
            .createQuery(
                "select fornecedor from Fornecedor fornecedor left join fetch fornecedor.produtos where fornecedor in :fornecedors",
                Fornecedor.class
            )
            .setParameter("fornecedors", fornecedors)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
