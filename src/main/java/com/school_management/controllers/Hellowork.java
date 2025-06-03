package com.school_management.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Hellowork {

    @GetMapping("/hello")
    public String hellowork(){
        return "<h1>Hello World Francky la joie</h1>";
    }
}

