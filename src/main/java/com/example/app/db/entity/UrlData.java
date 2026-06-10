package com.example.app.db.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.CloseableThreadContext;

import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "`UrlData`")
public class UrlData {
    @Id
    @Column(name = "`SlicedUrl`")
    private String slicedUrl;
    @Column(name = "`TrueUrl`", nullable = false)
    private String url;
    @Column(name = "`RefCount`", nullable = false)
    private Long references;
    @Column(name = "`ExpDate`", nullable = false)
    private LocalDate date;
    @Column(name ="`IsActive`", nullable = false)
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "`Username`", nullable = false)
    private UserData username;


    public UrlData(String slicedUrl, String url, UserData username) {
        this.url = url;
        this.slicedUrl = slicedUrl;
        references = 0L;
        this.username = username;
        date = LocalDate.now().plusDays(14);
        this.isActive = true;
    }

    public UrlData(String slicedUrl, String url, UserData username, boolean isActive) {
        this.url = url;
        this.slicedUrl = slicedUrl;
        references = 0L;
        this.username = username;
        date = LocalDate.now().plusDays(14);
        this.isActive = isActive;
    }

    public void updateUrl(String newUrl) {
        this.url = newUrl;
    }

    @Override
    public boolean equals(Object o) {
        if(o.getClass() != this.getClass()) {
            return false;
        }

        return this.slicedUrl.equals(((UrlData) o).getSlicedUrl());

    }
}
