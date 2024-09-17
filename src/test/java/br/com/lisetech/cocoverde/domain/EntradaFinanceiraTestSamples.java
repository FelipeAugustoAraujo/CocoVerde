package br.com.lisetech.cocoverde.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EntradaFinanceiraTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static EntradaFinanceira getEntradaFinanceiraSample1() {
        return new EntradaFinanceira().id(1L).descricao("descricao1");
    }

    public static EntradaFinanceira getEntradaFinanceiraSample2() {
        return new EntradaFinanceira().id(2L).descricao("descricao2");
    }

    public static EntradaFinanceira getEntradaFinanceiraRandomSampleGenerator() {
        return new EntradaFinanceira().id(longCount.incrementAndGet()).descricao(UUID.randomUUID().toString());
    }
}
