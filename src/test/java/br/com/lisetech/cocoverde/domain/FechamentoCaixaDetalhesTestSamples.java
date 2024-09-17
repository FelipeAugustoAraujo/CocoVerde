package br.com.lisetech.cocoverde.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class FechamentoCaixaDetalhesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FechamentoCaixaDetalhes getFechamentoCaixaDetalhesSample1() {
        return new FechamentoCaixaDetalhes().id(1L);
    }

    public static FechamentoCaixaDetalhes getFechamentoCaixaDetalhesSample2() {
        return new FechamentoCaixaDetalhes().id(2L);
    }

    public static FechamentoCaixaDetalhes getFechamentoCaixaDetalhesRandomSampleGenerator() {
        return new FechamentoCaixaDetalhes().id(longCount.incrementAndGet());
    }
}
