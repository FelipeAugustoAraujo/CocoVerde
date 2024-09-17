package br.com.lisetech.cocoverde.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class DiaTrabalhoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DiaTrabalho getDiaTrabalhoSample1() {
        return new DiaTrabalho().id(1L);
    }

    public static DiaTrabalho getDiaTrabalhoSample2() {
        return new DiaTrabalho().id(2L);
    }

    public static DiaTrabalho getDiaTrabalhoRandomSampleGenerator() {
        return new DiaTrabalho().id(longCount.incrementAndGet());
    }
}
