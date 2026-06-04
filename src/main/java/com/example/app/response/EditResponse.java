package com.example.app.response;

import com.example.app.entity.UrlData;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EditResponse {
    UrlData data;

    public static EditResponse response(UrlData data) {
        return EditResponse.builder().data(data).build();
    }
}
