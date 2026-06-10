package com.example.app.controller;

import com.example.app.db.entity.UrlData;
import com.example.app.db.entity.UserData;
import com.example.app.requests.EditRequest;
import com.example.app.requests.NewUrlRequest;
import com.example.app.response.DeleteResponse;
import com.example.app.response.EditResponse;
import com.example.app.response.NewUrlResponse;
import com.example.app.response.ShowUrlResponse;
import com.example.app.service.SlicerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Security;
import java.util.List;

@RestController
@RequestMapping(path = "/slicer/v1")
public class SlicerController {

    @Autowired
    private SlicerService service;

    @Operation
    @PostMapping("/url")
    public NewUrlResponse sliceNewUrl(@Valid @RequestBody NewUrlRequest request, @AuthenticationPrincipal String user)  {
             String slicedUrl = service.slice(request.getUrl(),
                     user);

            return NewUrlResponse.response(slicedUrl);
    }

    @Operation
    @GetMapping("/url/{id}")
    public RedirectView get(@Valid @PathVariable String id) {
        RedirectView redirect = new RedirectView();
        redirect.setUrl(service.redirect(id));

        return redirect;
    }

    @Operation
    @GetMapping("/list")
    public ShowUrlResponse showUrl(@AuthenticationPrincipal String user) {
        List<UrlData> urlList = service.showData(user);

        return ShowUrlResponse.response(urlList);
    }

    @Operation
    @GetMapping("/list/active")
    public ShowUrlResponse showActiveUrls(@AuthenticationPrincipal String user) {
        List<UrlData> activeUrlList = service.showActiveData(user);

        return ShowUrlResponse.response(activeUrlList);
    }

    @Operation
    @PutMapping("/url")
    public EditResponse edit(@Valid @RequestBody EditRequest request, @AuthenticationPrincipal String user) {
            UrlData data = service.edit(request.getId(), request.getNewUrl(), user);

            return EditResponse.response(data);
    }

    @Operation
    @DeleteMapping("/url/{id}")
    public DeleteResponse delete(@PathVariable String id, @AuthenticationPrincipal String user) {
        UrlData data = service.delete(id, user);

            return DeleteResponse.response(data.getSlicedUrl(), data.getUrl());
    }
}
