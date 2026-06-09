package com.example.app.controller;

import com.example.app.db.entity.UrlData;
import com.example.app.db.entity.UserData;
import com.example.app.requests.EditRequest;
import com.example.app.requests.NewUrlRequest;
import com.example.app.response.EditResponse;
import com.example.app.response.NewUrlResponse;
import com.example.app.response.ShowUrlResponse;
import com.example.app.service.SlicerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Security;
import java.util.List;

@RestController
@RequestMapping("/slicer/v1")
public class SlicerController {

    @Autowired
    private SlicerService service;


    @PostMapping(name = "/url")
    public NewUrlResponse sliceNewUrl(@Valid @RequestBody NewUrlRequest request, @AuthenticationPrincipal UserDetails user)  {
             String slicedUrl = service.slice(request.getUrl(),
                     user.getUsername());

            return NewUrlResponse.response(slicedUrl);
    }

    @GetMapping(name = "/url/{id}")
    public RedirectView get(@Valid @PathVariable String id) {
        RedirectView redirect = new RedirectView();
        redirect.setUrl(service.redirect(id));

        return redirect;
    }

    @GetMapping("/list")
    public ShowUrlResponse showUrl(@AuthenticationPrincipal String user) {
        List<UrlData> urlList = service.showData(user);

        return ShowUrlResponse.response(urlList);
    }

    @GetMapping("/list/active")
    public ShowUrlResponse showActiveUrls(@AuthenticationPrincipal String user) {
        List<UrlData> activeUrlList = service.showActiveData(user);

        return ShowUrlResponse.response(activeUrlList);
    }

    @PutMapping(name = "/url")
    public EditResponse edit(@Valid @RequestBody EditRequest request) {

            UrlData data = service.edit(request.getId(), request.getNewUrl());

            return EditResponse.response(data);
    }

    @DeleteMapping(name = "/url/{id}")
    public UrlData delete(@PathVariable String id) {
            return service.delete(id);
    }
}
