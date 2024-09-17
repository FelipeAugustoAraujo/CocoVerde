package br.com.lisetech.cocoverde.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class EnderecoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Endereco getEnderecoSample1() {
        return new Endereco().id(1L).cep("cep1").logradouro("logradouro1").numero(1).complemento("complemento1").bairro("bairro1");
    }

    public static Endereco getEnderecoSample2() {
        return new Endereco().id(2L).cep("cep2").logradouro("logradouro2").numero(2).complemento("complemento2").bairro("bairro2");
    }

    public static Endereco getEnderecoRandomSampleGenerator() {
        return new Endereco()
            .id(longCount.incrementAndGet())
            .cep(UUID.randomUUID().toString())
            .logradouro(UUID.randomUUID().toString())
            .numero(intCount.incrementAndGet())
            .complemento(UUID.randomUUID().toString())
            .bairro(UUID.randomUUID().toString());
    }
}
