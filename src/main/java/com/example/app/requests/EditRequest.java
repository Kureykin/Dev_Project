package com.example.app.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class EditRequest {
    @URL
    @NotBlank
    private String newUrl;
    private String id;

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ", \"newUrl\":\"" + newUrl + '\"' +
                '}';
    }
}
