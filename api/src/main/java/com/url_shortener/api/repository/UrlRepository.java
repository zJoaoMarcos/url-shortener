package com.url_shortener.api.repository;


import com.url_shortener.api.entity.UrlEntity;
import org.springframework.data.repository.CrudRepository;

public interface UrlRepository extends CrudRepository<UrlEntity, String> {
}
