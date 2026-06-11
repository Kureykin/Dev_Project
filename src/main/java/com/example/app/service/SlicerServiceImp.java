package com.example.app.service;

import com.example.app.db.entity.*;
import com.example.app.db.repository.SlicerRepository;
import com.example.app.until.Generator;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
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
        String slicedUrl = generateUniqueCode();

        UrlData newUrl = new UrlData(slicedUrl, url, userData);
        repo.save(newUrl);

        return slicedUrl;
    }

    @Override
    public UrlData edit(String slicedUrl, String newTrueUrl, String user) {
        UrlData url = repo.findById(slicedUrl)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Link not found"));

        if(!url.getUsername()
                .getUsername()
                .equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can not do that to this link");
        }

        url.updateUrl(newTrueUrl);
        return repo.save(url);
    }

    @Override
    public List<UrlData> showActiveData(String username) {
        UserData user = userService.findUserByName(username);
        return repo.showActiveData(user, LocalDate.now());
    }

    @Override
    public List<UrlData> showData(String username) {
        UserData user = userService.findUserByName(username);
        return repo.showAllUsersUrl(user);
    }

    @Override
    public UrlData delete(String id, String user) {
        UrlData deletedData = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Link not found"));

        if(!deletedData.getUsername()
                .getUsername()
                .equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can not do that to this link");
        }

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

        if(LocalDate.now()
                .isAfter(url.getDate())) {
            throw new ResponseStatusException(HttpStatus.GONE, "Link is expired");
        }

        repo.incrementRefCount(id);

        return url.getUrl();
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = Generator.generateId();
        } while (repo.existsById(code));
        return code;
    }
}
