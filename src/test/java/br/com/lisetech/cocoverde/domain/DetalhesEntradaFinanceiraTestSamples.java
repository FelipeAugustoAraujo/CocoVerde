package br.com.lisetech.cocoverde.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DetalhesEntradaFinanceiraTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static DetalhesEntradaFinanceira getDetalhesEntradaFinanceiraSample1() {
        return new DetalhesEntradaFinanceira().id(1L).quantidadeItem(1);
    }

    public static DetalhesEntradaFinanceira getDetalhesEntradaFinanceiraSample2() {
        return new DetalhesEntradaFinanceira().id(2L).quantidadeItem(2);
    }

    public static DetalhesEntradaFinanceira getDetalhesEntradaFinanceiraRandomSampleGenerator() {
        return new DetalhesEntradaFinanceira().id(longCount.incrementAndGet()).quantidadeItem(intCount.incrementAndGet());
    }
}
