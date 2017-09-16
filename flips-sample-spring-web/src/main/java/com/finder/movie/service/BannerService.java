package com.finder.movie.service;

import org.flips.annotation.FlipOnEnvironmentProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class BannerService {

    private BannerServiceInner bannerServiceInner;

    @Autowired
    public BannerService(BannerServiceInner bannerServiceInner) {
        this.bannerServiceInner = bannerServiceInner;
    }

    public String getBanner(){
        return bannerServiceInner.getBanner();
    }
}

@Component
class BannerServiceInner{

    @FlipOnEnvironmentProperty(property = "feature.application.banner")
    public String getBanner(){
        return "Movie Finder";
    }
}