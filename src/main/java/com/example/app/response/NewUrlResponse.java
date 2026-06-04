package com.example.app.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NewUrlResponse {
    String newUrl;

    public static NewUrlResponse response(String newUrl) {
        return NewUrlResponse.builder().newUrl(newUrl).build();
    }
}
