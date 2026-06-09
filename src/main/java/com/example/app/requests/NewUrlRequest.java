package com.example.app.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class NewUrlRequest {
    @URL
    @NotBlank
    private String url;
}
