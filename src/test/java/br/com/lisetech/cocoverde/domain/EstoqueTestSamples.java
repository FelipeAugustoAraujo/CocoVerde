package br.com.lisetech.cocoverde.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class EstoqueTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Estoque getEstoqueSample1() {
        return new Estoque().id(1L).quantidade(1);
    }

    public static Estoque getEstoqueSample2() {
        return new Estoque().id(2L).quantidade(2);
    }

    public static Estoque getEstoqueRandomSampleGenerator() {
        return new Estoque().id(longCount.incrementAndGet()).quantidade(intCount.incrementAndGet());
    }
}
