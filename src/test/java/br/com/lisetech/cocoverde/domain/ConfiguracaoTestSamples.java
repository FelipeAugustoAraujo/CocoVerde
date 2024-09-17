package br.com.lisetech.cocoverde.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ConfiguracaoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Configuracao getConfiguracaoSample1() {
        return new Configuracao().id(1L);
    }

    public static Configuracao getConfiguracaoSample2() {
        return new Configuracao().id(2L);
    }

    public static Configuracao getConfiguracaoRandomSampleGenerator() {
        return new Configuracao().id(longCount.incrementAndGet());
    }
}
