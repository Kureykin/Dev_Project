package com.example.app.service;

import com.example.app.entity.UrlData;
import com.example.app.repository.SlicerRepository;
import com.example.app.requests.EditRequest;
import com.example.app.response.EditResponse;
import com.example.app.response.NewUrlResponse;
import com.example.app.response.ShowUrlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlicerServiceImp implements SlicerService {
    @Autowired
    private SlicerRepository repo;

    @Override
    public NewUrlResponse slice(String url) {
        UrlData newUrl = new UrlData(url);
        repo.save(newUrl);

        return NewUrlResponse.response(newUrl.getSlicedUrl());
    }

    @Override
    public EditResponse edit(EditRequest request) {
        UrlData url = repo.findById(request.getId()).get();
        url.updateUrl(request.getNewUrl());
        return EditResponse.response(url);
    }

    @Override
    public ShowUrlResponse showData() {
        return ShowUrlResponse.response(repo.findAll());
    }

    @Override
    public UrlData delete(String id) {
        UrlData deletedData = repo.findById(id).get();
        repo.deleteById(id);
        return deletedData;
    }

    @Override
    public String redirect(String id) {
        UrlData url = repo.findById(id).get();
        url.incrementRefCount();
        repo.save(url);
        return url.getUrl();
    }
}
