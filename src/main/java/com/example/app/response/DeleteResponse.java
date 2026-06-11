package com.example.app.response;

import com.example.app.db.entity.UrlData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteResponse {
    private String slicedUrl;
    private String trueUrl;

    public static DeleteResponse response(String slicedUrl, String trueUrl) {
        return DeleteResponse.builder().slicedUrl(slicedUrl).trueUrl(trueUrl).build();
    }
}
