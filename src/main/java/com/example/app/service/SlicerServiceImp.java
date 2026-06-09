package com.example.app.service;

import com.example.app.db.entity.*;
import com.example.app.db.repository.SlicerRepository;
import com.example.app.untils.Generator;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SlicerServiceImp implements SlicerService {
    @Autowired
    private SlicerRepository repo;
    @Autowired
    private UserDataService userService;

    @Override
    public String slice(String url, String username) {

        UserData userData = userService.findUserByName(username);
        String slicedUrl = Generator.generateId();

        UrlData newUrl = new UrlData(slicedUrl, url, userData);
        repo.save(newUrl);

        return slicedUrl;
    }

    @Override
    public UrlData edit(String slicedUrl, String newTrueUrl) {
        UrlData url = repo.findById(slicedUrl)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Link not found"));
        url.updateUrl(newTrueUrl);
        return repo.save(url);
    }

    @Override
    public List<UrlData> showActiveData(String username) {
        UserData user = userService.findUserByName(username);
        return repo.showActiveData(user);
    }

    @Override
    public List<UrlData> showData(String username) {
        UserData user = userService.findUserByName(username);
        return repo.showAllUsersUrl(user);
    }

    @Override
    public UrlData delete(String id) {
        UrlData deletedData = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Link not found"));
        repo.deleteById(id);
        return deletedData;
    }

    @Override
    public String redirect(String id) {
        UrlData url = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Link not found"));

        if(!url.getIsActive()){
            throw new ResponseStatusException(HttpStatus.LOCKED, "This link is not active");
        }
        repo.incrementRefCount(id);

        return url.getUrl();
    }
}
