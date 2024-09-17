package br.com.lisetech.cocoverde.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CidadeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Cidade getCidadeSample1() {
        return new Cidade().id(1L).nome("nome1");
    }

    public static Cidade getCidadeSample2() {
        return new Cidade().id(2L).nome("nome2");
    }

    public static Cidade getCidadeRandomSampleGenerator() {
        return new Cidade().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString());
    }
}
