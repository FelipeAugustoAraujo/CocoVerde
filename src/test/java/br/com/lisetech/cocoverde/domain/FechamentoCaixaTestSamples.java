package br.com.lisetech.cocoverde.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FechamentoCaixaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static FechamentoCaixa getFechamentoCaixaSample1() {
        return new FechamentoCaixa().id(1L).quantidadeCocosPerdidos(1).quantidadeCocosVendidos(1).quantidadeCocoSobrou(1).divididoPor(1);
    }

    public static FechamentoCaixa getFechamentoCaixaSample2() {
        return new FechamentoCaixa().id(2L).quantidadeCocosPerdidos(2).quantidadeCocosVendidos(2).quantidadeCocoSobrou(2).divididoPor(2);
    }

    public static FechamentoCaixa getFechamentoCaixaRandomSampleGenerator() {
        return new FechamentoCaixa()
            .id(longCount.incrementAndGet())
            .quantidadeCocosPerdidos(intCount.incrementAndGet())
            .quantidadeCocosVendidos(intCount.incrementAndGet())
            .quantidadeCocoSobrou(intCount.incrementAndGet())
            .divididoPor(intCount.incrementAndGet());
    }
}
