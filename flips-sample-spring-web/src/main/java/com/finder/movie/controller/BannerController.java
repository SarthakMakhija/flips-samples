package com.finder.movie.controller;

import com.finder.movie.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BannerController {

    private BannerService bannerService;

    @Autowired
    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @RequestMapping(value = "/banner", method = RequestMethod.GET)
    public String getBanner(){
        return bannerService.getBanner();
    }
}