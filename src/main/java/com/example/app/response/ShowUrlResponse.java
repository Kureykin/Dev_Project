package com.example.app.response;

import com.example.app.db.entity.UrlData;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ShowUrlResponse {
    List<UrlData> dataList;

    public static ShowUrlResponse response(List<UrlData> urlList) {
        return ShowUrlResponse.builder().dataList(urlList).build();
    }

}
