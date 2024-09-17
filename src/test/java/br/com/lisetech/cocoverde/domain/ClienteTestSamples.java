package br.com.lisetech.cocoverde.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ClienteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Cliente getClienteSample1() {
        return new Cliente().id(1L).nome("nome1").dataNascimento("dataNascimento1").identificador("identificador1").telefone("telefone1");
    }

    public static Cliente getClienteSample2() {
        return new Cliente().id(2L).nome("nome2").dataNascimento("dataNascimento2").identificador("identificador2").telefone("telefone2");
    }

    public static Cliente getClienteRandomSampleGenerator() {
        return new Cliente()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .dataNascimento(UUID.randomUUID().toString())
            .identificador(UUID.randomUUID().toString())
            .telefone(UUID.randomUUID().toString());
    }
}
