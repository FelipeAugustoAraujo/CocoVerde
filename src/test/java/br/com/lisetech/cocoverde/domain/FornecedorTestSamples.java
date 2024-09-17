package br.com.lisetech.cocoverde.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FornecedorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Fornecedor getFornecedorSample1() {
        return new Fornecedor().id(1L).nome("nome1").identificador("identificador1").telefone("telefone1");
    }

    public static Fornecedor getFornecedorSample2() {
        return new Fornecedor().id(2L).nome("nome2").identificador("identificador2").telefone("telefone2");
    }

    public static Fornecedor getFornecedorRandomSampleGenerator() {
        return new Fornecedor()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .identificador(UUID.randomUUID().toString())
            .telefone(UUID.randomUUID().toString());
    }
}
