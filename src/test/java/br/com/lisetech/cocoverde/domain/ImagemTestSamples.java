package br.com.lisetech.cocoverde.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ImagemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Imagem getImagemSample1() {
        return new Imagem().id(1L).name("name1").contentType("contentType1").description("description1");
    }

    public static Imagem getImagemSample2() {
        return new Imagem().id(2L).name("name2").contentType("contentType2").description("description2");
    }

    public static Imagem getImagemRandomSampleGenerator() {
        return new Imagem()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .contentType(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
