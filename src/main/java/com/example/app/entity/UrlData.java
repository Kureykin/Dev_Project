package com.example.app.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Random;

@Entity
@Getter
@NoArgsConstructor
public class UrlData {
    @Id
    private String slicedUrl;
    @Column(name = "trueUrl", nullable = false)
    private String url;
    @Column(name = "refCount", nullable = false)
    private Long references;

    public UrlData(String url) {
        this.url = url;
        this.slicedUrl = generateId();
        references = 0L;
    }
    public void incrementRefCount() {
        references++;
    }

    public void updateUrl(String newUrl) {
        this.url = newUrl;
    }

    private String generateId() {
        long id = new Random().nextLong() % 10000000;

        return "" + id;
    }
}
