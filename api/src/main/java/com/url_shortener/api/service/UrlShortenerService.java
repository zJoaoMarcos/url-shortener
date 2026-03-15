package com.url_shortener.api.service;

import com.url_shortener.api.entity.UrlEntity;
import com.url_shortener.api.exception.UrlNotFoundException;
import com.url_shortener.api.generator.IdGenerator;
import com.url_shortener.api.repository.UrlRepository;
import com.url_shortener.api.util.Base62;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class UrlShortenerService {

    private final UrlRepository urlRepository;
    private final IdGenerator idGenerator;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String CACHE_PREFIX = "url:cache:";
    private static final Duration CACHE_TTL = Duration.ofHours(24);

    public UrlShortenerService(UrlRepository urlRepository, IdGenerator idGenerator, RedisTemplate<String, String> redisTemplate) {
        this.urlRepository = urlRepository;
        this.idGenerator = idGenerator;
        this.redisTemplate = redisTemplate;
    }

    public String shorten(String longUrl) {

        long id = idGenerator.generateId();

        String shortcode =  new Base62("my_secret").encode(id);

        UrlEntity entity = new UrlEntity(shortcode, longUrl);
        urlRepository.save(entity);

        redisTemplate.opsForValue().set(CACHE_PREFIX + shortcode, longUrl, CACHE_TTL);

        return "http://localhost:8080/" + shortcode;

    }

    public String getOriginalUrl(String shortcode) {
        String cached = redisTemplate.opsForValue().get(CACHE_PREFIX + shortcode);
        if (cached != null) {
            return cached;
        }

        String longUrl = urlRepository.findById(shortcode)
                .map(UrlEntity::getLongUrl)
                .orElseThrow(() -> new UrlNotFoundException(shortcode));

        redisTemplate.opsForValue().set(CACHE_PREFIX + shortcode, longUrl, CACHE_TTL);

        return longUrl;
    }
}
