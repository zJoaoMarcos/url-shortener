package com.url_shortener.api.generator;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

    private static final String COUNTER_KEY = "url:id:counter";

    @Value("${app.id.initial-value:14776336}")
    private long initialValue;

    private final RedisTemplate<String, String> redisTemplate;

    public IdGenerator(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void initializeCounter() {
        try {
            Boolean success = redisTemplate.opsForValue()
                    .setIfAbsent(COUNTER_KEY, String.valueOf(initialValue));

            if (Boolean.TRUE.equals(success)) {
                System.out.println("✅ Contador inicializado em: " + initialValue);
            } else {
                String currentValue = redisTemplate.opsForValue().get(COUNTER_KEY);
                System.out.println("ℹ️ Contador já existe com valor: " + currentValue);
            }
        } catch (Exception e) {
            System.err.println("❌ Erro ao inicializar contador: " + e.getMessage());
        }
    }

    public long generateId() {
        Long id = redisTemplate.opsForValue().increment(COUNTER_KEY);

        if (id == null) {
            throw new RuntimeException("Falha ao gerar ID no Redis");
        }

        return id;
    }

    public long getCurrentCounter() {
        String value = redisTemplate.opsForValue().get(COUNTER_KEY);
        return value != null ? Long.parseLong(value) : 0L;
    }
}