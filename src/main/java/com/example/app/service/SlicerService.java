package com.example.app.service;

import com.example.app.db.entity.UrlData;
import com.example.app.requests.EditRequest;
import java.text.ParseException;
import java.util.List;

public interface SlicerService {
    public String slice(String url, String username);
    public UrlData edit(String slicedUrl, String NewTrueUrl);
    public List<UrlData> showData(String username);
    public UrlData delete(String id);
    public String redirect(String id);
    public List<UrlData> showActiveData(String username);
}
