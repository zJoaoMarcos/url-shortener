package com.url_shortener.api.service;

import com.url_shortener.api.entity.UrlEntity;
import com.url_shortener.api.exception.UrlNotFoundException;
import com.url_shortener.api.generator.IdGenerator;
import com.url_shortener.api.repository.UrlRepository;
import com.url_shortener.api.util.Base62;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {

    private final UrlRepository urlRepository;
    private final IdGenerator idGenerator;

    public UrlShortenerService(UrlRepository urlRepository, IdGenerator idGenerator) {
        this.urlRepository = urlRepository;
        this.idGenerator = idGenerator;
    }

    public String shorten(String longUrl) {

        long id = idGenerator.generateId();

        String shortcode =  new Base62("my_secret").encode(id);

        UrlEntity entity = new UrlEntity(shortcode, longUrl);
        urlRepository.save(entity);

        return "http://localhost:8080/" + shortcode;

    }

    public String getOriginalUrl(String shortcode) {
        return urlRepository.findById(shortcode)
                .map(UrlEntity::getLongUrl)
                .orElseThrow(() -> new UrlNotFoundException(shortcode));
    }
}
