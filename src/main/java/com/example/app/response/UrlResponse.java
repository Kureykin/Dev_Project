package com.example.app.response;

import com.example.app.db.entity.UrlData;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class UrlResponse {
    private String slicedUrl;
    private String trueUrl;
    private String shortUrl;
    private Long references;
    private LocalDate expiresAt;
    private Boolean active;
    private String username;

    public static UrlResponse response(UrlData data) {
        return UrlResponse.builder()
                .slicedUrl(data.getSlicedUrl())
                .trueUrl(data.getUrl())
                .shortUrl("http://localhost:8080/slicer/v1/url/" + data.getSlicedUrl())
                .references(data.getReferences())
                .expiresAt(data.getDate())
                .active(data.getIsActive())
                .username(data.getUsername().getUsername()).build();
    }
}
