package com.example.app.controller;

import com.example.app.entity.UrlData;
import com.example.app.requests.EditRequest;
import com.example.app.requests.NewUrlRequest;
import com.example.app.response.EditResponse;
import com.example.app.response.NewUrlResponse;
import com.example.app.response.ShowUrlResponse;
import com.example.app.service.SlicerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/slicer/v1")
public class SlicerController {

    @Autowired
    private SlicerService service;

    @PostMapping( name = "url")
    public NewUrlResponse sliceNewUrl(@RequestBody NewUrlRequest request)  {
        try {
            if(request.getJwt() == null) {
                throw new Exception();
            }
            return service.slice(request.getUrl());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping(name = "url")
    public RedirectView get(String id) {
        RedirectView redirect = new RedirectView();
        redirect.setUrl(service.redirect(id));

        return redirect;
    }
    @GetMapping("list")
    public ShowUrlResponse showUrl() {
        return service.showData();
    }
    @PutMapping(name = "url")
    public EditResponse edit(@RequestBody EditRequest request) {
        try {
            if(request.getJwt() == null) {
                throw new Exception();
            }
            return service.edit(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping(name = "url")
    public UrlData delete(@RequestBody String id, @RequestBody String jwt) {
        try {
            if(jwt == null) {
                throw new Exception();
            }
            return service.delete(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
