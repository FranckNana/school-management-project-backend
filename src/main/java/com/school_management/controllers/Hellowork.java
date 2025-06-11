package com.school_management.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class Hellowork {

    @GetMapping("/hello")
    public ResponseEntity<String> helloworld(){
        System.out.println("Controller hello method called");
        String s = "Hello World Francky la joie";
        return new ResponseEntity<>(s, HttpStatus.OK);
    }
}

