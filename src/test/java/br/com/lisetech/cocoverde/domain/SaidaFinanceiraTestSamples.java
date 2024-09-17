package br.com.lisetech.cocoverde.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SaidaFinanceiraTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SaidaFinanceira getSaidaFinanceiraSample1() {
        return new SaidaFinanceira().id(1L).descricao("descricao1");
    }

    public static SaidaFinanceira getSaidaFinanceiraSample2() {
        return new SaidaFinanceira().id(2L).descricao("descricao2");
    }

    public static SaidaFinanceira getSaidaFinanceiraRandomSampleGenerator() {
        return new SaidaFinanceira().id(longCount.incrementAndGet()).descricao(UUID.randomUUID().toString());
    }
}
