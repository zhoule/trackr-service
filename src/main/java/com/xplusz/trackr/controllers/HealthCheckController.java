package com.xplusz.trackr.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthCheckController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getRoot(){
        return new ResponseEntity<>("I'm alive.", HttpStatus.OK);
    }
}
