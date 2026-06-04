package com.example.app.service;

import com.example.app.entity.UrlData;
import com.example.app.requests.EditRequest;
import com.example.app.response.EditResponse;
import com.example.app.response.NewUrlResponse;
import com.example.app.response.ShowUrlResponse;

public interface SlicerService {
    public NewUrlResponse slice(String url);
    public EditResponse edit(EditRequest request);
    public ShowUrlResponse showData();
    public UrlData delete(String id);
    public String redirect(String id);
}
