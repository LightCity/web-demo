package me.longli.demo.caffeinetest;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class CaffeineTest {
    public static void main(String[] args) {
        final long beginTime = System.currentTimeMillis();
        final Cache<String, Number> cache = Caffeine.newBuilder()
                .maximumSize(10)
                .expireAfterWrite(Duration.ofSeconds(3))
                //.weigher((String key, Number value) -> value.intValue() % 7)
                .evictionListener((@Nullable String key, @Nullable Number value, RemovalCause cause) -> {
                    System.out.printf("key=%s, value=%s, cause=%s%n", key, value, cause);
                })
                .build();
        final Random random = new Random();

        for (int n = 0; n < 10; ++n) {
            ForkJoinPool.commonPool().submit(() -> {
                do {
                    String key = String.valueOf(random.nextInt(20));
                    System.out.printf("origin: (%s, %s)%n", key, cache.getIfPresent(key));
                    cache.put(key, random.nextInt(1000));
                } while (System.currentTimeMillis() - beginTime < 10_000);
            });
        }

        try {
            ForkJoinPool.commonPool().awaitTermination(10_000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
