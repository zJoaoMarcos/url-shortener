package com.url_shortener.api.controller;

import com.url_shortener.api.dto.ShorteUrlResponse;
import com.url_shortener.api.dto.ShortenUrlRequest;
import com.url_shortener.api.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping()
public class UrlController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @GetMapping("/{shortcode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortcode) {
        String longUrl = urlShortenerService.getOriginalUrl(shortcode);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(longUrl));

        return new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);
    }

    @PostMapping("v1/shorter")
    public ResponseEntity<ShorteUrlResponse> shortenUrl(@RequestBody ShortenUrlRequest request) {
        String longUrl = request.longUrl();

        String shortUrl = urlShortenerService.shorten(longUrl);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ShorteUrlResponse(shortUrl));
    }
}
