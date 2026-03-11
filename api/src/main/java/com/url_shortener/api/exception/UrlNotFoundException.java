package com.url_shortener.api.exception;

public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String shortcode) {
        super("Shortcode não encontrado: " + shortcode);
    }
}
