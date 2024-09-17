package br.com.lisetech.cocoverde.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DetalhesSaidaFinanceiraTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static DetalhesSaidaFinanceira getDetalhesSaidaFinanceiraSample1() {
        return new DetalhesSaidaFinanceira().id(1L).quantidadeItem(1);
    }

    public static DetalhesSaidaFinanceira getDetalhesSaidaFinanceiraSample2() {
        return new DetalhesSaidaFinanceira().id(2L).quantidadeItem(2);
    }

    public static DetalhesSaidaFinanceira getDetalhesSaidaFinanceiraRandomSampleGenerator() {
        return new DetalhesSaidaFinanceira().id(longCount.incrementAndGet()).quantidadeItem(intCount.incrementAndGet());
    }
}
