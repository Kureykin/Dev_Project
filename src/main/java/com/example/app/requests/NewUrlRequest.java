package com.example.app.requests;

import lombok.Data;

@Data
public class NewUrlRequest {
    private String url;
    private String jwt;
}
