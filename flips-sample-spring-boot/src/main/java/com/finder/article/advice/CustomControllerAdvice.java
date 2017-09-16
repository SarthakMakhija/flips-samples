package com.finder.article.advice;

import org.flips.exception.FeatureNotEnabledException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(FeatureNotEnabledException.class)
    public ResponseEntity<?> handleFeatureNotEnabledException(FeatureNotEnabledException e){
        return new ResponseEntity<>("Custom Disabled Message " + e.getFeature().getFeatureName(), HttpStatus.NOT_IMPLEMENTED);
    }
}