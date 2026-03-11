package com.url_shortener.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

@Setter
@Getter
@Table("urls")
public class UrlEntity {

    @PrimaryKey
    private String shortcode;

    @Column("long_url")
    private String longUrl;

    @Column("created_at")
    private LocalDateTime createdAt;


    public UrlEntity() {
    }

    public UrlEntity(String shortcode, String longUrl) {
        this.shortcode = shortcode;
        this.longUrl = longUrl;
        this.createdAt = LocalDateTime.now();
    }

}
