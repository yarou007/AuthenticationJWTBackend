package com.AuthBack.springboot.controller;


import org.springframework.web.bind.annotation.RestController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminController {
    
    @GetMapping("/")
    public String helloAdmineController(){
        return "Admin level access";
    }
}