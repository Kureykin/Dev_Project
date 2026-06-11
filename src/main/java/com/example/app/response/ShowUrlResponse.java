package com.example.app.response;

import com.example.app.db.entity.UrlData;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ShowUrlResponse {
    List<UrlResponse> dataList;

    public static ShowUrlResponse response(List<UrlData> urlList) {
        return ShowUrlResponse.builder()
                .dataList(urlList.stream()
                        .map(com.example.app.response.UrlResponse::response)
                        .toList())
                .build();
    }

}
