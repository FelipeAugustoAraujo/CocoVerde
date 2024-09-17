package br.com.lisetech.cocoverde.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FrenteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Frente getFrenteSample1() {
        return new Frente().id(1L).quantidade(1);
    }

    public static Frente getFrenteSample2() {
        return new Frente().id(2L).quantidade(2);
    }

    public static Frente getFrenteRandomSampleGenerator() {
        return new Frente().id(longCount.incrementAndGet()).quantidade(intCount.incrementAndGet());
    }
}
